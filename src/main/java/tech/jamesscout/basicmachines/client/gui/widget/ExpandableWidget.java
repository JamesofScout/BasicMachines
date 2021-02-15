package tech.jamesscout.basicmachines.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpandableWidget extends AbstractButtonWidget implements ParentElement {

  public static final Identifier BACKGROUND = new Identifier("jos_lib", "textures/gui/background.png");

  @Nullable
  Element focused;

  List<AbstractButtonWidget> children = new ArrayList<>();
  boolean expanded = false;
  boolean dragging;
  int expWidth;
  int expHeight;
  int smWidth;
  int smHeight;


  public ExpandableWidget(int x, int y, int smWidth, int smHeight, int expWidth, int expHeight, Text title) {
    super(x, y, smWidth, smHeight, title);
    this.smHeight = smHeight;
    this.smWidth = smWidth;
    this.expHeight = expHeight;
    this.expWidth = expWidth;
  }

  public void addChild(AbstractButtonWidget widget) {
    children.add(widget);
  }

  @Override
  public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    if (expanded) {
      children.forEach(c -> c.render(matrixStack, mouseX, mouseY, partialTicks));
    }
  }

  @Override
  public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    MinecraftClient minecraft = MinecraftClient.getInstance();
    minecraft.getTextureManager().bindTexture(BACKGROUND);
    RenderSystem.color4f(1f,1f,1f, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    RenderSystem.enableDepthTest();
    drawTexture(matrixStack, x, y, 0, 0, width, height, width, height);
  }

  @Override
  public List<? extends Element> children() {
    if (expanded)
      return children;
    return Collections.emptyList();
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (active && visible) {
      if (expanded && ParentElement.super.mouseClicked(mouseX, mouseY, button)) {
        return true;
      }
      else if (isValidClickButton(button) && clicked(mouseX, mouseY)) {
        this.playDownSound(MinecraftClient.getInstance().getSoundManager());
        toggleExpanded();
        return true;
      }
    }
    return false;
  }

  void toggleExpanded() {
    expanded = !expanded;
    width = expanded ? expWidth : smWidth;
    height = expanded ? expHeight : smHeight;
  }

  @Override
  public boolean isDragging() {
    return dragging;
  }

  @Override
  public void setDragging(boolean dragging) {
    this.dragging = dragging;
  }

  @Nullable
  @Override
  public Element getFocused() {
    return focused;
  }

  @Override
  public void setFocused(@Nullable Element listener) {
    focused = listener;
  }
}
