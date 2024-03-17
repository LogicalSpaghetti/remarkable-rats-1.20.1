package main.me.spaghetti.remarkablerats.world.gen;

import main.me.spaghetti.remarkablerats.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntityGeneration {
    public static void  addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                BiomeKeys.PLAINS,
                BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.SWAMP,
                BiomeKeys.MANGROVE_SWAMP,
                BiomeKeys.DRIPSTONE_CAVES,
                BiomeKeys.LUSH_CAVES,
                BiomeKeys.SAVANNA,
                BiomeKeys.SAVANNA_PLATEAU,
                BiomeKeys.DESERT,
                BiomeKeys.BADLANDS,
                BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.WOODED_BADLANDS,
                BiomeKeys.FOREST,
                BiomeKeys.FLOWER_FOREST,
                BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,
                BiomeKeys.TAIGA,
                BiomeKeys.JUNGLE,
                BiomeKeys.SPARSE_JUNGLE,
                BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.CHERRY_GROVE,
                BiomeKeys.BEACH,
                BiomeKeys.STONY_SHORE,
                BiomeKeys.WINDSWEPT_HILLS,
                BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
                BiomeKeys.WINDSWEPT_FOREST,
                BiomeKeys.WINDSWEPT_SAVANNA,
                BiomeKeys.MEADOW,
                BiomeKeys.GROVE,
                BiomeKeys.JAGGED_PEAKS,
                BiomeKeys.STONY_PEAKS

                ), SpawnGroup.CREATURE,
                ModEntities.RAT, 5, 1, 1);

        SpawnRestriction.register(ModEntities.RAT, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }
}
