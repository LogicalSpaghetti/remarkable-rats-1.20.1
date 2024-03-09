package main.me.spaghetti.remarkablerats.entity.client;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer RAT =
            new EntityModelLayer(new Identifier(RemarkableRats.MOD_ID, "rat"), "main");
}
