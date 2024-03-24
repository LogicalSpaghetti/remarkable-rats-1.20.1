package main.me.spaghetti.remarkablerats.entity;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.custom.JointedEntity;
import main.me.spaghetti.remarkablerats.entity.custom.RatEntity;
import main.me.spaghetti.remarkablerats.entity.custom.WetRatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<RatEntity> RAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(RemarkableRats.MOD_ID, "rat"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RatEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.4f)).build());

    public static final EntityType<WetRatEntity> WET_RAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(RemarkableRats.MOD_ID, "wet_rat"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WetRatEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 1.9f)).build());
    public static final EntityType<JointedEntity> JOINTED = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(RemarkableRats.MOD_ID, "jointed"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, JointedEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 1.9f)).build());


    public static void registerModEntityAttributes() {
        FabricDefaultAttributeRegistry.register(RAT, RatEntity.createRatAttributes());
        FabricDefaultAttributeRegistry.register(WET_RAT, WetRatEntity.createRatAttributes());
        FabricDefaultAttributeRegistry.register(JOINTED, JointedEntity.createJointedAttributes());
    }
}