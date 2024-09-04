package io.github.derec4.loudexplodemod_1_20_1.gamerule;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnableExplodeGamerule {
    public static GameRules.Key<GameRules.BooleanValue> EXPLODE_GAMERULE;

    @SubscribeEvent
    public static void registerGameRules(FMLCommonSetupEvent event) {
        EXPLODE_GAMERULE = GameRules.register("explodeGamerule", GameRules.Category.PLAYER, BooleanValue.create(true));
    }
}
