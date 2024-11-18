package cn.academy.support.groovyscript.modules;

import cn.academy.crafting.ImagFusorRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class ImagFusor extends StandardListRegistry<ImagFusorRecipes.IFRecipe> {
    @Override
    public Collection<ImagFusorRecipes.IFRecipe> getRecipes() {
        return ImagFusorRecipes.INSTANCE.getAllRecipe();
    }


    public void addRecipe(ItemStack output, IIngredient input, int liquidAmount){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new ImagFusorRecipes.IFRecipe(stack, liquidAmount, output));
        }
    }

    public void removeRecipe(IIngredient input){
        for(ItemStack stack:input.getMatchingStacks()) {
            ImagFusorRecipes.IFRecipe recipe = ImagFusorRecipes.INSTANCE.removeRecipebyInput(stack);
            if (recipe != null){
                this.addBackup(recipe);
            }
        }
    }

    public void addRecipe(ImagFusorRecipes.IFRecipe recipe){
        ImagFusorRecipes.INSTANCE.addRecipe(recipe);
        this.addScripted(recipe);
    }
}
