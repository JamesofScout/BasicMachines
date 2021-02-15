package tech.jamesscout.basicmachines.client.gui;

import static tech.jamesscout.basicmachines.Basicmachines.INV_ACCESS;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import tech.jamesscout.basicmachines.client.gui.widget.AccessControlPanel;
import tech.jamesscout.basicmachines.common.Access;
import tech.jamesscout.basicmachines.common.CrusherBlockEntity;
import tech.jamesscout.basicmachines.common.Side;
import tech.jamesscout.basicmachines.common.networking.SideAccessPackage;
import tech.jamesscout.basicmachines.common.screenhandler.CrusherScreenHandler;

public class CrusherScreen extends HandledScreen<ScreenHandler> {

  private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

  private final AccessControlPanel ControlPanel = new AccessControlPanel(0, 0, new LiteralText("Access"), this::sendButtonPress, this::getAccess);
  private BlockPos pos;
  private CrusherBlockEntity crusherBlockEntity;

  void sendButtonPress(Side side, Access access) {
    ClientPlayNetworking.send(INV_ACCESS, (new SideAccessPackage(pos, side, access)).toBytes());
  }

  public CrusherScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, getPositionText(handler).orElse(title));
    this.pos = ((CrusherScreenHandler) handler).getPos();
    BlockEntity blockEntity = inventory.player.world.getBlockEntity(pos);
    if (blockEntity instanceof CrusherBlockEntity)
      crusherBlockEntity = (CrusherBlockEntity) blockEntity;
  }

  Access getAccess(Side side) {
    return crusherBlockEntity.getInventoryAccess(side);
  }

  // This method will try to get the Position from the ScreenHandler, as ScreenRendering only
  // happens on the client we
  // get the ScreenHandler instance here which has the correct BlockPos in it!
  private static Optional<Text> getPositionText(ScreenHandler handler) {
    if (handler instanceof CrusherScreenHandler) {
      BlockPos pos = ((CrusherScreenHandler) handler).getPos();
      return pos != null
          ? Optional.of(new LiteralText("(" + pos.toShortString() + ")"))
          : Optional.empty();
    } else {
      return Optional.empty();
    }
  }

  @Override
  protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    client.getTextureManager().bindTexture(TEXTURE);
    int x = (width - backgroundWidth) / 2;
    int y = (height - backgroundHeight) / 2;
    drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    renderBackground(matrices);
    super.render(matrices, mouseX, mouseY, delta);
    drawMouseoverTooltip(matrices, mouseX, mouseY);
  }

  @Override
  protected void init() {
    super.init();
    // Center the title
    titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    addButton(ControlPanel);
  }
}
