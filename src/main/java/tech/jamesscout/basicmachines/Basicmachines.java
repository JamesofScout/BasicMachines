package tech.jamesscout.basicmachines;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.jamesscout.basicmachines.common.CrusherBlock;
import tech.jamesscout.basicmachines.common.CrusherBlockEntity;
import tech.jamesscout.basicmachines.common.networking.SideAccessPackage;
import tech.jamesscout.basicmachines.common.screenhandler.CrusherScreenHandler;

public class Basicmachines implements ModInitializer {

  public static final Item FABRIC_ITEM = new Item(new FabricItemSettings().group(ItemGroup.MISC));
  public static final Block CRUSHER_BLOCK = new CrusherBlock(FabricBlockSettings.of(Material.METAL));
  public static BlockEntityType<CrusherBlockEntity> CRUSHER_BLOCK_ENTITY;
  public static final ScreenHandlerType<CrusherScreenHandler> CRUSHER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("basic_machines", "crusher"), CrusherScreenHandler::new);

  public final static Identifier INV_ACCESS = new Identifier("basic_machines", "inv_access");

  @Override
  public void onInitialize() {

    System.out.println(
        "Server: "
            + ServerPlayNetworking.registerGlobalReceiver(
                INV_ACCESS,
                (server, player, serverPlayNetworkHandler, buf, sender) -> {
                  // Read packet data on the event loop
                  SideAccessPackage sideAccessPackage = new SideAccessPackage(buf);
                  server.execute(
                      () -> {
                        // Everything in this lambda is run on the server thread
                        sideAccessPackage.handle(player.world);
                      });
                }));

    Registry.register(Registry.BLOCK, new Identifier("basic_machines", "crusher"), CRUSHER_BLOCK);
    System.out.println(CRUSHER_BLOCK_ENTITY);

    CRUSHER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "basic_machines:crusher", BlockEntityType.Builder.create(CrusherBlockEntity::new, CRUSHER_BLOCK).build(null));

    Registry.register(Registry.ITEM, new Identifier("basic_machines", "fabric_item"), FABRIC_ITEM);
    Registry.register(
        Registry.ITEM,
        new Identifier("basic_machines", "crusher"),
        new BlockItem(CRUSHER_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
  }
}
