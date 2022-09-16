package com.tgirou.skilled;

import com.mojang.logging.LogUtils;
import com.tgirou.skilled.api.util.Constants;
import com.tgirou.skilled.client.KeyInputHandler;
import com.tgirou.skilled.events.GatherDataHandler;
import com.tgirou.skilled.events.progression.ProgressionEvents;
import com.tgirou.skilled.events.skills.MinerEvent;
import com.tgirou.skilled.networking.Messages;
import com.tgirou.skilled.client.KeyBindings;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SkilledMod.MOD_ID)
public class SkilledMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = Constants.MOD_ID;

    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    public SkilledMod()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        forgeEventBus.register(this);
        forgeEventBus.register(new MinerEvent());
        forgeEventBus.addGenericListener(Entity.class, SkilledMod::forgeEventHandler);
        forgeEventBus.addListener(GatherDataHandler::dataGeneratorSetup);
    }

    // This event is on the forge bus
    private static void forgeEventHandler(AttachCapabilitiesEvent<Entity> event) {

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            forgeEventBus.addListener(KeyInputHandler::onKeyInput);
            forgeEventBus.addGenericListener(Entity.class, ProgressionEvents::onAttachCapabilitiesPlayer);
            forgeEventBus.addListener(ProgressionEvents::onPlayerCloned);
            forgeEventBus.addListener(ProgressionEvents::onRegisterCapabilities);
            KeyBindings.init();
            Messages.register();
        });

    }
}
