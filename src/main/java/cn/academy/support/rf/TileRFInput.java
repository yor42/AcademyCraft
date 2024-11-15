package cn.academy.support.rf;

import cn.academy.block.tileentity.TileGeneratorBase;
import cn.lambdalib2.registry.mc.RegTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

import static cn.academy.support.rf.RFSupport.if2rf;
import static cn.academy.support.rf.RFSupport.rf2if;

@RegTileEntity
public class TileRFInput extends TileGeneratorBase implements IEnergyStorage
{
    
    public TileRFInput() {
        super("ac_rf_input", 0, 2000, 100);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityEnergy.ENERGY){
            @SuppressWarnings("unchecked")
            T result = (T) this;
            return result;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive,
            boolean simulate) {
        int amount = (int) rf2if(maxReceive);
        return maxReceive - if2rf(addEnergy(amount, simulate));
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public int getEnergyStored() {
        return if2rf(getEnergy());
    }

    @Override
    public int getMaxEnergyStored() {
        return if2rf(2000);
    }

    @Override
    public double getGeneration(double required) {
        return 0;
    }
    
}