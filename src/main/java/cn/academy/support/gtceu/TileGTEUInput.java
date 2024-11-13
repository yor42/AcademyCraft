package cn.academy.support.gtceu;

import cn.academy.block.tileentity.TileGeneratorBase;
import cn.academy.support.ic2.IC2Support;
import cn.lambdalib2.registry.mc.RegTileEntity;
import gregtech.api.GTValues;
import gregtech.api.capability.FeCompat;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EUToFEProvider;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.converter.ConverterTrait;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

import static cn.academy.support.gtceu.GTEUSupport.*;

@RegTileEntity
@Optional.Interface(modid = GTCEU_MODID, iface = GTCEU_IFACE)
public class TileGTEUInput extends TileGeneratorBase implements IEnergyContainer {
    public TileGTEUInput() {
        super("ac_gteu_input", 0, 2000, 100);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER){
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public double getGeneration(double required) {
        return 0;
    }

    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        if (amperage > 0L && voltage > 0L) {
            if (voltage > this.getInputVoltage()) {
                this.getWorld().setBlockToAir(this.getPos());
                this.getWorld().createExplosion((Entity)null, (double)this.getPos().getX() + 0.5, (double)this.getPos().getY() + 0.5, (double)this.getPos().getZ() + 0.5, 3, ConfigHolder.machines.doesExplosionDamagesTerrain);
                return amperage;
            } else {
                long space = (long) (this.bufferSize - this.getEnergy());
                if (space < voltage) {
                    return 0L;
                } else {
                    long maxAmps = Math.min(amperage, space / voltage);
                    this.changeEnergy(voltage*maxAmps);
                    return maxAmps;
                }
            }
        } else {
            return 0L;
        }
    }

    @Override
    public boolean inputsEnergy(EnumFacing enumFacing) {
        return true;
    }

    @Override
    public long changeEnergy(long delta) {
        return (long) if2eu(addEnergy(eu2if(delta)));
    }

    @Override
    public long getEnergyStored() {
        return (long) if2eu(this.getEnergy());
    }

    @Override
    public long getEnergyCapacity() {
        return (long) if2eu(this.bufferSize);
    }

    @Override
    public long getInputAmperage() {
        return this.getInputVoltage() == 0L ? 0L : 2L;
    }

    @Override
    public long getInputVoltage() {
        return GTValues.V[2];
    }
}
