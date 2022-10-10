package com.tgirou.skilled;

import com.mojang.logging.LogUtils;
import com.tgirou.skilled.api.util.Constants;
import com.tgirou.skilled.blocks.ModBlocks;
import com.tgirou.skilled.blocks.entities.ModBlockEntities;
import com.tgirou.skilled.client.KeyInputHandler;
import com.tgirou.skilled.client.gui.CrystallizerScreen;
import com.tgirou.skilled.client.gui.ModMenuTypes;
import com.tgirou.skilled.events.commands.RegisterCommandEvents;
import com.tgirou.skilled.events.progression.ProgressionEvents;
import com.tgirou.skilled.events.skills.MinerEvent;
import com.tgirou.skilled.items.ModItems;
import com.tgirou.skilled.networking.Messages;
import com.tgirou.skilled.client.KeyBindings;
import com.tgirou.skilled.recipes.ModRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        forgeEventBus.register(new MinerEvent());
        forgeEventBus.register(RegisterCommandEvents.class);
        forgeEventBus.addListener(KeyInputHandler::onKeyInput);
        forgeEventBus.addGenericListener(Entity.class, ProgressionEvents::onAttachCapabilitiesPlayer);
        forgeEventBus.addListener(ProgressionEvents::onPlayerCloned);
        forgeEventBus.addListener(ProgressionEvents::onRegisterCapabilities);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.CRYSTALLIZER_MENU.get(), CrystallizerScreen::new);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLIZER.get(), RenderType.translucent());
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
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
