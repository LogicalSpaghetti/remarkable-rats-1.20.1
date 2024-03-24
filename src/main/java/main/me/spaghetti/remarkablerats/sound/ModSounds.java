package main.me.spaghetti.remarkablerats.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static main.me.spaghetti.remarkablerats.RemarkableRats.RAT_SQUEAK;
import static main.me.spaghetti.remarkablerats.RemarkableRats.RAT_SQUEAK_EVENT;

public class ModSounds {
    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, RAT_SQUEAK, RAT_SQUEAK_EVENT);
    }
}
