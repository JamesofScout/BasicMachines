package tech.jamesscout.basicmachines.common.networking;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import tech.jamesscout.basicmachines.common.AbstractMachineBlockEntity;
import tech.jamesscout.basicmachines.common.Access;
import tech.jamesscout.basicmachines.common.Side;

public class SideAccessPackage extends BlockPosPackage {

  Side side;
  Access access;

  public SideAccessPackage(BlockPos pos, Side side, Access access) {
    super(pos);
    this.side = side;
    this.access = access;
  }

  public SideAccessPackage(PacketByteBuf packet) {
    super(packet);
    side = packet.readEnumConstant(Side.class);
    access = packet.readEnumConstant(Access.class);
  }

  @Override
  public PacketByteBuf toBytes() {
    PacketByteBuf packet = super.toBytes();
    packet.writeEnumConstant(side);
    packet.writeEnumConstant(access);
    return packet;
  }

  public void handle (World world) {
    if (world.isChunkLoaded(pos)) {
      BlockEntity e = world.getBlockEntity(pos);
      if (e instanceof AbstractMachineBlockEntity) {
        AbstractMachineBlockEntity abstractMachineBlockEntity = (AbstractMachineBlockEntity) e;
        abstractMachineBlockEntity.setInventoryAccess(side, access);
      }
    }
  }

}
