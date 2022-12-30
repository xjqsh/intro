package me.xjqsh.selfintroduce.listener;

import me.xjqsh.selfintroduce.ConfigManager;
import me.xjqsh.selfintroduce.SelfIntroduce;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IntroduceManager implements Listener {

    private static IntroduceManager INSTANCE;

    private final HashMap<String,String> introList = new HashMap<>();

    public IntroduceManager(){
        INSTANCE=this;
        load();
    }

    public static void setIntro(String player,String intro){
        INSTANCE.introList.put(player, intro);
    }

    @NotNull
    public static String getIntro(String player){
        if(!INSTANCE.introList.containsKey(player))return ConfigManager.getDefaultIntro();
        return INSTANCE.introList.get(player);
    }

    public static void load(){
        File file = new File(SelfIntroduce.plugin().getDataFolder(),"data.yml");
        Configuration data = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection cs = data.getConfigurationSection("data");
        if (cs != null) {
            for(String player : cs.getKeys(false)){
                INSTANCE.introList.put(player, data.getString("data."+player));
            }
        }
    }

    public static void save(){
        File file = new File(SelfIntroduce.plugin().getDataFolder(),"data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        for(String player : INSTANCE.introList.keySet()){
            data.set("data."+player,INSTANCE.introList.get(player));
        }
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
