package io.github.mrbest2525.betraknockbackstick;

import io.github.mrbest2525.betraknockbackstick.recipe.KnockBackStickRecipe;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jspecify.annotations.Nullable;

public class BetraKnockBackStick implements ModInitializer {
    public static final String MOD_ID = "betra-knockback-stick";
    
    @Nullable
    public static RecipeSerializer<KnockBackStickRecipe> KNOCKBACK_COMBINE_SERIALIZER;
    
    @Override
    public void onInitialize() {
        KNOCKBACK_COMBINE_SERIALIZER = Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                Identifier.fromNamespaceAndPath(MOD_ID, "crafting_special_knockback_stick"),
                KnockBackStickRecipe.SERIALIZER
        );
    }
}
