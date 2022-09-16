package com.tgirou.skilled.handlers;

import com.tgirou.skilled.skills.AbstractSkill;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class LevelHandler {

    public static void levelUp(Player player, Integer experienceLevel) {
            float f = experienceLevel > 100 ? 1.0F : (float)experienceLevel / 100.0F;
            player.level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.PLAYER_LEVELUP,
                    player.getSoundSource(),
                    f * 0.75F,
                    1.0F
            );
            player.sendMessage(
                    new TextComponent("Mineur level up ! Niveau : " + experienceLevel),
                    Util.NIL_UUID
            );
    }

    /**
     * Returns the level achieved for a skill based on the experience earned.
     * @param exp earned for this skill
     * @return the level achieved
     */
    public static Integer getLevel(Integer exp) {
        int level = 0;
        while ((level < AbstractSkill.expTable.length) && (exp > AbstractSkill.expTable[level])) {
            ++level;
        }
        if (level == 0) {
            level++;
        }
        return level;
    }
}
