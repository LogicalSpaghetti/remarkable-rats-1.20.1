package main.me.spaghetti.remarkablerats;

import main.me.spaghetti.remarkablerats.block.custom.ModBlocks;
import main.me.spaghetti.remarkablerats.block.entity.ModBlockEntities;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.item.ModItemGroups;
import main.me.spaghetti.remarkablerats.item.ModItems;
import main.me.spaghetti.remarkablerats.screen.ModScreenHandlers;
import main.me.spaghetti.remarkablerats.sound.ModSounds;
import main.me.spaghetti.remarkablerats.world.gen.ModEntityGeneration;
import net.fabricmc.api.ModInitializer;

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

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();


		ModSounds.registerSounds();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();


		ModEntities.registerModEntityAttributes();

		ModEntityGeneration.addSpawns();
	}
}


