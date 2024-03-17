package main.me.spaghetti.remarkablerats;

import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.entity.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RemarkableRatsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {


        EntityRendererRegistry.register(ModEntities.RAT, RatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RAT, RatModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.WET_RAT, WetRatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.WET_RAT, WetRatModel::getTexturedModelData);
    }
}
