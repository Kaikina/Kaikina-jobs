package com.tgirou.skilled.handlers;

import com.tgirou.skilled.client.gui.ChoosePath;
import com.tgirou.skilled.skills.AbstractSkill;
import io.netty.buffer.Unpooled;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class LevelHandler {

    /**
     * Method to call when a player has leveled up. Plays a leveling up sound to the player.
     * @param player that has leveled up
     * @param experienceLevel his new level
     */
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
                new TextComponent("Level up ! Niveau : " + experienceLevel),
                Util.NIL_UUID
        );
        BlockPos blockPos = new BlockPos(player.getX(), player.getY(), player.getZ());

        // Open Choose Path GUI
        NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return new TextComponent("ChoosePath");
            }
            @Override
            public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
                return new ChoosePath(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(blockPos));
            }
        }, blockPos);
    }

    /**
     * Returns the level achieved for a skill based on the experience earned.
     * @param exp earned for this skill
     * @return the level achieved
     */
    public static Integer getLevel(Integer exp) {
        int level = 0;
        while ((level < AbstractSkill.expTable.length) && (exp >= AbstractSkill.expTable[level])) {
            ++level;
        }
        if (level == 0) {
            level++;
        }
        return level;
    }

    /**
     * Sets the level of a player for a skill.
     * @param skillName the skill to set the level for
     * @param level the level to set
     * @param player the player to set the skill's level for
     */
    public static void setLevelFor(String skillName, Integer level, Player player) {
        // Set player's new level
        ExpHandler.setExpFor(skillName, AbstractSkill.expTable[level - 1], player);
        // Manager leveling triggers
        ExpHandler.levelingUp(skillName, player, 1);
    }
}
