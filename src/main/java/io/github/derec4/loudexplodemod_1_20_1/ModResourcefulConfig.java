package io.github.derec4.loudexplodemod_1_20_1;

import com.teamresourceful.resourcefulconfig.common.annotations.*;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Config(value = "loudexplodemod")
public final class ModResourcefulConfig {
    @ConfigEntry(id = "dbThreshold", type = EntryType.DOUBLE, translation = "loudexplodemod.config.dbThreshold.val")
    @DoubleRange(min=-30, max=30)
    @Comment("The decibel threshold for microphone input to trigger the explosion")
    public static double dbThreshold = -2.0;
}
