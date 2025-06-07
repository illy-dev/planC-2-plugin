package de.illy.planCEvent;

import com.sun.tools.javac.Main;
import de.illy.planCEvent.commands.*;
import de.illy.planCEvent.dungeons.generator.DungeonGenerator;
import de.illy.planCEvent.events.HideAndSeekEvent;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.items.CustomItemBuilder;
import de.illy.planCEvent.listeners.*;
import de.illy.planCEvent.util.Inventory.InventoryFileManager;
import de.illy.planCEvent.util.Wave.WaveManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlanCEvent extends JavaPlugin {

    public static FileConfiguration config;
    @Getter
    private static PlanCEvent instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        WaveManager waveManager = new WaveManager(this);
        TowerOfDungeonEvent todEvent = new TowerOfDungeonEvent(waveManager);
        HideAndSeekEvent hideAndSeekEvent = new HideAndSeekEvent();

        //saveResource("waves.yml", true); // true = force overwrite


        // configs
        saveDefaultConfig();
        config = getConfig();
        InventoryFileManager.setup(this);

        // item builder
        CustomItemBuilder.setPlugin(this);

        // commands und tab completer
        getCommand("event").setExecutor(new Event(todEvent, hideAndSeekEvent));
        getCommand("event").setTabCompleter(new de.illy.planCEvent.tabcompleter.Event());

        getCommand("dungeon").setExecutor(new Dungeon());
        getCommand("hub").setExecutor(new Hub());
        getCommand("giveitems").setExecutor(new GiveItems());
        getCommand("toggledamage").setExecutor(new ToggleDamage());
        getCommand("opencase").setExecutor(new OpenCase());

        getCommand("spawnmob").setExecutor(new SpawnMob());
        getCommand("spawnmob").setTabCompleter(new de.illy.planCEvent.tabcompleter.SpawnMob());

        // listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this, todEvent), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(todEvent), this);
        getServer().getPluginManager().registerEvents(new ItemAbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new ArrowHitListener(), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(), this);
        getServer().getPluginManager().registerEvents(new CaseGUI(), this);

        getLogger().info("Plan C Event Plugin started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


        getLogger().info("whyyyy??");
        InventoryFileManager.save();
    }

}
