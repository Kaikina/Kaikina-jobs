package com.tgirou.skilled.skills;

import net.minecraft.nbt.CompoundTag;

public class Skill {
    private String name;
    private int level;
    private int exp;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}

    public int getExp() {return exp;}
    public void setExp(int exp) {this.exp = exp;}

    public Skill(String name, int level, int exp) {
        this.name = name;
        this.level = level;
        this.exp = exp;
    }

    public void copyFrom(Skill source) {
        this.name = source.name;
        this.level = source.level;
        this.exp = source.exp;
    }

    public void saveNBT(CompoundTag compound) {
        compound.putString("name", name);
        compound.putInt("level", level);
        compound.putInt("exp", exp);
    }

    public void loadNBT(CompoundTag compound) {
        name = compound.getString("name");
        level = compound.getInt("level");
        exp = compound.getInt("exp");
    }

    public void addExp(int exp) {
        this.exp += exp;
    }


}
