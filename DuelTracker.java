package pl.duelstats;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

public final class DuelTracker {
    private static boolean active = false;
    private static boolean finished = false;
    private static int attempts = 0;
    private static int hits = 0;
    private static int currentCombo = 0;
    private static int longestCombo = 0;
    private static UUID pendingTarget;
    private static long pendingTick = -1;
    private static float pendingHealth = -1.0f;
    private static int tickCounter = 0;

    private DuelTracker() {}

    public static void start() {
        attempts = hits = currentCombo = longestCombo = 0;
        pendingTarget = null;
        pendingTick = -1;
        pendingHealth = -1.0f;
        active = true;
        finished = false;
    }

    public static void stop() {
        active = false;
        finished = true;
        pendingTarget = null;
    }

    public static boolean isActive() { return active; }
    public static boolean isFinished() { return finished; }
    public static int attempts() { return attempts; }
    public static int hits() { return hits; }
    public static int longestCombo() { return longestCombo; }
    public static int currentCombo() { return currentCombo; }
    public static double accuracy() { return attempts == 0 ? 0.0 : hits * 100.0 / attempts; }

    public static void onAttackAttempt(Entity target) {
        if (!active || !(target instanceof LivingEntity living) || !living.isAlive()) return;
        attempts++;
        pendingTarget = target.getUuid();
        pendingTick = tickCounter;
        pendingHealth = living.getHealth();
    }

    public static void onEntityStatus(Entity entity, byte status) {
        if (!active || status != 2 || pendingTarget == null) return;
        if (entity.getUuid().equals(pendingTarget) && tickCounter - pendingTick <= 8) {
            // A hurt animation is the server-confirmed feedback most commonly available to the client.
            registerHit();
            pendingTarget = null;
        }
    }

    public static void tick(MinecraftClient client) {
        tickCounter++;
        if (!active || pendingTarget == null || client.world == null) return;
        if (tickCounter - pendingTick > 8) {
            Entity entity = client.world.getEntity(pendingTarget);
            if (entity instanceof LivingEntity living && pendingHealth >= 0 && living.getHealth() < pendingHealth) {
                registerHit();
            } else {
                currentCombo = 0;
            }
            pendingTarget = null;
        }
    }

    private static void registerHit() {
        hits++;
        currentCombo++;
        longestCombo = Math.max(longestCombo, currentCombo);
    }

    public static void onMissedAttempt() {
        if (active) currentCombo = 0;
    }
}
