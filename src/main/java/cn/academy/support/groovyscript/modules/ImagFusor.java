package cn.academy.support.groovyscript.modules;

import cn.academy.Main;
import cn.academy.crafting.ImagFusorRecipes;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.Example;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@RegistryDescription(linkGenerator = Main.MODID)
public class ImagFusor extends StandardListRegistry<ImagFusorRecipes.IFRecipe> {
    @Override
    public Collection<ImagFusorRecipes.IFRecipe> getRecipes() {
        return ImagFusorRecipes.INSTANCE.getAllRecipe();
    }

    @MethodDescription(description = "groovyscript.wiki.academy.imag_fusor.add", example = @Example("item('academy:crystal_normal'), item('academy:crystal_low'), 3000"), type = MethodDescription.Type.ADDITION)
    public void addRecipe(ItemStack output, IIngredient input, int liquidAmount){
        for(ItemStack stack:input.getMatchingStacks()) {
            this.add(new ImagFusorRecipes.IFRecipe(stack, liquidAmount, output));
        }
    }

    @MethodDescription(description = "groovyscript.wiki.academy.imag_fusor.remove", example = @Example("item('academy:crystal_normal')"), type = MethodDescription.Type.REMOVAL)
    public void removeByInput(ItemStack input) {
        this.remove(ImagFusorRecipes.INSTANCE.getRecipe(input));
    }

}
