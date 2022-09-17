package com.tgirou.skilled;

import com.mojang.logging.LogUtils;
import com.tgirou.skilled.api.util.Constants;
import com.tgirou.skilled.client.KeyInputHandler;
import com.tgirou.skilled.events.progression.ProgressionEvents;
import com.tgirou.skilled.events.skills.MinerEvent;
import com.tgirou.skilled.networking.Messages;
import com.tgirou.skilled.client.KeyBindings;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SkilledMod.MOD_ID)
public class SkilledMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = Constants.MOD_ID;

    private static final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, Constants.MOD_ID), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;



    public SkilledMod()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        forgeEventBus.register(this);
        forgeEventBus.register(new MinerEvent());
        forgeEventBus.addGenericListener(Entity.class, SkilledMod::forgeEventHandler);
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

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                                             BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}
