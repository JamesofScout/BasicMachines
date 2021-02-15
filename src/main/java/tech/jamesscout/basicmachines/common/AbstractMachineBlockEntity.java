package tech.jamesscout.basicmachines.common;

import java.util.Arrays;
import java.util.stream.Stream;
import javax.swing.InputVerifier;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements
    ExtendedScreenHandlerFactory, BlockEntityClientSerializable, ImplementedInventory, SidedInventory {

  protected Access[] inventory_access = new Access[6];
  private final DefaultedList<ItemStack> list;

  public AbstractMachineBlockEntity(BlockEntityType<?> type, int invSize) {
    super(type);
    Arrays.fill(inventory_access, Access.None);
    list = DefaultedList.ofSize(invSize, ItemStack.EMPTY);
  }

  @Override
  public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
    buf.writeBlockPos(pos);
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    super.toTag(tag);
    tag.putByteArray("inv_access", inventoryAccessToByteArray());
    Inventories.toTag(tag, list, true);
    return tag;
  }

  @Override
  public CompoundTag toInitialChunkDataTag() {
    return toTag(new CompoundTag());
  }

  @Override
  public DefaultedList<ItemStack> getItems() {
    return list;
  }

  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    byte[] b = tag.getByteArray("inv_access");
    for (int i = 0; i < b.length; i++) {
      inventory_access[i] = Access.values()[b[i]];
    }
    Inventories.fromTag(tag, list);
  }

  @Override
  public CompoundTag toClientTag(CompoundTag tag) {
    tag.putByteArray("inv_access", inventoryAccessToByteArray());
    return tag;
  }

  @Override
  public void fromClientTag(CompoundTag tag) {
    byte[] b = tag.getByteArray("inv_access");
    for (int i = 0; i < b.length; i++) {
      inventory_access[i] = Access.values()[b[i]];
    }
  }

  byte[] inventoryAccessToByteArray() {
    byte[] b = new byte[6];
    for(int i = 0; i < 6; i++) {
      b[i] = inventory_access[i].toByte();
    }
    return b;
  }

  public void setInventoryAccess(Side side, Access access) {
    Direction dir = side.sideToDirection(getCachedState().get(Properties.HORIZONTAL_FACING));
    Access old = inventory_access[dir.getId()];
    inventory_access[dir.getId()] = access;
    if (old != access) {
      markDirty();
      sync();
    }
  }

  public Access getInventoryAccess(Side side) {
    Direction dir = side.sideToDirection(getCachedState().get(Properties.HORIZONTAL_FACING));
    return inventory_access[dir.getId()];
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
    return inventory_access[dir.getId()].allowInput && isValid(slot, stack);
  }

  @Override
  public boolean canExtract(int slot, ItemStack stack, Direction dir) {
    return inventory_access[dir.getId()].allowOutput;
  }


}
