package cn.academy.support.groovyscript.modules;

import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.MetalFormerRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MetalFormer extends StandardListRegistry<MetalFormerRecipes.RecipeObject> {

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

    public void removeEtchbyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.ETCH);
    }

    public void removePlatebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.PLATE);
    }

    public void removeIncisebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.INCISE);
    }

    public void removeRefinebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.REFINE);
    }

    public void removeByInputandMode(ItemStack input, TileMetalFormer.Mode mode) {
        MetalFormerRecipes.RecipeObject recipe;
        recipe = MetalFormerRecipes.INSTANCE.removebyInput(input, mode);

        if (recipe != null) {
            this.addBackup(recipe);
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

    @Override
    public Collection<MetalFormerRecipes.RecipeObject> getRecipes() {
        return MetalFormerRecipes.INSTANCE.getAllRecipes();
    }
}
