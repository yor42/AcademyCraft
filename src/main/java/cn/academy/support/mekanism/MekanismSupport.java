package cn.academy.support.mekanism;

import cn.academy.ACBlocks;
import cn.academy.ACItems;
import cn.lambdalib2.registry.RegistryCallback;
import cn.lambdalib2.registry.StateEventCallback;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class MekanismSupport {

    private static final String MEKANISM_MODID = "mekanism";

    @Optional.Method(modid= MEKANISM_MODID)
    @StateEventCallback
    private static void registerCompat(FMLInitializationEvent event) {
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACBlocks.item_crystal_ore), new ItemStack(ACItems.crystal_low, 4));

        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACBlocks.item_imagsil_ore), new ItemStack(ACItems.imag_silicon_ingot, 2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACItems.imag_silicon_ingot), new ItemStack(ACItems.wafer, 4));

        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACItems.crystal_low, 8), new ItemStack(ACItems.crystal_normal, 1));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACItems.crystal_normal, 8), new ItemStack(ACItems.crystal_pure, 1));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ACBlocks.item_reso_ore), new ItemStack(ACItems.reso_crystal, 3));
    }

}
