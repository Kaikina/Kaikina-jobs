package com.tgirou.skilled.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final String KEY_CATEGORY = "key.category.skilled";
    public static final String KEY_SKILLED = "key.skilled.skilled";
    public static KeyMapping skilledKeyMapping;

    public static void init() {
        skilledKeyMapping = new KeyMapping(KEY_SKILLED, KeyConflictContext.IN_GAME,
                InputConstants.getKey("key.keyboard.j"), KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(skilledKeyMapping);
    }
}
