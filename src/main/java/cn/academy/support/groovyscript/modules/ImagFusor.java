package cn.academy.support.groovyscript.modules;

import cn.academy.crafting.ImagFusorRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;

public class ImagFusor extends VirtualizedRegistry<ImagFusorRecipes.IFRecipe> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void addRecipe(ItemStack output, ItemStack input, int liquidAmount){
        this.addRecipe(new ImagFusorRecipes.IFRecipe(input, liquidAmount, output));
    }

    public void addRecipe(ItemStack output, IIngredient input, int liquidAmount){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.addRecipe(new ImagFusorRecipes.IFRecipe(stack, liquidAmount, output));
        }
    }

    public void addRecipe(ImagFusorRecipes.IFRecipe recipe){
        ImagFusorRecipes.INSTANCE.addRecipe(recipe);
        this.addScripted(recipe);
    }

    public void removeRecipe(ImagFusorRecipes.IFRecipe recipe){
        ImagFusorRecipes.INSTANCE.removeRecipe(recipe);
        this.addBackup(recipe);
    }
}
