package com.tgirou.skilled.skills;

public class Fisherman {
    private int level;
    private int xp;

    public final String name = "Fisherman";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Fisherman(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }

    public void addExp(int xp) {
        this.xp += xp;
    }
}
