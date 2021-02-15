package tech.jamesscout.basicmachines.common;

import java.util.stream.Stream;
import tech.jamesscout.basicmachines.common.Access;

public class AccessSlotArray {
  final int[] in;
  final int[] out;
  final int[] in_out;
  final int[] none = new int[0];

  public AccessSlotArray(int in, int out) {
    this.in = Stream.iterate(0, j -> j + 1).limit(in).mapToInt(i -> i).toArray();
    this.out = Stream.iterate(in, j -> j + 1).limit(out).mapToInt(i -> i).toArray();
    this.in_out = Stream.iterate(0, j -> j + 1).limit(in + out).mapToInt(i -> i).toArray();
  }

  public int[] fromAccess(Access access) {
    switch (access) {
      case In:
        return in;
      case Out:
        return out;
      case InOut:
        return in_out;
      default:
        return none;
    }
  }
}
