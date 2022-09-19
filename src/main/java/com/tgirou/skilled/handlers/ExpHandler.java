package com.tgirou.skilled.handlers;

import com.tgirou.skilled.api.util.Utils;
import com.tgirou.skilled.providers.progression.ProgressionProvider;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class ExpHandler {
    protected static Random random = new Random();

    /**
     * Add experience to a skill to a given player.
     * @param skillName the name of the skill who gain the exp
     * @param exp the amount of experience to add
     * @param player the player who gain the experience
     */
    public static void addExp(String skillName, Integer exp, Player player) {
        // Get player's level before adding exp
        int previousLevel = LevelHandler.getLevel(getExpFor(skillName, player));

        // Apply the multiplier corresponding to the player's level
        exp = Math.round((float) exp * getMultiplier(previousLevel));
        player.sendMessage(
                new TextComponent("Multiplicateur : x" + getMultiplier(previousLevel) + ". Giving " + exp + " xp."),
                Util.NIL_UUID
        );

        // Add the multiplied exp
        setExpFor(skillName, getExpFor(skillName, player) + exp, player);

        // Check if player has leveled up
        levelingUp(skillName, player, previousLevel);
    }

    /**
     * Checks if player has leveled up and if so, manage the leveling triggers.
     * @param skillName the skill being potentially leveled up
     * @param player the player leveling
     * @param previousLevel the player's previous level before earning experience
     */
    public static void levelingUp(String skillName, Player player, Integer previousLevel) {
        int experienceLevel = LevelHandler.getLevel(getExpFor(skillName, player));
        if (experienceLevel > previousLevel) {
            LevelHandler.levelUp(player, experienceLevel);
        }
    }

    /**
     * Update the experience earned for a skill.
     * @param skillName the name of the skill
     * @param exp the amount for experience
     * @param player the player who gain the experience
     */
    public static void setExpFor(String skillName, Integer exp, Player player) {
        player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent(progressionManager ->
                progressionManager.getProgression().getSkillsProgression().replace(skillName, exp));
    }

    /**
     * Returns the experience earned for a skill or null if that skill does not exist.
     * @param skillName the name of the skill
     * @return the amount of experience if the skill exist, null if it does not
     */
    public static Integer getExpFor(String skillName, Player player) {
        return player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).map(progressionManager ->
                progressionManager.getProgression().getSkillsProgression().get(skillName)).orElse(null);
    }

    /**
     * Returns exp multiplier depending on player level.
     * @param level of player
     * @return multiplier value
     */
    private static float getMultiplier(int level) {
        if (level >= 80) {
            return 5f + random.nextFloat() * (50f - 5f);
        } else if (level >= 70) {
            return 4.5f + random.nextFloat() * (25f - 4.5f);
        } else if (level >= 60) {
            return 4f + random.nextFloat() * (25f - 4f);
        } else if (level >= 50) {
            return 3.5f + random.nextFloat() * (5f - 3.5f);
        } else if (level >= 40) {
            return 3f + random.nextFloat() * (5f - 3f);
        } else if (level >= 30) {
            return 2.5f + random.nextFloat() * (5f - 2.5f);
        } else if (level >= 20) {
            return 2f + random.nextFloat() * (2.5f - 2f);
        } else if (level >= 10) {
            return 1.5f;
        } else {
            return 1f;
        }
    }

}
