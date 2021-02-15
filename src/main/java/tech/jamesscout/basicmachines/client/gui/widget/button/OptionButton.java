package tech.jamesscout.basicmachines.client.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class OptionButton<T extends Option<T>> extends AbstractButtonWidget {

  Supplier<T> option;
  final OnOptionPress<T> onPress;

  public OptionButton(int x, int y, int width, int height, Supplier<T> option, OnOptionPress<T> onPress) {
    super(x, y, width, height, LiteralText.EMPTY);
    this.onPress = onPress;
    this.option = option;
  }

  public void setOption(Supplier<T> option) {
    this.option = option;
  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    onPress.onPress(option.get().next());
  }

  public interface OnOptionPress<T extends Option<T>> {
    void onPress(T option);
  }

  public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.renderButton(matrices, mouseX, mouseY, delta);
    MinecraftClient minecraftClient = MinecraftClient.getInstance();
    minecraftClient.getTextureManager().bindTexture(option.get().image());

    RenderSystem.enableDepthTest();
    drawTexture(
        matrices, this.x + 2, this.y + 2, 0, 0, this.width - 4, this.height - 4, this.width - 4, this.height - 4);
    if (this.isHovered()) {
      this.renderToolTip(matrices, mouseX, mouseY);
    }
  }
}