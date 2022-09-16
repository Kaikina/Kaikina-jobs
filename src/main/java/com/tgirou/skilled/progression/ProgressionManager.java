package com.tgirou.skilled.progression;

import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public class ProgressionManager {

    protected Progression progression;

    public ProgressionManager() {
        this.progression = new Progression();
    }

    public Progression getProgression() {
        return this.progression;
    }


    public void copyFrom(ProgressionManager source) {
        this.progression = source.progression;
    }

    public void saveNBT(CompoundTag compound) {
        Map<String, Integer> localProgression = this.progression.getSkillsProgression();
        localProgression.forEach(compound::putInt);
    }

    public void loadNBT(CompoundTag compound) {
        Map<String, Integer> localProgression = this.progression.getSkillsProgression();
        localProgression.forEach((skill, exp) -> this.progression.getSkillsProgression().replace(skill, compound.getInt(skill)));
    }

}
