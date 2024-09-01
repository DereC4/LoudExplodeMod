package io.github.derec4.loudexplodemod;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import org.slf4j.Logger;

import javax.annotation.Nullable;

@EventBusSubscriber()
public class MicLevelListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final MicLevelDetector micLevelDetector = new MicLevelDetector();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
        if (micLevelDetector.isMicLevelHigh()) {
            if (world instanceof Level level && !level.isClientSide())
                level.explode(null, x, y, z, 5, Level.ExplosionInteraction.TNT);
            {
                System.out.println("EXPLODE");
            }
        }
    }

//    @SubscribeEvent
//    public static void onPlayerTick(PlayerTickEvent event) {
//
//        Player player = event.getEntity();
//        if (!player.level().isClientSide) {
//            return;
//        }
//
//        LOGGER.info("Checking mic level for player: {}", player.getName().getString());
//        if (micLevelDetector.isMicLevelHigh()) {
//            LOGGER.info("High mic level detected! Exploding player: {}", player.getName().getString());
//            player.level().explode(player, player.getX(), player.getY(), player.getZ(), 4.0F, true);
//        }
//    }
}
