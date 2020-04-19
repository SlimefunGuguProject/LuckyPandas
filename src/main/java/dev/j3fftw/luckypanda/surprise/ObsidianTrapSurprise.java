package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ObsidianTrapSurprise implements Surprise {

    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "obsidian_trap");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        Location playerLoc = player.getLocation();
        for (byte y = -1; y < 3; y++) {
            for (byte x = -1; x < 2; x++) {
                for (byte z = -1; z < 2; z++) {
                    if (x == 0 && z == 0 && (y == 0 || y == 1)) {
                        playerLoc.clone().add(x, y, z).getBlock().setType(Material.WATER);
                    } else if (((x == 0 && z != 0) || (z == 0 && x != 0)) && y == 1) {
                        playerLoc.clone().add(x, y, z).getBlock().setType(Material.GLASS_PANE);
                    } else {
                        playerLoc.clone().add(x, y, z).getBlock().setType(Material.OBSIDIAN);
                    }
                }
            }
        }
    }

}


