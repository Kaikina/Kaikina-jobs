package com.tgirou.skilled.api.util;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public final class Constants {
    Constants() {}
    public static final String MOD_ID = "skilled";

    /**
     * Advancement Names
     */
    public static final ResourceLocation FIRST_MINER_ADVANCEMENT = new ResourceLocation("skilled:first_miner_block_broken");


    /**
     * Commands constants
     */
    public static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLED_COMMANDS = (commandContext, suggestionsBuilder) -> {
        return SharedSuggestionProvider.suggest(Arrays.asList("setLevel"), suggestionsBuilder);
    };
    public static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLED_SKILLS = (commandContext, suggestionsBuilder) -> {
        return SharedSuggestionProvider.suggest(Arrays.asList("miner"), suggestionsBuilder);
    };
}
