package com.solonarv.mods.toughlamps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "tlamps", name = "Tough Lamps", version = "1.6.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ToughLamps {
    @Instance
    public static ToughLamps    instance;
    
    public static Configuration config;
    
    public static Block         toughGlowstone;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        config = new Configuration(e.getSuggestedConfigurationFile());
        
        toughGlowstone = new BlockToughGlowstone(config.getBlock(
                "toughGlowstone", 430).getInt(), Material.iron);
        
        GameRegistry.addRecipe(new ItemStack(toughGlowstone, 1, 0),
                new Object[] { "bgb", "gbg", "bgb", 'b', Block.fenceIron, 'g',
                        Item.glowstone });
        GameRegistry.addRecipe(new ItemStack(toughGlowstone, 1, 1),
                new Object[] { "bgb", "grg", "bgb", 'b', Block.fenceIron, 'g',
                        Item.glowstone, 'r', Item.redstone });
    }
}
