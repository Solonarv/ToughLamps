package com.solonarv.mods.toughlamps;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockToughGlowstone extends Block {
    
    protected Icon[]   icons;
    protected String[] names = { "toughGlowstone", "toughRSLamp(off)",
            "toughRSLamp(on)", "invToughRSLamp(on)", "invToughRSLamp(off)" };
    
    public BlockToughGlowstone(int id, Material mat) {
        super(id, mat);
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
        return meta < 3 ? this.icons[meta] : this.icons[5 - meta];
    }
    
    @Override
    public void registerIcons(IconRegister ir) {
        this.icons = new Icon[] { ir.registerIcon("tlamp:toughGlowstone"),
                ir.registerIcon("tlamp:toughRSLamp_off"),
                ir.registerIcon("tlamp:toughRSLamp_on") };
    }
    
    @Override
    public int damageDropped(int meta) {
        return meta == 2 ? 1 : meta == 4 ? 3 : meta;
    }
    
    @Override
    public int idPicked(World world, int x, int y, int z) {
        return this.blockID;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }
    
    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x,
            int y, int z) {
        return false;
    }
    
    public void getSubBlocks(int id, CreativeTabs tab, List subBlocks) {
        subBlocks.add(new ItemStack(id, 1, 0)); // glowstone
        subBlocks.add(new ItemStack(id, 1, 1)); // lamp
        subBlocks.add(new ItemStack(id, 1, 3)); // inv. lamp
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
            case 3: // It's an inverted lamp (on)
                return 15;
            case 1: // It's a lamp (off)
            case 4: // It's an inverted lamp (off)
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
        } else if (meta == 3 && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 3);
        } else if (meta == 4 && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 3);
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
