package main.me.spaghetti.remarkablerats.block.custom;

import main.me.spaghetti.remarkablerats.block.entity.ModBlockEntities;
import main.me.spaghetti.remarkablerats.block.entity.RatHatSewingTableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


// wool can be turned into a basic colored hat,
// which then needs to be shaped (profession),
// and then upgraded in a couple ways. the wool color, embellishing item, and other stuff...

// todo: just make it like tinker's construct, where the different parts determine profession, niche, and even modifiers
// idk if it should use separate blocks for each step, or just have them contained in different tabs
public class RatHatSewingTableBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape OUTLINE_SHAPE =
            VoxelShapes.union(
                    BlockWithEntity.createCuboidShape(0, 12, 0, 16, 16, 16),
                    BlockWithEntity.createCuboidShape(0, 0, 0, 4, 12, 4),
                    BlockWithEntity.createCuboidShape(12, 0, 0, 16, 12, 4),
                    BlockWithEntity.createCuboidShape(12, 0, 12, 16, 12, 16),
                    BlockWithEntity.createCuboidShape(0, 0, 12, 4, 12, 16)
            );

    public RatHatSewingTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RatHatSewingTableBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RatHatSewingTableBlockEntity) {
                ItemScatterer.spawn(world, pos, (RatHatSewingTableBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((RatHatSewingTableBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.RAT_HAT_SEWING_TABLE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
