package tech.jamesscout.basicmachines.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrusherBlock extends HorizontalFacingBlock implements BlockEntityProvider {


  public CrusherBlock(Settings settings) {
    super(settings);
    setDefaultState(
        this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
    stateManager.add(Properties.HORIZONTAL_FACING);
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockView world) {
    System.out.println("Hello World");
    return new CrusherBlockEntity();
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
      Hand hand, BlockHitResult hit) {
    if (!world.isClient) {
      NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

      if (screenHandlerFactory != null) {
        // With this call the server will request the client to open the appropriate Screenhandler
        player.openHandledScreen(screenHandlerFactory);
      }
    }

    return ActionResult.SUCCESS;
  }

  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  // This method will drop all items onto the ground when the block is broken
  @Override
  public void onStateReplaced(
      BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof CrusherBlockEntity) {
        ItemScatterer.spawn(world, pos, (CrusherBlockEntity) blockEntity);
        // update comparators
        world.updateComparators(pos, this);
      }
      super.onStateReplaced(state, world, pos, newState, moved);
    }
  }

  @Nullable
  @Override
  public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world,
      BlockPos pos) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
  }
}
