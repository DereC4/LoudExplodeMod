package io.github.derec4.loudexplodemod.gamerule;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class EnableExplodeGamerule {
    public static GameRules.Key<GameRules.BooleanValue> EXPLODE_GAMERULE;

    @SubscribeEvent
    public static void registerGameRules(FMLCommonSetupEvent event) {
        EXPLODE_GAMERULE = GameRules.register("explodeGamerule", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    }
}
