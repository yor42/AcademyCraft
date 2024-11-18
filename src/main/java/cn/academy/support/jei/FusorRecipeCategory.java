package cn.academy.support.jei;

import cn.academy.ACBlocks;
import cn.academy.crafting.ImagFusorRecipes;
import cn.academy.crafting.ImagFusorRecipes.IFRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author KSkun
 *
 */
public class FusorRecipeCategory extends IACRecipeCategory
{
    public static List<IRecipeWrapper> recipeWrapper = loadCraftingRecipes();
    private static ResourceLocation bg = new ResourceLocation("academy", "textures/guis/nei_fusor.png");
    private final IGuiHelper guiHelper;

    public FusorRecipeCategory(IGuiHelper guiHelper)
    {
        super(ACBlocks.imag_fusor);
        this.guiHelper = guiHelper;
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.ac_imag_fusor.name");
    }

    //TODO 物品槽的具体位置还需要调整
    @Override
    public List<SlotPos> getInputs() {
        return Collections.singletonList(new SlotPos(5, 36));
    }


    @Override
    public List<SlotPos> getOutputs() {
        return Collections.singletonList(new SlotPos(93, 36));
    }

    private static List<IRecipeWrapper> loadCraftingRecipes() {
        List<IRecipeWrapper> lists = new ArrayList<>();
        for(IFRecipe r : ImagFusorRecipes.INSTANCE.getAllRecipe()) {
            lists.add(iIngredients -> {
                List<List<ItemStack>> inputLists = new ArrayList<>();
                inputLists.add(Arrays.asList(r.consumeType.matchingStacks));
                iIngredients.setInputLists(VanillaTypes.ITEM, inputLists);
                iIngredients.setOutput(VanillaTypes.ITEM, r.output);
                //r.cost
            });
        }
        return lists;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return guiHelper.drawableBuilder(bg, 0, 0, 115, 66).setTextureSize(115, 66).build();
    }

}