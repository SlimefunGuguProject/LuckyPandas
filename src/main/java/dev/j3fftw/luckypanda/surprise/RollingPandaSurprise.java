package dev.j3fftw.luckypanda.surprise;

import dev.j3fftw.luckypanda.LuckyPanda;
import dev.j3fftw.luckypanda.utils.ReflectionUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class RollingPandaSurprise implements Surprise {

    private final Class<?> nmsClass = ReflectionUtils.getNMSClass("Panda");
    private final Class<?> cbPandaClass = ReflectionUtils.getCBClass("entity.CraftPanda");

    @Nonnull
    @Override
    public NamespacedKey getId() {
        return new NamespacedKey(LuckyPanda.getInstance(), "roll_panda");
    }

    @Override
    public void process(@Nonnull Player player, @Nonnull Block block) {
        Panda newPanda = (Panda) player.getWorld().spawnEntity(block.getLocation(), EntityType.PANDA);
        ReflectionUtils.invokeMethod(nmsClass,
            ReflectionUtils.invokeMethod(cbPandaClass, newPanda, "getHandle"),
            "roll", new Class[] {Boolean.class}, true);
    }

}
