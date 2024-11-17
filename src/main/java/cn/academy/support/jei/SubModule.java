package cn.academy.support.jei;

import cn.academy.Main;
import cn.academy.core.client.ui.TechUI;
import cn.lambdalib2.cgui.CGuiScreenContainer;
import mezz.jei.api.*;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@JEIPlugin
public class SubModule implements IModPlugin {
    public FusorRecipeCategory fusorRecipeCategory;
    public MetalFormerRecipeCategory mfRecipeCategory;

    public SubModule() {
    }

    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        addMachineRecipes(registry, FusorRecipeCategory.recipeWrapper, fusorRecipeCategory);
        addMachineRecipes(registry, MetalFormerRecipeCategory.recipeWrapper, mfRecipeCategory);

        registry.addAdvancedGuiHandlers(new IAdvancedGuiHandler<TechUI.ContainerUI>() {
            @Override
            public Class<TechUI.ContainerUI> getGuiContainerClass() {
                return TechUI.ContainerUI.class;
            }

            @Override
            public java.util.List<Rectangle> getGuiExtraAreas(TechUI.ContainerUI guiContainer) {

                ArrayList<Rectangle> list = new ArrayList<>();
                TechUI.ContainerUI.InfoArea area = guiContainer.infoPage();

                if(area == null){
                    return list;
                }

                list.add(new Rectangle((int) area.x, (int) area.y, (int) area.transform.width, (int) area.transform.height));
                return list;
            }

            @Nullable
            @Override
            public Object getIngredientUnderMouse(TechUI.ContainerUI guiContainer, int mouseX, int mouseY) {
                return null;
            }
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        fusorRecipeCategory = new FusorRecipeCategory(guiHelper);
        registry.addRecipeCategories(fusorRecipeCategory);

        mfRecipeCategory = new MetalFormerRecipeCategory(guiHelper);
        registry.addRecipeCategories(mfRecipeCategory);
    }

    private void addMachineRecipes(IModRegistry registry, Collection<IRecipeWrapper> wrappers, IACRecipeCategory category) {
        registry.addRecipes(wrappers, category.getUid());
        registry.addRecipeCatalyst(category.getBlockStack(), category.getUid());
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IModPlugin.super.onRuntimeAvailable(jeiRuntime);
    }
}
