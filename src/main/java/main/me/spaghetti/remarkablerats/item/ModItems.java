package main.me.spaghetti.remarkablerats.item;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.item.custom.BundleOfRatsItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModItems {



    public static final Item RAT_SPAWN_EGG = registerItem("rat_spawn_egg",
            new SpawnEggItem(ModEntities.RAT, 0x656476, 0x663d3d, new FabricItemSettings()));
    public static final Item BUNDLE_OF_RATS = registerItem("bundle_of_rats",
            new BundleOfRatsItem(ModEntities.RAT, Fluids.EMPTY, SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(RemarkableRats.MOD_ID, name), item);
    }

    public static void registerModItems() {
        RemarkableRats.LOGGER.info("Registering Mod Items for " + RemarkableRats.MOD_ID);
    }
}
