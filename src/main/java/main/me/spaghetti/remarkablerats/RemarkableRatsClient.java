package main.me.spaghetti.remarkablerats;

import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.entity.client.*;
import main.me.spaghetti.remarkablerats.screen.ModScreenHandlers;
import main.me.spaghetti.remarkablerats.screen.RatHatSewingScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class RemarkableRatsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {


        EntityRendererRegistry.register(ModEntities.RAT, RatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RAT, RatModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.WET_RAT, WetRatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.WET_RAT, WetRatModel::getTexturedModelData);

        HandledScreens.register(ModScreenHandlers.RAT_HAT_SEWING_SCREEN_HANDLER_SCREEN_HANDLER, RatHatSewingScreen::new);
    }
}
