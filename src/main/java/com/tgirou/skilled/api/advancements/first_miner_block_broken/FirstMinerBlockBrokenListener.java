package com.tgirou.skilled.api.advancements.first_miner_block_broken;

import com.tgirou.skilled.api.advancements.CriterionListeners;
import net.minecraft.server.PlayerAdvancements;

public class FirstMinerBlockBrokenListener extends CriterionListeners<FirstMinerBlockBrokenCriterionInstance> {
    public FirstMinerBlockBrokenListener(final PlayerAdvancements playerAdvancements) {
        super(playerAdvancements);
    }

    public void trigger() {
        trigger(instance -> true);
    }
}
