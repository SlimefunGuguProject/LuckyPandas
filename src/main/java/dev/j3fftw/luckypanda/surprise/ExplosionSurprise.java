package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ExplosionSurprise implements Surprise {

    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "explosion");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        player.getWorld().createExplosion(player.getLocation(), 5);
    }

}
