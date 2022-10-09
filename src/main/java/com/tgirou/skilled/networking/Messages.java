package com.tgirou.skilled.networking;

import com.tgirou.skilled.SkilledMod;
import com.tgirou.skilled.networking.packet.ItemStackSyncPacket;
import com.tgirou.skilled.networking.packet.OpenSkilledWindowPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SkilledMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;
        net.messageBuilder(OpenSkilledWindowPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenSkilledWindowPacket::new)
                .encoder(OpenSkilledWindowPacket::toBytes)
                .consumer(OpenSkilledWindowPacket::handle)
                .add();
        net.messageBuilder(ItemStackSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncPacket::new)
                .encoder(ItemStackSyncPacket::toBytes)
                .consumer(ItemStackSyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
