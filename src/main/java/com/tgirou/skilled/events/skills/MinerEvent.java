package com.tgirou.skilled.events.skills;

import com.tgirou.skilled.api.advancements.AdvancementTriggers;
import com.tgirou.skilled.handlers.ExpHandler;
import com.tgirou.skilled.api.util.Utils;
import com.tgirou.skilled.skills.MinerSkill;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.http.config.Registry;

import java.util.Objects;
import java.util.Set;

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
            // Generate exp within the range
            int exp = Utils.getRandomNumber(xpRange[0], xpRange[1]);
            // Give the exp to the player
            ExpHandler.addExp(MinerSkill.NAME, exp, player);
            AdvancementTriggers.FIRST_MINER_BLOCK_BROKEN.trigger((ServerPlayer) player);
            player.sendMessage(
                    new TextComponent(
                            ExpHandler.getExpFor(MinerSkill.NAME, player).toString() + " xp pour Mineur."
                    ),
                    Util.NIL_UUID
            );
        }

    }
}
