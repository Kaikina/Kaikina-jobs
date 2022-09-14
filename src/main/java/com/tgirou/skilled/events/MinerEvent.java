package com.tgirou.skilled.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MinerEvent {
    @SubscribeEvent
    public void registerBreakEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getState().getBlock();
        boolean didGetEXP = false;
        final BlockPos pos = event.getPos();
        System.out.println("BLOCK " + block.getRegistryName() + " destroyed.");
        didGetEXP = true;
        if (didGetEXP) {
            System.out.println("1 XP added to " + player.getName().getContents());
        }
    }
}
