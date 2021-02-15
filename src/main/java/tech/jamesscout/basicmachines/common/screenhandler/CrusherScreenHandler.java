package tech.jamesscout.basicmachines.common.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import tech.jamesscout.basicmachines.Basicmachines;
import tech.jamesscout.basicmachines.common.CrusherBlockEntity;

public class CrusherScreenHandler extends ScreenHandler {

  Inventory inventory;
  BlockPos pos;

  public CrusherScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buf) {
    this(syncId, inv, new SimpleInventory(3));
    pos = buf.readBlockPos();
  }

  //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
  //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
  public CrusherScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
    super(Basicmachines.CRUSHER_SCREEN_HANDLER, syncId);
    checkSize(inventory, 3);
    this.inventory = inventory;
    //some inventories do custom logic when a player opens it.
    inventory.onOpen(playerInventory.player);

    //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
    //This will not render the background of the slots however, this is the Screens job
    int m;
    //Our inventory
    for (m = 0; m < 3; ++m) {
        this.addSlot(new Slot(inventory, m, 62 + m * 18, 35));
    }

    int l;

    //The player inventory
    for (m = 0; m < 3; ++m) {
      for (l = 0; l < 9; ++l) {
        this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
      }
    }
    //The player Hotbar
    for (m = 0; m < 9; ++m) {
      this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
    }

  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return this.inventory.canPlayerUse(player);
  }

  // Shift + Player Inv Slot
  @Override
  public ItemStack transferSlot(PlayerEntity player, int invSlot) {
    ItemStack newStack = ItemStack.EMPTY;
    Slot slot = this.slots.get(invSlot);
    if (slot != null && slot.hasStack()) {
      ItemStack originalStack = slot.getStack();
      newStack = originalStack.copy();
      if (invSlot < this.inventory.size()) {
        if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
        return ItemStack.EMPTY;
      }

      if (originalStack.isEmpty()) {
        slot.setStack(ItemStack.EMPTY);
      } else {
        slot.markDirty();
      }
    }

    return newStack;
  }

  public BlockPos getPos() {
    return pos;
  }
}
