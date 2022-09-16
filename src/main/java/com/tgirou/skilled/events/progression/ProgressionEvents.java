package com.tgirou.skilled.events.progression;

import com.tgirou.skilled.SkilledMod;
import com.tgirou.skilled.providers.progression.ProgressionProvider;
import com.tgirou.skilled.progression.ProgressionManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.lwjgl.system.CallbackI;


public class ProgressionEvents {
    ProgressionEvents() {

    }
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(ProgressionProvider.PROGRESSION_MANAGER).isPresent()) {
                event.addCapability(new ResourceLocation(SkilledMod.MOD_ID, "skill"), new ProgressionProvider());
            }
        }
    }


    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {

            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent(oldProgression -> {
                event.getPlayer().getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent(newProgression -> {
                    newProgression.copyFrom(oldProgression);
                });
            });
            event.getOriginal().invalidateCaps();

        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ProgressionManager.class);
    }

}
