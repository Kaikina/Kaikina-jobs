package com.tgirou.skilled.events;

import com.tgirou.skilled.data.SkillProvider;
import com.tgirou.skilled.skills.MinerSkill;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class MinerEvent {

    @SubscribeEvent
    public void registerBreakEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getState().getBlock();

        if (event.getWorld().isClientSide()) return;

        boolean didGetEXP = false;
        final BlockPos pos = event.getPos();
        MinerSkill minerSkill = new MinerSkill();
        System.out.println("BLOCK " + block.getRegistryName() + " destroyed.");
        if (minerSkill.hasBlock(Objects.requireNonNull(block.getRegistryName()).toString())) {
            int[] xpRange = minerSkill.getExpRangeForBlock(Objects.requireNonNull(block.getRegistryName()).toString());
            System.out.println("Giving " + (int) ((Math.random() * (++xpRange[1] - xpRange[0])) + xpRange[0]) + " xp !");
        }
        didGetEXP = true;

        System.out.println("With 0 xp, I'm level " + minerSkill.getLevel(0));
        System.out.println("With 45321 xp, I'm level " + minerSkill.getLevel(45321));

        if (didGetEXP) {
            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
//                SkillManager.miner.addExp(1);
            });

            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
//                System.out.println("Skill " + SkillManager.miner.name + " has " + SkillManager.miner.getXp() + " exp.");
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
//                SkillManager.lumberjack.addExp(1);
            });

            player.getCapability(SkillProvider.SKILL_MANAGER).ifPresent(SkillManager -> {
//                System.out.println("Skill " + SkillManager.lumberjack.name + " has " + SkillManager.lumberjack.getXp() + " exp.");
            });
        }
    }
}
