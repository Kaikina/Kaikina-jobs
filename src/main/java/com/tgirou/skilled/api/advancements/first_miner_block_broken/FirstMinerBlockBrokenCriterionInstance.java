package com.tgirou.skilled.api.advancements.first_miner_block_broken;

import com.tgirou.skilled.api.util.Constants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;

public class FirstMinerBlockBrokenCriterionInstance extends AbstractCriterionTriggerInstance {
    public FirstMinerBlockBrokenCriterionInstance() {
        super(new ResourceLocation(Constants.MOD_ID, Constants.CRITERION_FIRST_MINER_BLOCK_BROKEN), EntityPredicate.Composite.ANY);
    }
}
