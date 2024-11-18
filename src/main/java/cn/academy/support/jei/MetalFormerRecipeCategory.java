package cn.academy.support.jei;

import cn.academy.ACBlocks;
import cn.academy.crafting.MetalFormerRecipes;
import cn.academy.crafting.MetalFormerRecipes.RecipeObject;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
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
public class MetalFormerRecipeCategory extends IACRecipeCategory
{
    public static List<IRecipeWrapper> recipeWrapper = loadCraftingRecipes();
    private static ResourceLocation bg = new ResourceLocation("academy", "textures/guis/nei_metalformer.png");
    private final IGuiHelper guiHelper;

    public MetalFormerRecipeCategory(IGuiHelper guiHelper)
    {
        super(ACBlocks.metal_former);
        this.guiHelper = guiHelper;
    }


    //TODO 物品槽的具体位置还需要调整
    @Override
    public List<SlotPos> getInputs() {
        return Collections.singletonList(new SlotPos(5, 23));
    }


    @Override
    public List<SlotPos> getOutputs() {
        return Collections.singletonList(new SlotPos(71, 23));
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.ac_metal_former.name");
    }

    private static List<IRecipeWrapper> loadCraftingRecipes() {

        List<IRecipeWrapper> lists = new ArrayList<>();
        for(RecipeObject r : MetalFormerRecipes.INSTANCE.getAllRecipes()) {
            lists.add(iIngredients -> {
                List<List<ItemStack>> inputLists = new ArrayList<>();
                inputLists.add(Arrays.asList(r.input.matchingStacks));
                iIngredients.setInputLists(VanillaTypes.ITEM, inputLists);
                iIngredients.setOutput(VanillaTypes.ITEM, r.output);
                //r.mode
            });
        }
        return lists;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return guiHelper.drawableBuilder(bg, 0, 0, 94, 57).setTextureSize(94, 57).build();
    }


}