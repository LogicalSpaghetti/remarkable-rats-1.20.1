package main.me.spaghetti.remarkablerats;

import main.me.spaghetti.remarkablerats.block.entity.ModBlockEntities;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.entity.custom.RatEntity;
import main.me.spaghetti.remarkablerats.entity.custom.WetRatEntity;
import main.me.spaghetti.remarkablerats.item.ModItemGroups;
import main.me.spaghetti.remarkablerats.item.ModItems;
import main.me.spaghetti.remarkablerats.screen.ModScreenHandlers;
import main.me.spaghetti.remarkablerats.world.gen.ModEntityGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemarkableRats implements ModInitializer {

	public static final String MOD_ID = "remarkable-rats";

	public static final Identifier RAT_SQUEAK = new Identifier("remarkable-rats:rat_squeak_1");
	public static SoundEvent RAT_SQUEAK_EVENT = SoundEvent.of(RAT_SQUEAK);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		FabricDefaultAttributeRegistry.register(ModEntities.RAT, RatEntity.createRatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.WET_RAT, WetRatEntity.createRatAttributes());

		ModItems.registerModItems();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModEntityGeneration.addSpawns();
		Registry.register(Registries.SOUND_EVENT, RAT_SQUEAK, RAT_SQUEAK_EVENT);
	}
}


