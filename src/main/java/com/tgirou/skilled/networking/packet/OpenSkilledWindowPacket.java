package com.tgirou.skilled.networking.packet;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSkilledWindowPacket {
    public OpenSkilledWindowPacket() {

    }

    public OpenSkilledWindowPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server stuff
            ServerPlayer player = context.getSender();
            ServerLevel level = null;
            if (player != null) {
                level = player.getLevel();
//                player.sendMessage(new TranslatableComponent("Skilled Window opened"), Util.NIL_UUID);
            }
        });
        return true;
    }
}
