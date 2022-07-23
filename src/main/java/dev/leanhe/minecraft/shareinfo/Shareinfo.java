package dev.leanhe.minecraft.shareinfo;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shareinfo implements ModInitializer {

    public static final String MOD_ID = "shareinfo";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("hello world!");
    }
}
