package com.solonarv.mods.toughlamps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "tlamps", name = "Tough Lamps", version = "1.6.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ToughLamps {
    @Instance
    public static ToughLamps          instance;
    
    public static Configuration       config;
    
    public static BlockToughGlowstone toughGlowstone;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        config = new Configuration(e.getSuggestedConfigurationFile());
        
        toughGlowstone = (BlockToughGlowstone) (new BlockToughGlowstone(config
                .getBlock("toughGlowstone", 430).getInt(), Material.iron)
                .setUnlocalizedName("toughGlowstone").setHardness(1.5f)
                // enough for point-blank charged creeper
                .setResistance(125).setStepSound(Block.soundMetalFootstep)
                .setCreativeTab(CreativeTabs.tabBlock));
        
        try {
            
            LanguageRegistry.addName(new ItemStack(toughGlowstone, 1, 0),
                    "Toughened Glowstone");
            LanguageRegistry.addName(new ItemStack(toughGlowstone, 1, 1),
                    "Toughened Lamp");
            LanguageRegistry.addName(new ItemStack(toughGlowstone, 1, 3),
                    "Toughened Inverted Lamp");
        } catch (NullPointerException ex) {
            if (new ItemStack(toughGlowstone).getItem() == null) {
                System.out
                        .println("ItemStacks of toughGlowstone have null as their item!");
            } else {
                ex.printStackTrace();
            }
        }
        /*
         * make toughened glowstone: 3 glowstone in the middle row/col,
         * surrounded by 6 iron.Gives 4.
         */
        GameRegistry.addRecipe(new ItemStack(toughGlowstone, 4, 0),
                new Object[] { "igi", "igi", "igi", 'i', Item.ingotIron, 'g',
                        Block.glowStone });
        GameRegistry.addRecipe(new ItemStack(toughGlowstone, 4, 0),
                new Object[] { "iii", "ggg", "iii", 'i', Item.ingotIron, 'g',
                        Block.glowStone });
        // convert to a toughened lamp: shapeless with redstone
        GameRegistry.addShapelessRecipe(new ItemStack(toughGlowstone, 1, 1),
                Item.redstone, Item.redstone, new ItemStack(toughGlowstone, 1,
                        0));
        // convert between inverted/non-inverted lamps by just crafting
        GameRegistry.addShapelessRecipe(new ItemStack(toughGlowstone, 1, 3),
                new ItemStack(toughGlowstone, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(toughGlowstone, 1, 1),
                new ItemStack(toughGlowstone, 1, 3));
    }
}
