package com.tgirou.skilled.defaults;

import com.tgirou.skilled.api.advancements.first_miner_block_broken.FirstMinerBlockBrokenCriterionInstance;
import com.tgirou.skilled.api.util.Constants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DefaultAdvancementsProvider extends AdvancementProvider {
    public DefaultAdvancementsProvider(@NotNull final DataGenerator generatorIn,
                                       @NotNull final ExistingFileHelper fileHelperIn)
    {
        super(generatorIn, fileHelperIn);
    }

    @Override
    public @NotNull String getName()
    {
        return "DefaultAdvancementsProvider";
    }

    @Override
    protected void registerAdvancements(@NotNull final Consumer<Advancement> consumer,
                                        @NotNull final ExistingFileHelper fileHelper)
    {
        final String GROUP = "skilled/";
        final Advancement root = Advancement.Builder.advancement()
                .display(Items.DIAMOND_PICKAXE,
                        new TranslatableComponent("advancements.skilled.root.title"),
                        new TranslatableComponent("advancements.skilled.root.description"),
                        new ResourceLocation("textures/block/light_gray_wool.png"),
                        FrameType.TASK, true, true, false)
                .addCriterion("first_miner_block_broken", new FirstMinerBlockBrokenCriterionInstance())
                .save(consumer, new ResourceLocation(Constants.MOD_ID, GROUP + "root"), fileHelper);
    }
}
