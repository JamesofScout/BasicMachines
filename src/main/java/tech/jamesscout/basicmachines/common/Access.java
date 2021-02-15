package tech.jamesscout.basicmachines.common;

import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import tech.jamesscout.basicmachines.client.gui.widget.button.Option;

public enum Access implements StringIdentifiable, Option<Access> {
  In("in", true, false, new Identifier("basic_machines", "textures/gui/access_in.png")),
  Out("out", false, true, new Identifier("basic_machines", "textures/gui/access_out.png")),
  InOut("in_out", true, true, new Identifier("basic_machines", "textures/gui/access_in_out.png")),
  None("none", false, false, new Identifier("basic_machines", "textures/gui/access_none.png"));

  final String name;
  final boolean allowInput;
  final boolean allowOutput;
  final Identifier identifier;

  Access(String name, boolean allowInput, boolean allowOutput,
      Identifier identifier) {
    this.name = name;
    this.allowInput = allowInput;
    this.allowOutput = allowOutput;
    this.identifier = identifier;
  }

  byte toByte() {
    return (byte) ordinal();
  }

  @Override
  public String asString() {
    return name;
  }

  @Override
  public Access next() {
    return values()[(ordinal() + 1) % 4];
  }

  @Override
  public Identifier image() {
    return identifier;
  }
}
