package main.me.spaghetti.remarkablerats.item;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup Rat_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(RemarkableRats.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.ruby"))
                    .icon(() -> new ItemStack(ModItems.RAT_SPAWN_EGG)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RAT_SPAWN_EGG);
                    }).build());

    public static void registerItemGroups() {
        RemarkableRats.LOGGER.info("Registering Item Groups for " + RemarkableRats.MOD_ID);
    }
}

