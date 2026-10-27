package pl.duelstats.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.duelstats.DuelTracker;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntityStatus", at = @At("HEAD"))
    private void duelstats$onEntityStatus(EntityStatusS2CPacket packet, CallbackInfo ci) {
        // Entity status 2 is the hurt animation/status byte.
        Entity entity = packet.getEntity(((ClientPlayNetworkHandler)(Object)this).getWorld());
        if (entity != null) {
            DuelTracker.onEntityStatus(entity, packet.getStatus());
        }
    }
}
