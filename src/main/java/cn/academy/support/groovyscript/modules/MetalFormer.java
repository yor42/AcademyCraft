package cn.academy.support.groovyscript.modules;

import cn.academy.Main;
import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.crafting.MetalFormerRecipes;
import cn.academy.support.groovyscript.AcademyCraftGroovyPlugin;
import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.compat.mods.roots.Moss;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
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

    @MethodDescription(example = @Example("item('minecraft:cobblestone'), item('minecraft:stone_slab', 3)"), type = MethodDescription.Type.ADDITION)
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
        this.add(new MetalFormerRecipes.RecipeObject(input.toMcIngredient(), input.getAmount(), output, mode));
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

    @RecipeBuilderDescription(example = {
            @Example(".input(item('minecraft:clay')).output(item('minecraft:diamond')).refine()"),
            @Example(".input(item('minecraft:clay')).output(item('minecraft:diamond')).incise()"),
            @Example(".input(item('minecraft:gold_ingot')).output(item('minecraft:clay') * 2)")
    })
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<MetalFormerRecipes.RecipeObject>{

        @Property(defaultValue = "ETCH", comp = @Comp(not = "null"))
        private TileMetalFormer.Mode MODE = TileMetalFormer.Mode.ETCH;

        @Override
        public String getErrorMsg() {
            return "Error Adding AcademyCraft Metal Former Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
            msg.add(MODE == null, "mode must not be null!");
        }

        @RecipeBuilderMethodDescription
        public RecipeBuilder mode(TileMetalFormer.Mode mode){
            this.MODE = mode;
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder incise(){
            this.MODE = TileMetalFormer.Mode.INCISE;
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder plate(){
            this.MODE = TileMetalFormer.Mode.PLATE;
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder refine(){
            this.MODE = TileMetalFormer.Mode.REFINE;
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder etch(){
            this.MODE = TileMetalFormer.Mode.ETCH;
            return this;
        }

        @Override
        protected int getMaxItemInput() {
            return 1;
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public MetalFormerRecipes.RecipeObject register() {
            if(!validate()){
                return null;
            }
            MetalFormerRecipes.RecipeObject object = new MetalFormerRecipes.RecipeObject(input.get(0).toMcIngredient(), input.get(0).getAmount(), output.get(0), this.MODE);
            AcademyCraftGroovyPlugin.FORMER.add(object);
            return object;
        }
    }
}
