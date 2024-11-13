package cn.academy.support.rf;

import cn.academy.support.EnergyBlockHelper.IEnergyBlockManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author WeAthFolD
 */
public class RFReceiverManager implements IEnergyBlockManager {
    
    static final EnumFacing DEFAULT_DIR = EnumFacing.UP;

    @Override
    public boolean isSupported(TileEntity tile) {
        return asReceiver(tile) != null;
    }
    
    private IEnergyStorage asReceiver(TileEntity tile) {
        return tile instanceof IEnergyStorage ? (IEnergyStorage) tile : null;
    }
    
    @Override
    public double getEnergy(TileEntity tile) {
        IEnergyStorage rec = asReceiver(tile);
        return rec == null ? 0 : rec.getEnergyStored() * RFSupport.CONV_RATE;
    }

    @Override
    public void setEnergy(TileEntity tile, double energy) {
        // NOT SUPPORTED
    }

    @Override
    public double charge(TileEntity tile, double amt, boolean ignoreBandwidth) {
        IEnergyStorage rec = asReceiver(tile);
        return rec == null ? amt : amt - rec.receiveEnergy((int) amt, false);
    }

    @Override
    public double pull(TileEntity tile, double amt, boolean ignoreBandwidth) {
        // NOT SUPPORTED
        return 0;
    }

}