package io.github.derec4.loudexplodemod_1_20_1;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DoubleSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.LongSliderEntry;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegration {

    public static void init() {

    }

    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.yourmod.config"));

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.yourmod.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Create the double slider
        IntegerSliderEntry doubleSlider = entryBuilder
                .startIntSlider(Text.translatable("config.yourmod.doubleslider"), 0.5, 0.0, 1.0)
                .setDefaultValue(0.5) // Default value
                .setTooltip(Text.of("Your tooltip here"))
                .setSaveConsumer(newValue -> {
                    // Save logic for your double value
                    // For example, save to your config file
                    YourConfigClass.doublesliderValue = newValue;
                })
                .build();

        general.addEntry(doubleSlider);

        builder.setSavingRunnable(() -> {
            // Code to save your configuration file
            YourConfigClass.save();
        });

        return builder.build();
    }
}
