package com.tgirou.jobs;

import com.mojang.logging.LogUtils;
import com.tgirou.jobs.events.ForgeBreakEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JobsMod.MOD_ID)
public class JobsMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "jobs";

    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    public JobsMod()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        forgeEventBus.register(this);
        forgeEventBus.register(new ForgeBreakEvent());
        forgeEventBus.addGenericListener(Entity.class, JobsMod::forgeEventHandler);
    }

    // This event is on the forge bus
    private static void forgeEventHandler(AttachCapabilitiesEvent<Entity> event) {

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
