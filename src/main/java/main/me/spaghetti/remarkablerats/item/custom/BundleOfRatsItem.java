package main.me.spaghetti.remarkablerats.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BundleOfRatsItem extends EntityBucketItem {
    public BundleOfRatsItem(EntityType<?> type, Fluid fluid, SoundEvent emptyingSound, Settings settings) {
        super(type, fluid, emptyingSound, settings);
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        return true;
    }

    //new EntityBucketItem(ModEntities.RAT, Fluids.WATER /*null*/, SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, new Item.Settings().maxCount(1))
}
