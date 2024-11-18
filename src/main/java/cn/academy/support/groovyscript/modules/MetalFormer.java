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

    @MethodDescription(example = @Example("item('minecraft:stonebrick'), item('minecraft:stonebrick', 3)"), type = MethodDescription.Type.ADDITION)
    public void addEtchRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(example = @Example("block('minecraft:cobblestone'), item('minecraft:stone_slab', 3)"), type = MethodDescription.Type.ADDITION)
    public void addInciseRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(example = @Example("ore('ingotIron'), item('academy:reinforced_iron_plate')"), type = MethodDescription.Type.ADDITION)
    public void addPlateRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(example = @Example("ore('oreDiamond'), item('minecraft:diamond') * 64"), type = MethodDescription.Type.ADDITION)
    public void addRefineRecipe(IIngredient input, ItemStack output){
        this.addRecipe(input, output, TileMetalFormer.Mode.REFINE);
    }

    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeEtchByInput(IIngredient input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removePlateByInput(IIngredient input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeInciseByInput(IIngredient input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeRefineByInput(IIngredient input){
        this.removeByInputAndMode(input, TileMetalFormer.Mode.REFINE);
    }

    @MethodDescription
    public void removeAllEtch(){
        removeByMode(TileMetalFormer.Mode.ETCH);
    }

    @MethodDescription
    public void removeAllIncise(){
        removeByMode(TileMetalFormer.Mode.INCISE);
    }

    @MethodDescription
    public void removeAllPlate(){
        removeByMode(TileMetalFormer.Mode.PLATE);
    }

    @MethodDescription
    public void removeAllRefine(){
        removeByMode(TileMetalFormer.Mode.REFINE);
    }

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

    public void removeByInputAndMode(IIngredient input, TileMetalFormer.Mode mode) {
        for(ItemStack stack: input.getMatchingStacks()) {
            this.remove(MetalFormerRecipes.INSTANCE.getRecipe(stack, mode));
        }
    }

    @Override
    public boolean add(MetalFormerRecipes.RecipeObject recipe) {
        recipe.setId(this.getRecipes().size());
        return super.add(recipe);
    }

    @Override
    public Collection<MetalFormerRecipes.RecipeObject> getRecipes() {
        return MetalFormerRecipes.INSTANCE.getAllRecipes();
    }
}
