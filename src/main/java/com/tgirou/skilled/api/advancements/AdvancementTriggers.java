package com.tgirou.skilled.api.advancements;

import com.tgirou.skilled.api.advancements.first_miner_block_broken.FirstMinerBlockBrokenTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementTriggers {
    public static final FirstMinerBlockBrokenTrigger FIRST_MINER_BLOCK_BROKEN = new FirstMinerBlockBrokenTrigger();

    public static void preInit() {
        CriteriaTriggers.register(FIRST_MINER_BLOCK_BROKEN);
    }
}
