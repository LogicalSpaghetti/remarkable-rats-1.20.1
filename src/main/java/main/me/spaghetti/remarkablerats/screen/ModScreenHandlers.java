package main.me.spaghetti.remarkablerats.screen;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<RatHatSewingScreenHandler> RAT_HAT_SEWING_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(RemarkableRats.MOD_ID, "rat_hat_sewing"),
                    new ExtendedScreenHandlerType<>(RatHatSewingScreenHandler::new));

    public static void registerScreenHandlers() {
        RemarkableRats.LOGGER.info("Registering Screen Handlers for " + RemarkableRats.MOD_ID);
    }
}
