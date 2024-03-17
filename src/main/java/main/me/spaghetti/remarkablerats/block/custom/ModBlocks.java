package main.me.spaghetti.remarkablerats.block.custom;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block RAT_HAT_SEWING_TABLE = registerBlock("rat_hat_sewing_table",
            new RatHatSewingTableBlock(FabricBlockSettings.copyOf(Blocks.IRON_DOOR)));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(RemarkableRats.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(RemarkableRats.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        RemarkableRats.LOGGER.info("Registering Mod Blocks for " + RemarkableRats.MOD_ID);
    }
}
