package cn.academy.support.gtceu;

import cn.academy.support.EnergyBlockHelper.IEnergyBlockManager;
import cn.academy.support.ic2.IC2Support;
import gregtech.api.capability.IEnergyContainer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 */
public class GTEUSourceManager implements IEnergyBlockManager {

    @Override
    public boolean isSupported(TileEntity tile) {
        return asSource(tile) != null;
    }

    IEnergyContainer asSource(TileEntity tile) {
        return tile instanceof IEnergyContainer ? (IEnergyContainer) tile : null;
    }
    
    @Override
    public double getEnergy(TileEntity tile) {
        // NOT SUPPORTED
        return 0;
    }

    @Override
    public void setEnergy(TileEntity tile, double energy) {
        // NOT SUPPORTED
        return;
    }

    @Override
    public double charge(TileEntity tile, double amt, boolean ignoreBandwidth) {
        // NOT SUPPORTED
        return amt;
    }

    @Override
    public double pull(TileEntity tile, double amt, boolean ignoreBandwidth) {
        IEnergyContainer src = asSource(tile);
        if(src != null) {
            double todraw = Math.min(src.getEnergyCanBeInserted(), amt / IC2Support.CONV_RATE);
            src.removeEnergy((long) todraw);
            return todraw * IC2Support.CONV_RATE;
        }
        return 0;
    }

}