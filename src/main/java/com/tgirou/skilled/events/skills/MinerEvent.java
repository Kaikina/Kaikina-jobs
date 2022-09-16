package com.tgirou.skilled.events.skills;

import com.tgirou.skilled.api.util.Utils;
import com.tgirou.skilled.progression.Progression;
import com.tgirou.skilled.providers.progression.ProgressionProvider;
import com.tgirou.skilled.skills.MinerSkill;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class MinerEvent {
    protected MinerSkill minerSkill = new MinerSkill();

    /**
     * Triggered when player is about to break a block.
     * @param event the breaking event
     */
    @SubscribeEvent
    public void registerBreakEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getState().getBlock();

        if (event.getWorld().isClientSide()) return;

        // If the block that is being broken is part of Miner skill's blocks
        if (minerSkill.hasBlock(Objects.requireNonNull(block.getRegistryName()).toString())) {
            // Get the exp range gain for this skill
            int[] xpRange = minerSkill.getExpRangeForBlock(Objects.requireNonNull(block.getRegistryName()).toString());
            // Get the player progression
            player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent(progressionManager -> {
                Progression playerProgression = progressionManager.getProgression();
                // Get the player's actual level before adding exp
                int previousExperienceLevel = minerSkill.getLevel(playerProgression.getExpFor("miner"));
                // Add exp to the player for miner skill inside the block's range
                playerProgression.addExpFor("miner", Utils.getRandomNumber(xpRange[0], xpRange[1]));
                // Get the player's actual level
                int experienceLevel = minerSkill.getLevel(playerProgression.getExpFor("miner"));
                // If player has level up, play level up sound
                if (experienceLevel > previousExperienceLevel) {
                    float f = experienceLevel > 100 ? 1.0F : (float)experienceLevel / 100.0F;
                    player.level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.PLAYER_LEVELUP,
                            player.getSoundSource(),
                            f * 0.75F,
                            1.0F
                    );
                    player.sendMessage(
                            new TextComponent("Mineur level up ! Niveau : " + experienceLevel),
                            Util.NIL_UUID
                    );
                }
                player.sendMessage(
                        new TextComponent(
                                progressionManager.getProgression()
                                        .getExpFor("miner").toString() + " xp pour Mineur."
                        ),
                        Util.NIL_UUID
                );
            });
        }

    }
}
