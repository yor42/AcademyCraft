package cn.academy.support.gtceu;

import cn.academy.support.BlockConverterBase;
import cn.academy.support.ic2.TileEUOutput;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * 
 * @author KSkun
 */
public class BlockGTEUOutput extends BlockConverterBase {

    public BlockGTEUOutput() {
        super( "IF", "GTEU", TileGTEUOutput.class);
    }
    
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGTEUOutput();
    }

}