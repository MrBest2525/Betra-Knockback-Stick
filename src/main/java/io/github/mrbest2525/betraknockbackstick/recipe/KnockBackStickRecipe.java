package io.github.mrbest2525.betraknockbackstick.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KnockBackStickRecipe extends CustomRecipe {
    
    public static final MapCodec<KnockBackStickRecipe> MAP_CODEC = MapCodec.unit(
            KnockBackStickRecipe::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, KnockBackStickRecipe> STREAM_CODEC = StreamCodec.unit(
            new KnockBackStickRecipe()
    );
    public static final RecipeSerializer<KnockBackStickRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    
    @Nullable
    private Holder<Enchantment> cachedKnockbackHolder = null;
    
    public KnockBackStickRecipe() {
    
    }
    
    @Override
    public boolean isSpecial() {
        return false;
    }
    
    @Override
    public boolean showNotification() {
        return true;
    }
    
    @Override
    public String group() {
        return "KnockBack Stick";
    }
    
    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }
    
    @Override
    public PlacementInfo placementInfo() {
        return super.placementInfo();
    }
    
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) return false;
        
        List<ItemStack> notEmptyItems = new ArrayList<>();
        input.items().forEach(itemStack -> {
            if (!itemStack.isEmpty()) {
                notEmptyItems.add(itemStack);
            }
        });
        
        ItemStack item1 = notEmptyItems.get(0);
        ItemStack item2 = notEmptyItems.get(1);
        
        if (!(item1.is(Items.STICK) && item2.is(Items.STICK))) return false;
        
        var knockbackHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK);
        this.cachedKnockbackHolder = knockbackHolder;
        
        if (item1.getEnchantments().getLevel(knockbackHolder) == 0 && item2.getEnchantments().getLevel(knockbackHolder) == 0)
            return true;
        
        int knockbackLevel = item1.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(knockbackHolder);
        
        return item2.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(knockbackHolder) == knockbackLevel;
    }
    
    @Override
    public ItemStack assemble(CraftingInput input) {
        if (this.cachedKnockbackHolder == null) return ItemStack.EMPTY;
        
        List<ItemStack> notEmptyItems = new ArrayList<>();
        input.items().forEach(itemStack -> {
            if (!itemStack.isEmpty()) {
                notEmptyItems.add(itemStack);
            }
        });
        
        ItemStack item1 = notEmptyItems.get(0);
        ItemStack item2 = notEmptyItems.get(1);
        ItemStack resultStick = item1.copy();
        
        item2.getComponents().forEach(component -> this.tryApplyDataComponent(component, resultStick));
        
        int currentLevel = item1.getEnchantments().getLevel(this.cachedKnockbackHolder);
        int nextLevel = currentLevel + 1;
        
        var enchants = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        item1.getEnchantments().entrySet().forEach(entry -> enchants.set(entry.getKey(), entry.getIntValue()));
        item2.getEnchantments().entrySet().forEach(entry -> {
            if (enchants.getLevel(entry.getKey()) < entry.getIntValue()) {
                enchants.set(entry.getKey(), entry.getIntValue());
            }
        });
        enchants.set(this.cachedKnockbackHolder, nextLevel);
        
        resultStick.set(DataComponents.ENCHANTMENTS, enchants.toImmutable());
        
        resultStick.setCount(1);
        
        return resultStick;
    }
    
    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
    
    private <T> void tryApplyDataComponent(TypedDataComponent<T> component, ItemStack resultStick) {
        if (resultStick.get(component.type()) == null) resultStick.set(component.type(), component.value());
        if (component.value() instanceof CustomData customData) {
            resultStick.set(DataComponents.CUSTOM_DATA, CustomData.of(resultStick.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().merge(customData.copyTag())));
        }
    }
}
