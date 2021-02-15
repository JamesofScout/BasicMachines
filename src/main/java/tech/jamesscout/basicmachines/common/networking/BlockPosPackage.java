package tech.jamesscout.basicmachines.common.networking;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class BlockPosPackage {

  BlockPos pos;

  BlockPosPackage(BlockPos pos) {
    this.pos = pos;
  }

  PacketByteBuf toBytes() {
    PacketByteBuf packet = PacketByteBufs.create();
    packet.writeBlockPos(pos);
    return packet;
  }

  BlockPosPackage(PacketByteBuf packet) {
    pos = packet.readBlockPos();
  }

}
