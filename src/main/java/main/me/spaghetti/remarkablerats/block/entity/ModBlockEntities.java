package main.me.spaghetti.remarkablerats.block.entity;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.block.custom.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<RatHatSewingTableBlockEntity> RAT_HAT_SEWING_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RemarkableRats.MOD_ID, "rat_hat_sewing_be"),
                    FabricBlockEntityTypeBuilder.create(RatHatSewingTableBlockEntity::new,
                            ModBlocks.RAT_HAT_SEWING_TABLE).build());

    public static void registerBlockEntities() {
        RemarkableRats.LOGGER.info("Registering block entities for " + RemarkableRats.MOD_ID);
    }
}
