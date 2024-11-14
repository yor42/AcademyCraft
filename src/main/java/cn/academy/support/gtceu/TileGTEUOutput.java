package cn.academy.support.gtceu;

import cn.academy.block.tileentity.TileReceiverBase;
import cn.lambdalib2.registry.mc.RegTileEntity;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.common.ConfigHolder;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

import static cn.academy.support.gtceu.GTEUSupport.*;
@RegTileEntity
@Optional.Interface(modid = GTCEU_MODID, iface = GTCEU_IFACE)
public class TileGTEUOutput extends TileReceiverBase implements IEnergyContainer {
    protected long amps = 0L;
    protected long lastEnergyOutputPerSec = 0L;
    protected long energyOutputPerSec = 0L;
    protected int timer;
    public TileGTEUOutput() {
        super("ac_gteu_input", 0, 2000, 100);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER){
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        if (amperage > 0L && voltage > 0L) {
            if (voltage > this.getInputVoltage()) {
                this.getWorld().setBlockToAir(this.getPos());
                this.getWorld().createExplosion((Entity)null, (double)this.getPos().getX() + 0.5, (double)this.getPos().getY() + 0.5, (double)this.getPos().getZ() + 0.5, 3, ConfigHolder.machines.doesExplosionDamagesTerrain);
                return amperage;
            } else {
                long space = (long) (this.getEnergyCapacity() - this.getEnergy());
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
        return false;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public long changeEnergy(long delta) {
        double value = energy - eu2if(delta);
        if(value < 0d) value = 0d;
        energy = value;
        return (long) value;
    }

    @Override
    public long getEnergyStored() {
        return (long) if2eu(this.getEnergy());
    }

    @Override
    public long getEnergyCapacity() {
        return (long) if2eu(this.getMaxEnergy());
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }

    @Override
    public long getInputVoltage() {
        return 0;
    }

    @Override
    public long getOutputAmperage() {
        return 2L;
    }

    @Override
    public long getOutputVoltage() {
        return GTValues.V[2];
    }

    public void update() {
        this.amps = 0L;
        if (!this.getWorld().isRemote) {
            if (this.timer % 20L == 0L) {
                this.lastEnergyOutputPerSec = this.energyOutputPerSec;
                this.energyOutputPerSec = 0L;
            }
            boolean value = this.getEnergyStored() >= this.getOutputVoltage();
            if (value && this.getOutputVoltage() > 0L && this.getOutputAmperage() > 0L) {
                long outputVoltage = this.getOutputVoltage();
                long outputAmperes = Math.min(this.getEnergyStored() / outputVoltage, this.getOutputAmperage());
                if (outputAmperes == 0L) {
                    return;
                }

                long amperesUsed = 0L;
                EnumFacing[] var7 = EnumFacing.VALUES;
                int var8 = var7.length;

                for (EnumFacing side : var7) {
                    if (this.outputsEnergy(side)) {
                        TileEntity tileEntity = this.getNeighbor(side);
                        EnumFacing oppositeSide = side.getOpposite();
                        if (tileEntity != null && tileEntity.hasCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, oppositeSide)) {
                            IEnergyContainer energyContainer = tileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, oppositeSide);
                            if (energyContainer != null && energyContainer.inputsEnergy(oppositeSide)) {
                                amperesUsed += energyContainer.acceptEnergyFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
                                if (amperesUsed == outputAmperes) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (amperesUsed > 0L) {
                    this.changeEnergy(amperesUsed * outputVoltage);
                }
            }

        }
        this.timer++;
    }

    private TileEntity getNeighbor(@NotNull EnumFacing facing) {
        return this.world.getTileEntity(this.pos.offset(facing));
    }
}
