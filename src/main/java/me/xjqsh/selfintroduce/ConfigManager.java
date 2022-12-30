package me.xjqsh.selfintroduce;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigManager {

    private Configuration config;

    private static ConfigManager INSTANCE;

    public ConfigManager(){
        this.config = SelfIntroduce.plugin().getConfig();
        INSTANCE=this;
    }

    public static void refreshConfig(){
        SelfIntroduce.plugin().reloadConfig();
        INSTANCE.config = SelfIntroduce.plugin().getConfig();
    }

    public static @NotNull List<String> startEditing(){
        List<String> list = INSTANCE.config.getStringList("message.startediting");
        for (int i=0;i<list.size();i++){
            list.set(i,buffer(list.get(i)));
        }
        return list;
    }

    public static @NotNull String getMessage(String str){
        return buffer(INSTANCE.config.getString("message." + str));
    }

    public static int getCharLimit(){
        return INSTANCE.config.getInt("config.maxChar");
    }

    public static int getLineLimit(){
        return INSTANCE.config.getInt("config.maxLine");
    }

    public static String getRegex(){
        return INSTANCE.config.getString("config.regex");
    }

    public static String getDefaultIntro() {
        return INSTANCE.config.getString("config.default");
    }

    public static String buffer(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }


}
