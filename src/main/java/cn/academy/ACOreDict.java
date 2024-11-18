package cn.academy;

import net.minecraftforge.oredict.OreDictionary;

public class ACOreDict {

    public static void InitOredicts(){
        OreDictionary.registerOre("oreImagCrystal", ACBlocks.crystal_ore);
        OreDictionary.registerOre("oreResonantCrystal", ACBlocks.reso_ore);
        OreDictionary.registerOre("oreConstraintMetal", ACBlocks.constraint_metal);
        OreDictionary.registerOre("oreImagSilicon", ACBlocks.imagsil_ore);
        OreDictionary.registerOre("oreSilicon", ACBlocks.imagsil_ore);

        OreDictionary.registerOre("plateIron", ACItems.reinforced_iron_plate);

        OreDictionary.registerOre("ingotSilicon", ACItems.imag_silicon_ingot);
        OreDictionary.registerOre("nuggetSilicon", ACItems.imag_silicon_piece);
        OreDictionary.registerOre("waferSilicon", ACItems.wafer);

        OreDictionary.registerOre("ingotImagSilicon", ACItems.imag_silicon_ingot);
        OreDictionary.registerOre("nuggetImagSilicon", ACItems.imag_silicon_piece);
        OreDictionary.registerOre("waferImagSilicon", ACItems.wafer);

        OreDictionary.registerOre("ingotConstraintMetal", ACItems.constraint_ingot);
        OreDictionary.registerOre("plateConstraintMetal", ACItems.constraint_plate);

        OreDictionary.registerOre("gemResonantCrystal", ACItems.reso_crystal);
        OreDictionary.registerOre("gemImagCrystal", ACItems.crystal_low);
        OreDictionary.registerOre("gemImagCrystal", ACItems.crystal_normal);
        OreDictionary.registerOre("gemImagCrystal", ACItems.crystal_pure);
        OreDictionary.registerOre("gemImagCrystalLow", ACItems.crystal_low);
        OreDictionary.registerOre("gemImagCrystalNormal", ACItems.crystal_normal);
        OreDictionary.registerOre("gemImagCrystalHigh", ACItems.crystal_pure);
    }

}
