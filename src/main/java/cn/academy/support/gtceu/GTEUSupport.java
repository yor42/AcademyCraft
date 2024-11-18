package cn.academy.support.gtceu;

import cn.academy.ACBlocks;
import cn.academy.ACItems;
import cn.academy.Main;
import cn.academy.support.EnergyBlockHelper;
import cn.lambdalib2.registry.RegistryCallback;
import cn.lambdalib2.registry.StateEventCallback;
import cn.lambdalib2.util.SideUtils;
import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.items.MetaItems.POWER_UNIT_LV;

public class GTEUSupport {

    public static final String GTCEU_MODID = "gregtech";
    static final String GTCEU_IFACE = "gregtech.api.capability.IEnergyContainer";

    public static final BlockGTEUInput gteuInput = new BlockGTEUInput();
    public static final BlockGTEUOutput gteuOutput = new BlockGTEUOutput();
    public static final ItemBlock item_gteuInput = new ItemBlock(gteuInput);
    public static final ItemBlock item_gteuOutput = new ItemBlock(gteuOutput);

    /**
     * The convert rate from GTEU to IF(1IF = <CONV_RATE>EU).
     */
    public static final double CONV_RATE = 1;

    public static double eu2if(double euEnergy) {
        return euEnergy / CONV_RATE;
    }

    public static double if2eu(double ifEnergy) {
        return ifEnergy * CONV_RATE;
    }

    @Optional.Method(modid= GTCEU_MODID)
    @StateEventCallback
    public static void init(FMLInitializationEvent event){
        EnergyBlockHelper.register(new GTEUSinkManager());
        EnergyBlockHelper.register(new GTEUSourceManager());

        GameRegistry.addShapedRecipe(new ResourceLocation("academy","gteu_input"), null,
                new ItemStack(gteuInput), "abc", " d ",
                'a', ACItems.energy_unit, 'b', ACBlocks.machine_frame,
                'c', POWER_UNIT_LV, 'd', ACItems.energy_convert_component);
        GameRegistry.addShapedRecipe(new ResourceLocation("academy","gteu_output"), null,
                new ItemStack(gteuOutput), "abc", " d ",
                'a', MACHINE_CASING, 'b', ACBlocks.machine_frame,
                'c', POWER_UNIT_LV, 'd', ACItems.energy_convert_component);

        GameRegistry.addShapedRecipe(new ResourceLocation("academy","gteu_input_output"), null,
                new ItemStack(gteuInput),"X",'X',new ItemStack(gteuOutput));
        GameRegistry.addShapedRecipe(new ResourceLocation("academy","gteu_output_input"), null,
                new ItemStack(gteuOutput),"X",'X',new ItemStack(gteuInput));

        Main.log.info("GTCEU API Support has been loaded.");
    }


    @Optional.Method(modid= GTCEU_MODID)
    @RegistryCallback
    private static void registerBlocks(RegistryEvent.Register<Block> event) {
        gteuInput.setRegistryName("academy:gteu_input");
        gteuInput.setTranslationKey("ac_gteu_input");
        event.getRegistry().register(gteuInput);

        gteuOutput.setRegistryName("academy:gteu_output");
        gteuOutput.setTranslationKey("ac_gteu_output");
        event.getRegistry().register(gteuOutput);
    }

    @Optional.Method(modid= GTCEU_MODID)
    @RegistryCallback
    private static void registerItems(RegistryEvent.Register<Item> event){
        item_gteuInput.setRegistryName(gteuInput.getRegistryName());
        item_gteuInput.setTranslationKey(gteuInput.getTranslationKey());
        event.getRegistry().register(item_gteuInput);
        if(SideUtils.isClient()){
            ModelLoader.setCustomModelResourceLocation(item_gteuInput, 0,
                    new ModelResourceLocation("academy:gteu_input", "inventory"));
        }


        item_gteuOutput.setRegistryName(gteuOutput.getRegistryName());
        item_gteuOutput.setTranslationKey(gteuOutput.getTranslationKey());
        event.getRegistry().register(item_gteuOutput);
        if(SideUtils.isClient()){
            ModelLoader.setCustomModelResourceLocation(item_gteuOutput, 0,
                    new ModelResourceLocation("academy:gteu_output", "inventory"));
        }
    }

}
