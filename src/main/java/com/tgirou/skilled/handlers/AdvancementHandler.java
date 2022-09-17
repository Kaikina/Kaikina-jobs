package com.tgirou.skilled.handlers;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class AdvancementHandler {
    AdvancementHandler(){}

    public static void handleAdvancement(ServerPlayer sp, ResourceLocation advancement) {
        Advancement adv = sp.server.getAdvancements().getAdvancement(advancement);
        if (adv != null) {
            AdvancementProgress ap = sp.getAdvancements().getOrStartProgress(adv);
            if (!ap.isDone()) {
                for (String s : ap.getRemainingCriteria()) sp.getAdvancements().award(adv, s);
            }
        }
    }
}
