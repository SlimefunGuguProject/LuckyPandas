package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class JailAnvilSurprise implements Surprise {

    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "jail_anvil");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        final Location playerLoc = player.getLocation();
        for (byte y = -1; y < 4; y++) {
            for (byte x = 2; x > -3; x--) {
                for (byte z = 2; z > -3; z--) {
                    if (y == -1)
                        playerLoc.clone().add(x, y, z).getBlock().setType(Material.STONE_BRICKS);
                    else if (x == 0 && z == 0)
                        continue;
                    else if (x > -2 && x < 2 && z > -2 && z < 2)
                        playerLoc.clone().add(x, y, z).getBlock().setType(Material.IRON_BARS);
                }
            }
        }
        playerLoc.clone().add(0, 50, 0).getBlock().setType(Material.ANVIL);
        playerLoc.clone().add(0, 51, 0).getBlock().setType(Material.ANVIL);
    }
}
