package com.tgirou.skilled.events.skills;

import com.tgirou.skilled.api.util.Utils;
import com.tgirou.skilled.providers.progression.ProgressionProvider;
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
    @SubscribeEvent
    public void registerBreakEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getState().getBlock();

        if (event.getWorld().isClientSide()) return;

//        final BlockPos pos = event.getPos();
        System.out.println("BLOCK " + block.getRegistryName() + " destroyed.");
        if (minerSkill.hasBlock(Objects.requireNonNull(block.getRegistryName()).toString())) {
            int[] xpRange = minerSkill.getExpRangeForBlock(Objects.requireNonNull(block.getRegistryName()).toString());
            player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent(progressionManager -> {
                progressionManager.getProgression().addExpFor("miner", Utils.getRandomNumber(xpRange[0], xpRange[1]));
                player.sendMessage(new TextComponent(progressionManager.getProgression().getExpFor("miner").toString() + " xp pour Mineur."), Util.NIL_UUID);
            });
        }
//
//        System.out.println("With 0 xp, I'm level " + minerSkill.getLevel(0));
//        System.out.println("With 45321 xp, I'm level " + minerSkill.getLevel(45321));
    }
}
