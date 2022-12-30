package me.xjqsh.selfintroduce;

import me.xjqsh.selfintroduce.command.ICommandExecutor;
import me.xjqsh.selfintroduce.listener.IntroduceManager;
import me.xjqsh.selfintroduce.placeholder.IPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SelfIntroduce extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("data.yml", false);
        //noinspection ConstantConditions
        Bukkit.getPluginCommand("intro").setExecutor(new ICommandExecutor());
        Bukkit.getPluginManager().registerEvents(new IntroduceManager(),this);
        configManager = new ConfigManager();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new IPlaceHolder(this).register();
        }
    }

    @Override
    public void onDisable() {
        IntroduceManager.save();
    }

    public void onReload(boolean reloadData){
        ConfigManager.refreshConfig();
        if(reloadData){
            IntroduceManager.save();
            IntroduceManager.load();
        }
    }

    public static ConfigManager getConfigManager(){
        return plugin().configManager;
    }

    public static SelfIntroduce plugin(){
        return (SelfIntroduce) Bukkit.getPluginManager().getPlugin("SelfIntroduce");
    }
}
