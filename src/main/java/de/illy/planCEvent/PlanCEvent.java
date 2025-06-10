package de.illy.planCEvent;

import de.illy.planCEvent.StatSystem.LevelManager;
import de.illy.planCEvent.StatSystem.ManaRegenTask;
import de.illy.planCEvent.StatSystem.SpeedManager;
import de.illy.planCEvent.commands.*;
import de.illy.planCEvent.events.HideAndSeekEvent;
import de.illy.planCEvent.events.TowerOfDungeonEvent;
import de.illy.planCEvent.items.CraftingRecipes;
import de.illy.planCEvent.items.CustomItemBuilder;
import de.illy.planCEvent.listeners.*;
import de.illy.planCEvent.util.AfkManager;
import de.illy.planCEvent.util.Inventory.InventoryFileManager;
import de.illy.planCEvent.util.MagnetRelicTask;
import de.illy.planCEvent.util.Quests.QuestManager;
import de.illy.planCEvent.util.RelicManager;
import de.illy.planCEvent.util.Rewards.RewardManager;
import de.illy.planCEvent.util.Rewards.RewardTask;
import de.illy.planCEvent.util.Wave.WaveManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PlanCEvent extends JavaPlugin {

    public static FileConfiguration config;
    @Getter
    private static PlanCEvent instance;
    @Getter
    private RelicManager relicManager = new RelicManager(this);;
    @Getter
    private SpeedManager speedManager;

    private AfkManager afkManager;
    private RewardManager rewardManager;
    private RewardTask rewardTask;
    @Getter
    private QuestManager questManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        WaveManager waveManager = new WaveManager(this);
        TowerOfDungeonEvent todEvent = new TowerOfDungeonEvent(waveManager);
        HideAndSeekEvent hideAndSeekEvent = new HideAndSeekEvent();
        MenuGUI menu = new MenuGUI();
        speedManager = new SpeedManager(this);
        afkManager = new AfkManager();
        rewardManager = new RewardManager(afkManager);
        rewardTask = new RewardTask(rewardManager);
        questManager = new QuestManager();

        rewardTask.start();
        MagnetRelicTask.start();

        //saveResource("waves.yml", true); // true = force overwrite


        // configs
        saveDefaultConfig();
        config = getConfig();
        InventoryFileManager.setup(this);

        File playersFolder = new File(getDataFolder(), "players");
        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
            getLogger().info("Created players folder for saving player data.");
        }

        // item builder
        CustomItemBuilder.setPlugin(this);

        // commands und tab completer
        getCommand("event").setExecutor(new Event(todEvent, hideAndSeekEvent));
        getCommand("event").setTabCompleter(new de.illy.planCEvent.tabcompleter.Event());

        getCommand("dungeon").setExecutor(new Dungeon());
        getCommand("giveitems").setExecutor(new GiveItems());
        getCommand("menu").setExecutor(new Menu(menu));
        getCommand("spawnparticles").setExecutor(new spawnParticles(this));

        getCommand("spawnmob").setExecutor(new SpawnMob());
        getCommand("spawnmob").setTabCompleter(new de.illy.planCEvent.tabcompleter.SpawnMob());

        getCommand("givexp").setExecutor(new Givexp());

        // listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this, todEvent), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(todEvent), this);
        getServer().getPluginManager().registerEvents(new ItemAbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new ArrowHitListener(), this);
        getServer().getPluginManager().registerEvents(new XpBottleListener(), this);
        getServer().getPluginManager().registerEvents(new CaseGUI(), this);
        getServer().getPluginManager().registerEvents(new ArmorAbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
        getServer().getPluginManager().registerEvents(new MenuGUI(), this);
        getServer().getPluginManager().registerEvents(new RelicPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new VoidRelicListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionEvents(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(speedManager, this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerActivityListener(afkManager), this);
        getServer().getPluginManager().registerEvents(new PhantomRelicListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);

        CraftingRecipes.register(this);

        ManaRegenTask.startTask();

        getLogger().info("Plan C Event Plugin started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logi

        getLogger().info("whyyyy??");
        for (Player player : Bukkit.getOnlinePlayers()) {
            LevelManager.savePlayerData(player);
        }
        InventoryFileManager.save();
    }

}
