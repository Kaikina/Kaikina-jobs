package com.tgirou.skilled.api.advancements.first_miner_block_broken;

import com.google.gson.JsonObject;
import com.tgirou.skilled.api.advancements.AbstractCriterionTrigger;
import com.tgirou.skilled.api.util.Constants;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class FirstMinerBlockBrokenTrigger extends AbstractCriterionTrigger<FirstMinerBlockBrokenListener, FirstMinerBlockBrokenCriterionInstance> {
    public FirstMinerBlockBrokenTrigger() {
        super(new ResourceLocation(Constants.MOD_ID, Constants.CRITERION_FIRST_MINER_BLOCK_BROKEN), FirstMinerBlockBrokenListener::new);
    }

    /**
     * Triggers the listener checks if there are any listening in
     * @param player the player the check regards
     */
    public void trigger(final ServerPlayer player)
    {
        System.out.println("Advancement triggered !");
        final FirstMinerBlockBrokenListener listener = this.getListeners(player.getAdvancements());
        System.out.println(player.getAdvancements());
        System.out.println(listener);
        if (listener != null)
        {
            listener.trigger();
        }
    }

    @NotNull
    @Override
    public FirstMinerBlockBrokenCriterionInstance createInstance(@NotNull final JsonObject jsonObject,
                                                                 @NotNull final DeserializationContext conditionArrayParser)
    {
        return new FirstMinerBlockBrokenCriterionInstance();
    }
}
