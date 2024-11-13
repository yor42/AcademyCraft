package cn.academy.support.rf;

import cn.academy.support.EnergyBlockHelper.IEnergyBlockManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author WeAthFolD
 */
public class RFProviderManager implements IEnergyBlockManager {
    
    static final EnumFacing dir = EnumFacing.UP;

    @Override
    public boolean isSupported(TileEntity tile) {
        return asProvider(tile) != null;
    }
    
    private IEnergyStorage asProvider(TileEntity te) {
        return te instanceof IEnergyStorage ? (IEnergyStorage) te : null;
    }

    @Override
    public double getEnergy(TileEntity tile) {
        IEnergyStorage provider = asProvider(tile);
        return provider.getEnergyStored() * RFSupport.CONV_RATE;
    }

    @Override
    public void setEnergy(TileEntity tile, double energy) {
        // NOT SUPPORTED
    }

    @Override
    public double charge(TileEntity tile, double amt, boolean ignoreBandwidth) {
        // NOT SUPPORTED
        return amt;
    }

    @Override
    public double pull(TileEntity tile, double amt, boolean ignoreBandwidth) {
        IEnergyStorage provider = asProvider(tile);
        return provider == null ? 0 : RFSupport.CONV_RATE * provider.extractEnergy((int) (amt / RFSupport.CONV_RATE), false);
    }

}