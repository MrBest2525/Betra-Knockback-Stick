package io.github.mrbest2525.betraknockbackstick.client;

import io.github.mrbest2525.betraknockbackstick.BetraKnockBackStick;
import io.github.mrbest2525.betraknockbackstick.recipe.KnockBackStickRecipe;
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
                        ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(BetraKnockBackStick.MOD_ID, "knockback_stick")),
                        new KnockBackStickRecipe(),
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
