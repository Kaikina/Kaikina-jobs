package com.tgirou.skilled.data;

import com.tgirou.skilled.skills.Skill;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Skill> SKILL = CapabilityManager.get(new CapabilityToken<>(){});

    private Skill skill = null;
    private final LazyOptional<Skill> opt = LazyOptional.of(this::createSkill);

    @Nonnull
    private Skill createSkill() {
        if (skill == null) {
            skill = new Skill("test", 0, 0);
        }
        return skill;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == SKILL) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        createSkill().saveNBT(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSkill().loadNBT(nbt);
    }
}
