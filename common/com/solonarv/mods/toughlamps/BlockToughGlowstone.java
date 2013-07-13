package com.solonarv.mods.toughlamps;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockToughGlowstone extends Block {
    
    protected Icon[] icons;
    
    public BlockToughGlowstone(int id, Material mat) {
        super(id, mat);
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
        return this.icons[meta];
    }
    
    @Override
    public void registerIcons(IconRegister ir) {
        this.icons = new Icon[] { ir.registerIcon("tlamp:toughGlowstone"),
                ir.registerIcon("tlamp:toughRSLamp_off"),
                ir.registerIcon("tlamp:toughRSLamp_on") };
    }
    
    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
            int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        switch (world.getBlockMetadata(x, y, z)) {
            case 0: // It's glowstone
                ret.add(new ItemStack(this, 1, 0));
                break;
            case 1: // It's a lamp
            case 2:
                ret.add(new ItemStack(this, 1, 1));
                break;
        }
        return ret;
    }
    
    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x,
            int y, int z) {
        return false;
    }
    
    @Override
    public int getLightOpacity(World world, int x, int y, int z) {
        return 0;
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case 0: // It's glowstone
            case 2: // It's a lamp (on)
                return 15;
            case 1: // It's a lamp (off)
                return 0;
        }
        return 0;
    }
    
    protected void updateOnRedstone(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 1 && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 3);
        } else if (meta == 2 && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 1, 3);
        }
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            this.updateOnRedstone(world, x, y, z);
        }
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z,
            int neighborID) {
        if (!world.isRemote) {
            this.updateOnRedstone(world, x, y, z);
        }
    }
    
}
