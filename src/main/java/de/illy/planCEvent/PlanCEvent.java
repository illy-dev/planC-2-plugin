package de.illy.planCEvent;

import de.illy.planCEvent.commands.Dungeon;
import de.illy.planCEvent.commands.Event;
import de.illy.planCEvent.commands.GiveItems;
import de.illy.planCEvent.dungeons.generator.DungeonGenerator;
import de.illy.planCEvent.events.HideAndSeekEvent;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.items.CustomItemBuilder;
import de.illy.planCEvent.listeners.BlockBreakListener;
import de.illy.planCEvent.listeners.MobDeathListener;
import de.illy.planCEvent.listeners.PlayerEventListener;
import de.illy.planCEvent.util.Inventory.InventoryFileManager;
import de.illy.planCEvent.util.Wave.WaveManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlanCEvent extends JavaPlugin {

    public static FileConfiguration config;


    @Override
    public void onEnable() {
        // Plugin startup logic
        WaveManager waveManager = new WaveManager(this);
        TowerOfDungeonEvent todEvent = new TowerOfDungeonEvent(waveManager);
        HideAndSeekEvent hideAndSeekEvent = new HideAndSeekEvent();
        DungeonGenerator dungeonGenerator = new DungeonGenerator();

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

        getCommand("dungeon").setExecutor(new Dungeon(waveManager, dungeonGenerator));

        // listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this, todEvent), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(todEvent), this);

        getLogger().info("Plan C Event Plugin started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


        getLogger().info("whyyyy??");
        InventoryFileManager.save();
    }
}
