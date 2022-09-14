package com.tgirou.skilled.events;

import com.tgirou.skilled.data.SkillProvider;
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
            player.getCapability(SkillProvider.SKILL).ifPresent(skill -> {
                skill.addExp(1);
            });

            player.getCapability(SkillProvider.SKILL).ifPresent(skill -> {
                System.out.println("Skill " + skill.getName() + " has " + skill.getExp() + " exp.");
            });

        }
    }
}
