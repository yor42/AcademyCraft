package cn.academy.support.groovyscript.modules;

import cn.academy.Main;
import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.crafting.MetalFormerRecipes;
import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.Example;
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

@RegistryDescription(linkGenerator = Main.MODID)
public class MetalFormer extends StandardListRegistry<MetalFormerRecipes.RecipeObject> {

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.etch", example = @Example("item('minecraft:stonebrick'), item('minecraft:stonebrick', 3)"), type = MethodDescription.Type.ADDITION)
    public void addEtchRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.incise", example = @Example("block('minecraft:cobblestone'), item('minecraft:stone_slab', 3)"), type = MethodDescription.Type.ADDITION)
    public void addInciseRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.plate", example = @Example("ore('ingotIron'), item('academy:reinforced_iron_plate')"), type = MethodDescription.Type.ADDITION)
    public void addPlateRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.add.refine", example = @Example("ore('oreDiamond'), item('minecraft:diamond') * 64"), type = MethodDescription.Type.ADDITION)
    public void addRefineRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.REFINE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.etch", example = @Example("ore('oreDiamond')"), type = MethodDescription.Type.REMOVAL)
    public void removeEtchByInput(ItemStack input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.plate", example = @Example("ore('oreDiamond')"), type = MethodDescription.Type.REMOVAL)
    public void removePlateByInput(ItemStack input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.incise", example = @Example("ore('oreDiamond')"), type = MethodDescription.Type.REMOVAL)
    public void removeInciseByInput(ItemStack input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.remove.refine", example = @Example("ore('oreDiamond')"), type = MethodDescription.Type.REMOVAL)
    public void removeRefineByInput(ItemStack input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.REFINE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.etch", type = MethodDescription.Type.REMOVAL)
    public void clearEtch(){
        removeByMode(TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.incise", type = MethodDescription.Type.REMOVAL)
    public void clearIncise(){
        removeByMode(TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.plate", type = MethodDescription.Type.REMOVAL)
    public void clearPlate(){
        removeByMode(TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(description = "groovyscript.wiki.academy.metal_former.clear.refine", type = MethodDescription.Type.REMOVAL)
    public void clearRefine(){
        removeByMode(TileMetalFormer.Mode.REFINE);
    }

    @GroovyBlacklist
    public void removeByMode(TileMetalFormer.Mode mode){
        Iterator<MetalFormerRecipes.RecipeObject> recipeObjectIterator = MetalFormerRecipes.INSTANCE.getAllRecipes().iterator();
        while(recipeObjectIterator.hasNext()){
            MetalFormerRecipes.RecipeObject recipe = recipeObjectIterator.next();
            if (recipe.mode == mode) {
                this.remove(recipe);
            }
        }
    }

    public void addRecipe(IIngredient input, ItemStack output, TileMetalFormer.Mode mode){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.add(new MetalFormerRecipes.RecipeObject(stack, output, mode));
        }
    }

    @Override
    public boolean add(MetalFormerRecipes.RecipeObject recipe) {
        recipe.setId(this.getRecipes().size());
        return super.add(recipe);
    }

    @GroovyBlacklist
    public void removeByInputAndMode(ItemStack input, TileMetalFormer.Mode mode) {
        this.remove(MetalFormerRecipes.INSTANCE.getRecipe(input, mode));
    }

    @Override
    public Collection<MetalFormerRecipes.RecipeObject> getRecipes() {
        return MetalFormerRecipes.INSTANCE.getAllRecipes();
    }
}
