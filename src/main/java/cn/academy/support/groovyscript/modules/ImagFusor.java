package cn.academy.support.groovyscript.modules;

import cn.academy.AcademyCraft;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.support.groovyscript.AcademyCraftGroovyPlugin;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import net.minecraft.item.ItemStack;

import java.util.Collection;

@RegistryDescription(linkGenerator = AcademyCraft.MODID)
public class ImagFusor extends StandardListRegistry<ImagFusorRecipes.IFRecipe> {
    @Override
    public Collection<ImagFusorRecipes.IFRecipe> getRecipes() {
        return ImagFusorRecipes.INSTANCE.getAllRecipe();
    }

    @MethodDescription(example = @Example("item('academy:crystal_normal'), item('academy:crystal_low'), 3000"), type = MethodDescription.Type.ADDITION)
    public void addRecipe(ItemStack output, IIngredient input, int liquidAmount){
        recipeBuilder().fluid(liquidAmount).input(input).output(output).register();
    }

    @MethodDescription(example = @Example("item('academy:crystal_normal')"))
    public void removeByInput(ItemStack input) {
        this.remove(ImagFusorRecipes.INSTANCE.getRecipe(input));
    }

    @RecipeBuilderDescription(example = {
            @Example(".input(item('minecraft:clay')).output(item('minecraft:diamond')).fluid(1000)"),
            @Example(".input(item('minecraft:clay')).output(item('minecraft:diamond')).fluid(3000)"),
            @Example(".input(item('minecraft:gold_ingot')).output(item('minecraft:clay') * 2)")
    })
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Property(property = "input", comp = @Comp(eq=1))
    @Property(property = "output", comp = @Comp(eq=1))
    public static class RecipeBuilder extends AbstractRecipeBuilder<ImagFusorRecipes.IFRecipe> {

        @Property(defaultValue = "1000", comp = @Comp(gt = 0))
        private int fluidAmount = 1000;

        @RecipeBuilderMethodDescription(field = "fluidAmount")
        public RecipeBuilder fluid(int fluid) {
            this.fluidAmount = fluid;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error Adding AcademyCraft Imag Fusor Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1, 1, 1, 1);
            msg.add(fluidAmount <= 0, "fluidAmount must be an integer greater than 0, yet it was {}", fluidAmount);
        }

        @RecipeBuilderRegistrationMethod
        @Override
        public ImagFusorRecipes.IFRecipe register() {
            if (!validate()) return null;
            ImagFusorRecipes.IFRecipe recipe = new ImagFusorRecipes.IFRecipe(input.get(0).toMcIngredient(), input.get(0).getAmount(), fluidAmount, output.get(0));
            AcademyCraftGroovyPlugin.FUSOR.add(recipe);
            return recipe;
        }
    }
}
