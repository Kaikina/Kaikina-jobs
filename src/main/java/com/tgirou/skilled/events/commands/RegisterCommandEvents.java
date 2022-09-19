package com.tgirou.skilled.events.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.tgirou.skilled.commands.SetLevelCommand;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegisterCommandEvents {
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        SetLevelCommand.register(commandDispatcher);
    }
}
