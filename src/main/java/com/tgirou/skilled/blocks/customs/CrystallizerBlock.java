package com.tgirou.skilled.blocks.customs;

import com.tgirou.skilled.blocks.entities.CrystallizerBlockEntity;
import com.tgirou.skilled.blocks.entities.ModBlockEntities;
import com.tgirou.skilled.recipes.CrystallizerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static com.tgirou.skilled.api.util.Utils.getRandomNumber;

public class CrystallizerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CrystallizerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
    }

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 32, 16);

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof  CrystallizerBlockEntity) {
                ((CrystallizerBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof CrystallizerBlockEntity entity) {
                ItemStack playerHandItems = player.getItemInHand(interactionHand);
                if (entity.canCrystallizeHeldItem(playerHandItems)) {
                    entity.startCrystallizeHeldItem(new ItemStack(player.getItemInHand(interactionHand).getItem(), 1));
                    player.getItemInHand(interactionHand).shrink(1);
                } else {
                    NetworkHooks.openGui(((ServerPlayer) player), entity, pos);
                }
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrystallizerBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.CRYSTALLIZER.get(), CrystallizerBlockEntity::serverTick);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = pPos.getY() + 0.4D;
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRand.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (getRandomNumber(1, 4)) {
                case 1 -> {
                    double d5 = (double)Direction.EAST.getStepX()  * 0.52D;
                    double d6 = pRand.nextDouble() * 6.0D / 16.0D;
                    double d7 = (double)Direction.EAST.getStepZ()  * 0.52D;
                    pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                    pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                }
                case 2 -> {
                    double d8 = (double)Direction.NORTH.getStepX() * 0.52D;
                    double d9 = (double)Direction.NORTH.getStepZ() * 0.52D;
                    double d10 = pRand.nextDouble() * 6.0D / 16.0D;
                    pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                    pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                }
                case 3 -> {
                    double d8 = (double)Direction.SOUTH.getStepX() * 0.52D;
                    double d9 = (double)Direction.SOUTH.getStepZ() * 0.52D;
                    double d10 = pRand.nextDouble() * 6.0D / 16.0D;
                    pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                    pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                }
                case 4 -> {
                    double d8 = (double)Direction.WEST.getStepX() * 0.52D;
                    double d9 = (double)Direction.WEST.getStepZ() * 0.52D;
                    double d10 = pRand.nextDouble() * 6.0D / 16.0D;
                    pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                    pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, d0 + d8, d1 + d10, d2 + d9, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
