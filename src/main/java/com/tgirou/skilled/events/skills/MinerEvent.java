package com.tgirou.skilled.events.skills;

import com.tgirou.skilled.handlers.ExpHandler;
import com.tgirou.skilled.api.util.Utils;
import com.tgirou.skilled.skills.MinerSkill;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
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
        if (event.getWorld().isClientSide()) return;

        Player player = event.getPlayer();
        Block block = event.getState().getBlock();

        // If the block that is being broken is part of Miner skill's blocks
        if (minerSkill.hasBlock(Objects.requireNonNull(block.getRegistryName()).toString())) {
            // Get the exp range gain for this skill
            int[] xpRange = minerSkill.getExpRangeForBlock(Objects.requireNonNull(block.getRegistryName()).toString());
            int exp = Utils.getRandomNumber(xpRange[0], xpRange[1]);

                ExpHandler.addExp(MinerSkill.NAME, exp, player);

                player.sendMessage(
                        new TextComponent(
                                ExpHandler.getExpFor(MinerSkill.NAME, player).toString() + " xp pour Mineur."
                        ),
                        Util.NIL_UUID
                );
        }

    }
}
