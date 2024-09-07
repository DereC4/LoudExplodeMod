package io.github.derec4.loudexplodemod_1_20_1.gamerule;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnableExplodeGamerule {
    public static GameRules.Key<GameRules.BooleanValue> EXPLODE_GAMERULE;
    public static GameRules.Key<GameRules.IntegerValue> MIC_THRESHOLD_GAMERULE;

    @SubscribeEvent
    public static void registerGameRules(FMLCommonSetupEvent event) {
        EXPLODE_GAMERULE = GameRules.register("explodeGamerule", GameRules.Category.PLAYER, BooleanValue.create(true));
        MIC_THRESHOLD_GAMERULE = GameRules.register("micThreshold", GameRules.Category.PLAYER,
                GameRules.IntegerValue.create(85)); // Default to 85
    }

//    // Event to detect specific game rule changes
//    @SubscribeEvent
//    public static void onGameruleChange(PlayerEvent event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            int newThreshold = player.level().getGameRules().getInt(MIC_THRESHOLD_GAMERULE);
//            onMicThresholdChange(newThreshold);
//        }
//    }
}
