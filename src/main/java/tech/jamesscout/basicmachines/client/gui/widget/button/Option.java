package tech.jamesscout.basicmachines.client.gui.widget.button;

import net.minecraft.util.Identifier;

public interface Option<T extends Option<T>> {
  T next();
  Identifier image();
}
