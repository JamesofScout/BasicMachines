package tech.jamesscout.basicmachines.client.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ItemButton extends ButtonWidget {

  private ItemStack itemStack;
  int itemDeltaX;
  int itemDeltaY;

  public ItemButton(
      int x, int y, int width, int height, PressAction pressedAction, ItemStack itemStack) {
    this(x, y, width, height, LiteralText.EMPTY, pressedAction, itemStack);
  }

  public ItemButton(
      int x,
      int y,
      int width,
      int height,
      Text title,
      PressAction pressedAction,
      ItemStack itemStack) {
    this(x, y, width, height, title, pressedAction, EMPTY,itemStack);
  }

  public ItemButton(
      int x,
      int y,
      int width,
      int height,
      Text title,
      PressAction pressedAction,
      TooltipSupplier onTooltip,
      ItemStack itemStack) {
    this(x, y, width, height, 2, 2, title, pressedAction, onTooltip, itemStack);
  }

  public ItemButton(
      int x,
      int y,
      int width,
      int height,
      int itemDeltaX,
      int itemDeltaY,
      Text title,
      PressAction pressedAction,
      ItemStack itemStack) {
    this(x, y, width, height, itemDeltaX, itemDeltaY, title, pressedAction, EMPTY, itemStack);
  }

  public ItemButton(
      int x,
      int y,
      int width,
      int height,
      int itemDeltaX,
      int itemDeltaY,
      Text title,
      PressAction pressedAction,
      TooltipSupplier onTooltip,
      ItemStack itemStack) {
    super(x, y, width, height, title, pressedAction, onTooltip);
    this.itemStack = itemStack;
    this.itemDeltaX = itemDeltaX;
    this.itemDeltaY = itemDeltaY;
  }

  public void setPosition(int xIn, int yIn) {
    this.x = xIn;
    this.y = yIn;
  }

  public void setItemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  /**
   *   Edited Method from {@link net.minecraft.client.gui.widget.AbstractButtonWidget} so that it renders an Item instead of Text
   */
  public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    MinecraftClient minecraft = MinecraftClient.getInstance();
    TextRenderer fontrenderer = minecraft.textRenderer;
    minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
    int i = this.getYImage(this.isHovered());
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    RenderSystem.enableDepthTest();
    this.drawTexture(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
    this.drawTexture(
        matrixStack,
        this.x + this.width / 2,
        this.y,
        200 - this.width / 2,
        46 + i * 20,
        this.width / 2,
        this.height);
    this.renderBg(matrixStack, minecraft, mouseX, mouseY);
    minecraft.getItemRenderer().renderGuiItemIcon(itemStack, x + itemDeltaX, y + itemDeltaY);
    minecraft.getItemRenderer().renderGuiItemOverlay(fontrenderer, itemStack, x + itemDeltaX, y + itemDeltaY, getMessage().getString());

    if (this.isHovered()) {
      this.renderToolTip(matrixStack, mouseX, mouseY);
    }
  }
}
