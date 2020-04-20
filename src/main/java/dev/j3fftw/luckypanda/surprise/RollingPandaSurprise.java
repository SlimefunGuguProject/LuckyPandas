package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import dev.j3fftw.luckypanda.utils.ReflectionUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Iterator;

public class RollingPandaSurprise implements Surprise {

    private final Class<?> nmsClass = ReflectionUtils.getNMSClass("EntityPanda");
    private final Class<?> cbPandaClass = ReflectionUtils.getCBClass("entity.CraftPanda");
    private static boolean taskRunning = false;
    private static HashSet<Panda> pandaHashSet = new HashSet<>();


    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "roll_panda");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        final Panda newPanda = (Panda) player.getWorld().spawnEntity(block.getLocation(), EntityType.PANDA);
        newPanda.setMainGene(Panda.Gene.PLAYFUL);
        pandaHashSet.add(newPanda);
        if (!taskRunning) {
            taskRunning = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Iterator<Panda> pandaIterator = pandaHashSet.iterator();
                    while (pandaIterator.hasNext()) {
                        Panda panda = pandaIterator.next();
                        if (!panda.getLocation().getChunk().isLoaded())
                            continue;
                        else if (panda.isDead())
                            pandaHashSet.remove(panda);
                        else {
                            final Object nmsPanda = ReflectionUtils.invokeMethod(cbPandaClass,
                                panda, "getHandle");
                            if (!(boolean) ReflectionUtils.invokeMethod(nmsClass, nmsPanda,
                                "eC")) {
                                ReflectionUtils.invokeMethod(nmsClass, nmsPanda, "v",
                                    new Class[] {boolean.class}, true);
                                panda.setVelocity(panda.getVelocity().add
                                    (panda.getLocation().getDirection().normalize().multiply(0.01)));
                            }
                        }
                    }
                    if (pandaHashSet.isEmpty()) {
                        taskRunning = false;
                        cancel();
                    }
                }
            }.runTaskTimer(LuckyPanda.getInstance(), 0, 1);
        }
    }
}
