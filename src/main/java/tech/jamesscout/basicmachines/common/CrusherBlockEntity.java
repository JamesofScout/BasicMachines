package tech.jamesscout.basicmachines.common;

import static tech.jamesscout.basicmachines.Basicmachines.CRUSHER_BLOCK_ENTITY;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import tech.jamesscout.basicmachines.common.screenhandler.CrusherScreenHandler;

public class CrusherBlockEntity extends AbstractMachineBlockEntity {

  static final AccessSlotArray INVENTORY_ACCESS_SLOT_ARRAY = new AccessSlotArray(2, 1);

  public CrusherBlockEntity() {
    super(CRUSHER_BLOCK_ENTITY, 3);
  }

  @Override
 public int[] getAvailableSlots(Direction side) {
    return INVENTORY_ACCESS_SLOT_ARRAY.fromAccess(inventory_access[side.getId()]);
  }

  @Override
  public Text getDisplayName() {
    return new TranslatableText(getCachedState().getBlock().getTranslationKey());
  }

  @Nullable
  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
    return new CrusherScreenHandler(syncId, inv, this);
  }
}
