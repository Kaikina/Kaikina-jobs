package com.tgirou.skilled.handlers;

import com.tgirou.skilled.providers.progression.ProgressionProvider;
import net.minecraft.world.entity.player.Player;

public class ExpHandler {
    /**
     * Add experience to a skill to a given player.
     * @param skillName the name of the skill who gain the exp
     * @param exp the amount of experience to add
     * @param player the player who gain the experience
     */
    public static void addExp(String skillName, Integer exp, Player player) {
        setExpFor(skillName, getExpFor(skillName, player) + exp, player);
    }

    /**
     * Update the experience earned for a skill.
     * @param skillName the name of the skill
     * @param exp the amount for experience
     * @param player the player who gain the experience
     */
    public static void setExpFor(String skillName, Integer exp, Player player) {
        int previousLevel = LevelHandler.getLevel(getExpFor(skillName, player));

        player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).ifPresent((progressionManager) ->
                progressionManager.getProgression().getSkillsProgression().replace(skillName, exp));

        int experienceLevel = LevelHandler.getLevel(getExpFor(skillName, player));

        if (experienceLevel > previousLevel) {
            LevelHandler.levelUp(player, experienceLevel);
        }

    }

    /**
     * Returns the experience earned for a skill or null if that skill does not exist.
     * @param skillName the name of the skill
     * @return the amount of experience if the skill exist, null if it does not
     */
    public static Integer getExpFor(String skillName, Player player) {
        return player.getCapability(ProgressionProvider.PROGRESSION_MANAGER).map((progressionManager) ->
                progressionManager.getProgression().getSkillsProgression().get(skillName)).orElse(null);
    }

}
