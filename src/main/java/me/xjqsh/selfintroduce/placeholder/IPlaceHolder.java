package me.xjqsh.selfintroduce.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xjqsh.selfintroduce.SelfIntroduce;
import me.xjqsh.selfintroduce.listener.IntroduceManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class IPlaceHolder extends PlaceholderExpansion {
    private final SelfIntroduce plugin;

    public IPlaceHolder(SelfIntroduce plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "xjqsh";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "intro";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("self")) {
            if(player==null)return null;
            else {
                String intro = IntroduceManager.getIntro(player.getName());
                return intro.replace('\\','\n');
            }
        }
        if(params.matches("other_.+")){
            String target = params.replace("other_","");
            return IntroduceManager.getIntro(target).replace('\\','\n');
        }
        return null;
    }
}
