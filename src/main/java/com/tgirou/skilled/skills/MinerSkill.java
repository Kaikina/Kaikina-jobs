package com.tgirou.skilled.skills;

import com.tgirou.skilled.api.util.LearningType;

import java.util.HashMap;

public class MinerSkill extends AbstractSkill {
    protected LearningType learningType = LearningType.BREAK;

    public MinerSkill() {
        this.blocksTable.put("minecraft:netherrack", new int[]{1, 1});
        this.blocksTable.put("minecraft:andesite", new int[]{2, 2});
        this.blocksTable.put("minecraft:diorite", new int[]{2, 2});
        this.blocksTable.put("minecraft:granite", new int[]{2, 2});
        this.blocksTable.put("minecraft:stone", new int[]{2, 2});
        this.blocksTable.put("minecraft:coal_ore", new int[]{10, 20});
        this.blocksTable.put("minecraft:gold_ore", new int[]{15, 15});
        this.blocksTable.put("minecraft:iron_ore", new int[]{15, 15});
        this.blocksTable.put("minecraft:copper_ore", new int[]{15, 15});
        this.blocksTable.put("minecraft:nether_quartz_ore", new int[]{20, 35});
        this.blocksTable.put("minecraft:lapis_ore", new int[]{20, 35});
        this.blocksTable.put("minecraft:redstone_ore", new int[]{20, 35});
        this.blocksTable.put("minecraft:diamond_ore", new int[]{30, 45});
        this.blocksTable.put("minecraft:emerald_ore", new int[]{30, 45});
    }
}
