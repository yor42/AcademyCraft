package cn.academy.support.groovyscript.modules;

import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.crafting.MetalFormerRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;

public class MetalFormer extends VirtualizedRegistry<MetalFormerRecipes.RecipeObject> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(MetalFormerRecipes.INSTANCE::remove);
        this.restoreFromBackup().forEach(MetalFormerRecipes.INSTANCE::add);
    }

    public void addEtchRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            MetalFormerRecipes.INSTANCE.add(stack, output, TileMetalFormer.Mode.ETCH);
        }
    }

    public void addInciseRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            MetalFormerRecipes.INSTANCE.add(stack, output, TileMetalFormer.Mode.INCISE);
        }
    }

    public void addPlateRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            MetalFormerRecipes.INSTANCE.add(stack, output, TileMetalFormer.Mode.PLATE);
        }
    }

    public void removeByInput(IIngredient input){
        for(ItemStack stack:input.getMatchingStacks()) {
            MetalFormerRecipes.INSTANCE.removebyInput(stack);
        }
    }
}
