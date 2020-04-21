package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import dev.j3fftw.luckypanda.utils.ReflectionUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class RollingPandaSurprise implements Surprise {

    private final Class<?> nmsClass = ReflectionUtils.getNMSClass("EntityPanda");
    private final Class<?> cbPandaClass = ReflectionUtils.getCBClass("entity.CraftPanda");
    public static boolean taskRunning = false;
    public static final HashSet<UUID> pandaHashSet = new HashSet<>();
    public static int id = 0;
    private Panda panda;


    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "roll_panda");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        final Panda newPanda = (Panda) player.getWorld().spawnEntity(block.getLocation(), EntityType.PANDA);
        newPanda.setMainGene(Panda.Gene.PLAYFUL);
        pandaHashSet.add(newPanda.getUniqueId());
        if (!taskRunning) {
            taskRunning = true;
            id = new BukkitRunnable() {
                @Override
                public void run() {
                    final Iterator<UUID> pandaIterator = pandaHashSet.iterator();
                    while (pandaIterator.hasNext()) {
                        for (Entity entity : player.getWorld().getEntities()) {
                            if (entity instanceof Panda &&
                                (((Panda) entity).getMainGene()) == Panda.Gene.PLAYFUL &&
                            entity.getUniqueId().equals(pandaIterator.next())) {
                                panda = (Panda) entity;
                                break;
                            }
                        }
                        if (panda.getLocation().getWorld().isChunkLoaded(panda.getLocation().getChunk())){
                            final Object nmsPanda = ReflectionUtils.invokeMethod(cbPandaClass,
                                panda, "getHandle");
                            if (!(boolean) ReflectionUtils.invokeMethod(nmsClass, nmsPanda,
                                "eC")) {
                                ReflectionUtils.invokeMethod(nmsClass, nmsPanda, "v",
                                    new Class[] {boolean.class}, true);
                                panda.setVelocity(panda.getVelocity().add
                                    (panda.getLocation().getDirection().normalize().multiply(0.1)));
                            }
                        }
                    }
                }
            }.runTaskTimer(LuckyPanda.getInstance(), 0, 1).getTaskId();
        }
    }
}
