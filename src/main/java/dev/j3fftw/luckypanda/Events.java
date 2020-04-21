package dev.j3fftw.luckypanda;

import dev.j3fftw.luckypanda.surprise.RollingPandaSurprise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Panda;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Events implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Panda &&
        ((Panda) e.getEntity()).getMainGene() == Panda.Gene.PLAYFUL &&
            RollingPandaSurprise.pandaHashSet.contains(e.getEntity().getUniqueId())) {
            RollingPandaSurprise.pandaHashSet.remove(e.getEntity().getUniqueId());
            if (RollingPandaSurprise.pandaHashSet.isEmpty()) {
                Bukkit.getScheduler().cancelTask(RollingPandaSurprise.id);
                RollingPandaSurprise.taskRunning = false;
            }
        }
    }
}
