package com.tgirou.skilled.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY = "key.category.skilledmod";
    public static final String KEY_SKILLED = "key.skilledmod.skilled";
    public static KeyMapping skilledKeyMapping;

    public static void init() {
        skilledKeyMapping = new KeyMapping(KEY_SKILLED, KeyConflictContext.IN_GAME,
                InputConstants.getKey("key.keyboard.j"), KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(skilledKeyMapping);
    }
}
