package com.tgirou.skilled.data;

import com.tgirou.skilled.SkilledMod;
import com.tgirou.skilled.skills.ProgressionManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillEvents {
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(SkillProvider.SKILL_MANAGER).isPresent()) {
                event.addCapability(new ResourceLocation(SkilledMod.MOD_ID, "skill"), new SkillProvider());
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(SkillProvider.SKILL_MANAGER).ifPresent(oldSkillManager -> {
                event.getPlayer().getCapability(SkillProvider.SKILL_MANAGER).ifPresent(newSkillManager -> {
                    newSkillManager.copyFrom(oldSkillManager);
                });
            });
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ProgressionManager.class);
    }

}
