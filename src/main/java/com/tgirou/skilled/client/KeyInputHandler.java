package com.tgirou.skilled.client;

import com.tgirou.skilled.networking.Messages;
import com.tgirou.skilled.networking.packet.OpenSkilledWindowPacket;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.InputEvent;

public class KeyInputHandler {
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.skilledKeyMapping.consumeClick()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Skilled Window opened"),
                        Util.NIL_UUID);
            }
            Messages.sendToServer(new OpenSkilledWindowPacket());
        }
    }
}
