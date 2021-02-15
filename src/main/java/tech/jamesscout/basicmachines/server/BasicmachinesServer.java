package tech.jamesscout.basicmachines.server;

import static tech.jamesscout.basicmachines.Basicmachines.INV_ACCESS;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.system.CallbackI.B;
import tech.jamesscout.basicmachines.Basicmachines;
import tech.jamesscout.basicmachines.client.gui.CrusherScreen;
import tech.jamesscout.basicmachines.common.networking.SideAccessPackage;

public class BasicmachinesServer implements DedicatedServerModInitializer {


  @Override
  public void onInitializeServer() {

  }
}
