package main.me.spaghetti.remarkablerats.item;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {



    public static final Item RAT_SPAWN_EGG = registerItem("rat_spawn_egg",
            new SpawnEggItem(ModEntities.RAT, 0x656476, 0x663d3d, new FabricItemSettings()));
    public static final Item BUNDLE_OF_RATS = registerItem("bundle_of_rats",
            new Item())


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(RemarkableRats.MOD_ID, name), item);
    }

    public static void registerModItems() {
        RemarkableRats.LOGGER.info("Registering Mod Items for " + RemarkableRats.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        // entries.add(ITEM);
    }
}
