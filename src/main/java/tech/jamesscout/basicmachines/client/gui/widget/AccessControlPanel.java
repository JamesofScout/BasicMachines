package tech.jamesscout.basicmachines.client.gui.widget;

import static tech.jamesscout.basicmachines.common.Side.Back;
import static tech.jamesscout.basicmachines.common.Side.Bottom;
import static tech.jamesscout.basicmachines.common.Side.Front;
import static tech.jamesscout.basicmachines.common.Side.Left;
import static tech.jamesscout.basicmachines.common.Side.Right;
import static tech.jamesscout.basicmachines.common.Side.Top;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;



import net.minecraft.text.Text;
import tech.jamesscout.basicmachines.client.gui.widget.button.ItemButton;
import tech.jamesscout.basicmachines.client.gui.widget.button.OptionButton;
import tech.jamesscout.basicmachines.common.Access;
import tech.jamesscout.basicmachines.common.Side;

public class AccessControlPanel extends ExpandableWidget {

  AbstractButtonWidget[] buttons = new AbstractButtonWidget[6];

  public AccessControlPanel(int x, int y, Text title, OnPress onPress, Function<Side, Access> access) {
    super(x, y, 16, 16, 64, 64, title);
    buttons[Bottom.getIndex()] =
        new OptionButton<Access>(
            22, 43, 20, 20, () -> access.apply(Bottom), a -> onPress.onPress(Bottom, a));
    buttons[Top.getIndex()] =
        new OptionButton<Access>(22, 1, 20, 20, () -> access.apply(Top), a -> onPress.onPress(Top, a));
    buttons[Left.getIndex()] =
        new OptionButton<Access>(1, 22, 20, 20,  () -> access.apply(Left), a -> onPress.onPress(Left, a));
    buttons[Front.getIndex()] =
        new OptionButton<Access>(
            22, 22, 20, 20, () -> access.apply(Front), a -> onPress.onPress(Front, a));
    buttons[Right.getIndex()] =
        new OptionButton<Access>(
            43, 22, 20, 20, () -> access.apply(Right), a -> onPress.onPress(Right, a));
    buttons[Back.getIndex()] =
        new OptionButton<Access>(
            43, 43, 20, 20, () -> access.apply(Back), a -> onPress.onPress(Back, a));
    for (AbstractButtonWidget button : buttons) {
      addChild(button);
    }
  }



  public interface OnPress {
    void onPress(Side side, Access access);
  }

}