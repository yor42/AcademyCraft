package cn.academy.support.gtceu;

import cn.academy.support.BlockConverterBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * 
 * @author KSkun
 */
public class BlockGTEUInput extends BlockConverterBase {

    public BlockGTEUInput() {
        super( "GTEU", "IF", TileGTEUInput.class);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGTEUInput();
    }

}