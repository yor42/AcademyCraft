package cn.academy.support.groovyscript.modules;

import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.MetalFormerRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;

public class MetalFormer extends VirtualizedRegistry<MetalFormerRecipes.RecipeObject> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void addEtchRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.ETCH));
        }
    }

    public void addInciseRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.INCISE));
        }
    }

    public void addPlateRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.PLATE));
        }
    }

    public void addRefineRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.REFINE));
        }
    }

    public void removeEtchingbyInput(IIngredient input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.ETCH);
    }

    public void removePlqtingbyInput(IIngredient input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.PLATE);
    }

    public void removeIncisebyInput(IIngredient input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.INCISE);
    }

    public void removeRefinebyInput(IIngredient input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.REFINE);
    }

    public void removeByInputandMode(IIngredient input, TileMetalFormer.Mode mode){
        MetalFormerRecipes.RecipeObject recipe;
        for(ItemStack stack:input.getMatchingStacks()) {
            recipe = MetalFormerRecipes.INSTANCE.removebyInput(stack, mode);

            if (recipe != null){
                this.addBackup(recipe);
            }
        }

    }

    public void addRecipe(MetalFormerRecipes.RecipeObject recipe){
        MetalFormerRecipes.INSTANCE.add(recipe);
        this.addScripted(recipe);
    }

    public void removeRecipe(MetalFormerRecipes.RecipeObject recipe){
        MetalFormerRecipes.INSTANCE.remove(recipe);
        this.addBackup(recipe);
    }
}
