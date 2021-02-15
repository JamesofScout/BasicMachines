package tech.jamesscout.basicmachines.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.jamesscout.basicmachines.Basicmachines;
import tech.jamesscout.basicmachines.client.gui.CrusherScreen;

@Environment(EnvType.CLIENT)
public class BasicmachinesClient implements ClientModInitializer {


  @Override
  public void onInitializeClient() {
    ScreenRegistry.register(Basicmachines.CRUSHER_SCREEN_HANDLER, CrusherScreen::new);
  }
}
