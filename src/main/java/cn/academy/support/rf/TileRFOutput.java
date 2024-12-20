package cn.academy.support.rf;

import cn.academy.block.tileentity.TileReceiverBase;
import cn.lambdalib2.registry.mc.RegTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

import static cn.academy.support.rf.RFSupport.if2rf;
import static cn.academy.support.rf.RFSupport.rf2if;

@RegTileEntity
public class TileRFOutput extends TileReceiverBase implements IEnergyStorage
{

    public TileRFOutput() {
        super("ac_rf_output", 0, 2000, 100);
    }
    
    @Override
    public void update() {
        super.update();
        World world = getWorld();
        if (world.isRemote) {
            return;
        }
        for(EnumFacing dir : EnumFacing.VALUES) {
            BlockPos pos = this.pos.add(dir.getDirectionVec());
            TileEntity te = world.getTileEntity(pos);
            if (!(te instanceof IEnergyStorage) || !(energy > 0)) {
                continue;
            }
            IEnergyStorage receiver = (IEnergyStorage) te;
            if (!receiver.canReceive()) {
                continue;
            }
            int req = receiver.getMaxEnergyStored() - receiver.getEnergyStored();
            req = Math.min(if2rf(energy), req);
            energy -= rf2if(receiver.receiveEnergy(req, false));
        }
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityEnergy.ENERGY){
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int e = (int) energy;
        if(!simulate) {
            energy -= rf2if(maxExtract);
            if(energy < 0) energy = 0;
        }
        return Math.min(if2rf(e), maxExtract);
    }

    @Override
    public int getEnergyStored() {
        return if2rf(energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return if2rf(2000);
    }

}