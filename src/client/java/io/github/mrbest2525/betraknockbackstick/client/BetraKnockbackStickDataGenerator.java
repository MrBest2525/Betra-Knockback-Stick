package io.github.mrbest2525.betraknockbackstick.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetraKnockbackStickDataGenerator implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        pack.addProvider(ModRecipeProvider::new);
    }
}
