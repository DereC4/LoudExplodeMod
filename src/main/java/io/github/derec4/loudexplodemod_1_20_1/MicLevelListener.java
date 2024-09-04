package io.github.derec4.loudexplodemod_1_20_1;

import io.github.derec4.loudexplodemod_1_20_1.gamerule.EnableExplodeGamerule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = "loudexplodemod_1_20_1", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MicLevelListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static MicLevelDetector micLevelDetector;
    private static boolean isMicLevelDetectorInitialized = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            // Initialize mic level detector only if it's not already initialized and the player is in a world
            if (!isMicLevelDetectorInitialized && player.level() != null) {
                micLevelDetector = new MicLevelDetector();
                isMicLevelDetectorInitialized = true;
            }

            // Only run the detection logic if the mic level detector has been initialized
            if (micLevelDetector != null) {
                execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            }
        }
    }

    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (micLevelDetector.isMicLevelHigh()) {
            if (entity instanceof Player player) {
                if (world instanceof Level level && !level.isClientSide && level.getGameRules().getBoolean(EnableExplodeGamerule.EXPLODE_GAMERULE)) {
                    if (!player.isSpectator() && player.isAlive()) {
//                        System.out.println("ABCDEF" + Config.dbThreshold);
                        level.explode(null, x, y, z, 5, Level.ExplosionInteraction.TNT);
//                        LOGGER.info("EXPLODE");
                    }
                }
            }
        }
    }
}
