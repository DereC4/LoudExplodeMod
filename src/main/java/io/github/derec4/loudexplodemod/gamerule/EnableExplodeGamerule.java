package io.github.derec4.loudexplodemod.gamerule;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class EnableExplodeGamerule {

    @SubscribeEvent
    public static void registerGameRules(FMLCommonSetupEvent event) {
        MACEDEBUGMODE = GameRules.register("maceDebugMode", GameRules.Category.UPDATES, GameRules.BooleanValue.create(false));
        MACE_DAMAGE_CAP = GameRules.register("maceDamageCap", GameRules.Category.UPDATES, GameRules.IntegerValue.create(-1));
    }
}
