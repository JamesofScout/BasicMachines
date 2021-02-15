package tech.jamesscout.basicmachines.common;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum Side implements StringIdentifiable {
  Bottom(
      0,
      "bottom",
      Direction.WEST,
      Direction.EAST,
      Direction.DOWN,
      Direction.DOWN,
      Direction.DOWN,
      Direction.DOWN),
  Top(
      1,
      "top",
      Direction.EAST,
      Direction.WEST,
      Direction.UP,
      Direction.UP,
      Direction.UP,
      Direction.UP),
  Left(
      2,
      "left",
      Direction.NORTH,
      Direction.SOUTH,
      Direction.WEST,
      Direction.EAST,
      Direction.NORTH,
      Direction.SOUTH),
  Right(
      3,
      "right",
      Direction.SOUTH,
      Direction.NORTH,
      Direction.EAST,
      Direction.WEST,
      Direction.SOUTH,
      Direction.NORTH),
  Front(
      4,
      "front",
      Direction.UP,
      Direction.DOWN,
      Direction.NORTH,
      Direction.SOUTH,
      Direction.WEST,
      Direction.EAST),
  Back(
      5,
      "back",
      Direction.DOWN,
      Direction.UP,
      Direction.SOUTH,
      Direction.NORTH,
      Direction.EAST,
      Direction.WEST);

  private final int index;
  private final String name;
  private final Direction[] facingToDirection;

  Side(int index, String name, Direction... facingToDirection) {
    this.index = index;
    this.name = name;
    this.facingToDirection = facingToDirection;
  }

  public int getIndex() {
    return index;
  }

  Direction sideToDirection(Direction facing) {
    return facingToDirection[facing.getId()];
  }

  @Override
  public String asString() {
    return name;
  }
}
