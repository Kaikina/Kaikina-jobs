package com.tgirou.skilled.networking;

import com.tgirou.skilled.SkilledMod;
import com.tgirou.skilled.client.gui.ChoosePath;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoosePathButtonMessage {
    private final int buttonID, x, y, z;

    public ChoosePathButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public ChoosePathButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(ChoosePathButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(ChoosePathButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level;
        HashMap guistate = ChoosePath.guistate;
        if (buttonID == 0) {
            System.out.println("Chose first skill");
        }
        if (buttonID == 1) {
            System.out.println("Chose second skill");
        }
        // Close GUI
        entity.closeContainer();
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        SkilledMod.addNetworkMessage(ChoosePathButtonMessage.class, ChoosePathButtonMessage::buffer, ChoosePathButtonMessage::new,
                ChoosePathButtonMessage::handler);
    }

}
