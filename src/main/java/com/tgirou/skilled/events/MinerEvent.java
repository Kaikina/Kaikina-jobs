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

        if (event.getWorld().isClientSide()) return;

        boolean didGetEXP = false;
        final BlockPos pos = event.getPos();
        System.out.println("BLOCK " + block.getRegistryName() + " destroyed.");
        didGetEXP = true;

        if (didGetEXP) {
            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
                SkillManager.miner.addExp(1);
            });

            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
                System.out.println("Skill " + SkillManager.miner.name + " has " + SkillManager.miner.getXp() + " exp.");
            });
        }
    }

    @SubscribeEvent
    public void registerPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        Player player = (Player) event.getEntity();
        Block block = event.getState().getBlock();

        if (event.getWorld().isClientSide()) return;

        boolean didGetEXP = false;
        final BlockPos pos = event.getPos();
        System.out.println("BLOCK " + block.getRegistryName() + " placed.");
        didGetEXP = true;

        if (didGetEXP) {
            assert player != null;
            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
                SkillManager.lumberjack.addExp(1);
            });

            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
                System.out.println("Skill " + SkillManager.lumberjack.name + " has " + SkillManager.lumberjack.getXp() + " exp.");
            });
        }
    }
}
