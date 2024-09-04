package io.github.derec4.loudexplodemod_1_20_1;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec LOUD_EXPLODE_SPEC;

    public static final ForgeConfigSpec.DoubleValue DB_THRESHOLD;

    static {
        DB_THRESHOLD = BUILDER.comment("The decibel threshold for triggering the explosion")
                .defineInRange("mic.db_threshold", 85.0, 0.0, 120.0);

        LOUD_EXPLODE_SPEC = BUILDER.build();
    }
}
