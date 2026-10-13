package io.github.mrbest2525.betraknockbackstick;

import io.github.mrbest2525.betraknockbackstick.recipe.KnockbackStickRecipe;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jspecify.annotations.Nullable;

public class BetraKnockbackStick implements ModInitializer {
    public static final String MOD_ID = "betra-knockback-stick";
    
    @Nullable
    public static RecipeSerializer<KnockbackStickRecipe> KNOCKBACK_COMBINE_SERIALIZER;
    
    @Override
    public void onInitialize() {
        KNOCKBACK_COMBINE_SERIALIZER = Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                Identifier.fromNamespaceAndPath(MOD_ID, "crafting_special_knockback_stick"),
                KnockbackStickRecipe.SERIALIZER
        );
    }
}
