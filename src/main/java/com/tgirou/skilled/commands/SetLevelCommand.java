package com.tgirou.skilled.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.tgirou.skilled.api.util.Constants;
import com.tgirou.skilled.handlers.ExpHandler;
import com.tgirou.skilled.handlers.LevelHandler;
import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Arrays;
import java.util.Collection;

public class SetLevelCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> setLevelCommand = Commands.literal("skilled")
                .requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("setLevel", StringArgumentType.word())
                        .suggests(Constants.SUGGEST_SKILLED_COMMANDS)
                        .then(Commands.argument("miner", StringArgumentType.word())
                                .suggests(Constants.SUGGEST_SKILLED_SKILLS)
                                .then(Commands.argument("level", IntegerArgumentType.integer(1, 100))
                                        .executes(commandContext -> {
                                            ServerPlayer player = commandContext.getSource().getPlayerOrException();
                                            Integer levelToSet = IntegerArgumentType.getInteger(commandContext, "level");
                                            LevelHandler.setLevelFor("miner", levelToSet, player);
                                            player.sendMessage(
                                                    new TextComponent("Niveau mineur défini à " + levelToSet),
                                                    Util.NIL_UUID
                                            );
                                            return 1;
                                        })
                                )
                        )
                )
                .executes(commandContext -> {
                    commandContext.getSource().getPlayerOrException().sendMessage(
                            new TextComponent("Incorrect command !"),
                            Util.NIL_UUID
                    );
                    return 1;
                });
        commandDispatcher.register(setLevelCommand);
    }
}
