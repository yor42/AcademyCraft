package cn.academy.support.groovyscript.modules;

import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.crafting.MetalFormerRecipes;
import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@RegistryDescription
public class MetalFormer extends StandardListRegistry<MetalFormerRecipes.RecipeObject> {

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.etch", type = MethodDescription.Type.ADDITION)
    public void addEtchRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.ETCH));
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.incise", type = MethodDescription.Type.ADDITION)
    public void addInciseRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.INCISE));
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.plate", type = MethodDescription.Type.ADDITION)
    public void addPlateRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.PLATE));
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.refine", type = MethodDescription.Type.ADDITION)
    public void addRefineRecipe(IIngredient input, ItemStack output){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new MetalFormerRecipes.RecipeObject(stack, output, TileMetalFormer.Mode.REFINE));
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.etch", type = MethodDescription.Type.REMOVAL)
    public void removeEtchbyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.plate", type = MethodDescription.Type.REMOVAL)
    public void removePlatebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.incise", type = MethodDescription.Type.REMOVAL)
    public void removeIncisebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.refine", type = MethodDescription.Type.REMOVAL)
    public void removeRefinebyInput(ItemStack input){
        this.removeByInputandMode(input, TileMetalFormer.Mode.REFINE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.all", type = MethodDescription.Type.REMOVAL)
    public void clearRecipe(){
        Iterator<MetalFormerRecipes.RecipeObject> iterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while (iterator.hasNext()) {
            MetalFormerRecipes.RecipeObject recipe = iterator.next();
            iterator.remove();
            this.addBackup(recipe);
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.etch", type = MethodDescription.Type.REMOVAL)
    public void clearEtch(){
        Iterator<MetalFormerRecipes.RecipeObject> iterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while (iterator.hasNext()) {
            MetalFormerRecipes.RecipeObject recipe = iterator.next();
            if(recipe.mode == TileMetalFormer.Mode.ETCH) {
                iterator.remove();
                this.addBackup(recipe);
            }
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.incise", type = MethodDescription.Type.REMOVAL)
    public void clearIncise(){
        Iterator<MetalFormerRecipes.RecipeObject> iterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while (iterator.hasNext()) {
            MetalFormerRecipes.RecipeObject recipe = iterator.next();
            if(recipe.mode == TileMetalFormer.Mode.INCISE) {
                iterator.remove();
                this.addBackup(recipe);
            }
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.plate", type = MethodDescription.Type.REMOVAL)
    public void clearPlate(){
        Iterator<MetalFormerRecipes.RecipeObject> iterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while (iterator.hasNext()) {
            MetalFormerRecipes.RecipeObject recipe = iterator.next();
            if(recipe.mode == TileMetalFormer.Mode.PLATE) {
                iterator.remove();
                this.addBackup(recipe);
            }
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.refine", type = MethodDescription.Type.REMOVAL)
    public void clearRefine(){
        Iterator<MetalFormerRecipes.RecipeObject> iterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while (iterator.hasNext()) {
            MetalFormerRecipes.RecipeObject recipe = iterator.next();
            if(recipe.mode == TileMetalFormer.Mode.REFINE) {
                iterator.remove();
                this.addBackup(recipe);
            }
        }
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

    @Override
    public Collection<MetalFormerRecipes.RecipeObject> getRecipes() {
        return MetalFormerRecipes.INSTANCE.getAllRecipes();
    }
}
