package io.github.mrbest2525.betraknockbackstick.client;

import io.github.mrbest2525.betraknockbackstick.BetraKnockbackStick;
import io.github.mrbest2525.betraknockbackstick.recipe.KnockbackStickRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                // ここで特殊レシピ（CustomRecipe）のJSONを出力する
                // 特殊レシピはシリアライザのタイプ名のみを指定して出力します
                output.accept(
                        ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(BetraKnockbackStick.MOD_ID, "knockback_stick")),
                        new KnockbackStickRecipe(),
                        null
                );
            }
        };
    }
    
    @Override
    public String getName() {
        return "ModRecipeProvider";
    }
}
