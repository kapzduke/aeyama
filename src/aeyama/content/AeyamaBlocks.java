package aeyama.content;

import aeyama.content.blocks.*;

public class AeyamaBlocks {
    public static void load() {
        AeyamaDefenseBlocks.load();
        AeyamaDistributionBlocks.load();
        AeyamaProductionBlocks.load();
        AeyamaStorageBlocks.load();
        AeyamaEnvironmentBlocks.load();
    }
}
