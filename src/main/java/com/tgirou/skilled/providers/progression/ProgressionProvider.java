package com.tgirou.skilled.providers.progression;

import com.tgirou.skilled.progression.ProgressionManager;
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

public class ProgressionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<ProgressionManager> PROGRESSION_MANAGER = CapabilityManager.get(new CapabilityToken<>(){});

    private ProgressionManager skill = null;
    private final LazyOptional<ProgressionManager> opt = LazyOptional.of(this::createProgression);

    @Nonnull
    private ProgressionManager createProgression() {
        if (skill == null) {
            skill = new ProgressionManager();
        }
        return skill;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == PROGRESSION_MANAGER) {
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
        createProgression().saveNBT(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createProgression().loadNBT(nbt);
    }
}
