package com.tgirou.skilled.events;

import com.tgirou.skilled.defaults.DefaultAdvancementsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class GatherDataHandler {
    public static void dataGeneratorSetup(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        generator.addProvider(new DefaultAdvancementsProvider(generator, event.getExistingFileHelper()));
    }
}
