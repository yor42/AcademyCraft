/**
 * 
 */
package cn.academy.energy.block.tile.impl;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cn.academy.core.energy.WirelessSystem;
import cn.academy.core.register.ACBlocks;
import cn.academy.energy.block.tile.base.TileNodeBase;
import cn.academy.energy.client.render.tile.RenderMatrix;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegTileEntity;
import cn.liutils.util.DebugUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeathFolD
 *
 */
@RegistrationClass
@RegTileEntity
@RegTileEntity.HasRender
public class TileMatrix extends TileNodeBase {
	
	@RegTileEntity.Render
	@SideOnly(Side.CLIENT)
	public static RenderMatrix render;
	
	String channelToLoad, pwdToLoad;
	
	public TileMatrix() {
		super(100000, 512, 30);
	}
	
	public void onBreak() {
		String str = this.getChannel();
		if(str != null) {
			WirelessSystem.removeChannel(worldObj, str);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
	
	//Net info read&write
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(channelToLoad != null && pwdToLoad != null) {
			System.out.println("Restoring " + channelToLoad + " " + DebugUtils.formatArray(xCoord, yCoord, zCoord));
			WirelessSystem.registerNode(this, channelToLoad);
			WirelessSystem.setPassword(worldObj, channelToLoad, pwdToLoad);
			channelToLoad = pwdToLoad = null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		boolean b = WirelessSystem.isTileRegistered(this);
		tag.setBoolean("netLoaded", b);
		if(b) {
			String channel = WirelessSystem.getTileChannel(this);
			tag.setString("netChannel", channel);
			tag.setString("netPwd", WirelessSystem.getPassword(worldObj, channel));
		}
    }
	
    @Override
	public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        boolean b = tag.getBoolean("netLoaded");
        if(b) {
        	channelToLoad = tag.getString("netChannel");
        	pwdToLoad = tag.getString("netPwd");
        }
    }

	
	//Head block redirection
	@Override
	public double getEnergy() {
		return getHead().energy;
	}
	
	@Override
	public void setEnergy(double value) {
		getHead().rawSetEnergy(value);
	}
	
	private void rawSetEnergy(double value) {
		super.setEnergy(value);
	}
	
	private TileMatrix getHead() {
		int[] c = ACBlocks.grid.getOrigin(worldObj, xCoord, yCoord, zCoord, getBlockMetadata());
		TileEntity te = worldObj.getTileEntity(c[0], c[1], c[2]);
		return (TileMatrix) (te instanceof TileMatrix ? te : this);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return INFINITE_EXTENT_AABB;
    }

	@Override
	public double getSearchRange() {
		return 0;
	}

}
