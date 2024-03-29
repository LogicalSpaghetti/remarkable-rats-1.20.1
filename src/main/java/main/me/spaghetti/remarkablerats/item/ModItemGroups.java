package main.me.spaghetti.remarkablerats.item;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.block.custom.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RAT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(RemarkableRats.MOD_ID, "rat"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.remarkable-rats"))
                    .icon(() -> new ItemStack(ModItems.RAT_SPAWN_EGG)).entries((displayContext, entries) -> {
                        // add items here:
                        entries.add(ModItems.RAT_SPAWN_EGG);
                        entries.add(ModItems.BUNDLE_OF_RATS);
                        entries.add(ModItems.RAT_TOP_HAT);
                        entries.add(ModBlocks.RAT_HAT_SEWING_TABLE);


                    }).build());

    public static void registerItemGroups() {
        RemarkableRats.LOGGER.info("Registering Item Groups for " + RemarkableRats.MOD_ID);
    }
}

