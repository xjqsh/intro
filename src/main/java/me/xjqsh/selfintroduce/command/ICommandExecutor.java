package me.xjqsh.selfintroduce.command;

import me.xjqsh.selfintroduce.ConfigManager;
import me.xjqsh.selfintroduce.Conversation;
import me.xjqsh.selfintroduce.SelfIntroduce;
import me.xjqsh.selfintroduce.listener.IntroduceManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ICommandExecutor implements org.bukkit.command.CommandExecutor {
    /**
     * 执行传入的命令，返回它的执行结果.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0){
            if(!sender.hasPermission("intro.change")){
                sender.sendMessage(ConfigManager.getMessage("nopermission"));
                return true;
            }
            if(sender instanceof Player){

                if(((Player) sender).isConversing()){
                    return true;
                }

                List<String> list = ConfigManager.startEditing();
                for(String str : list){
                    sender.sendMessage(str);
                }
                Conversation.askForMessage(((Player) sender).getPlayer());

            }else{
                sender.sendMessage(ConfigManager.getMessage("needplayer"));
            }
        }
        if (args.length>0){
            if(args[0].equals("reload")){
                if(!sender.hasPermission("intro.reload")){
                    sender.sendMessage(ConfigManager.getMessage("nopermission"));
                    return true;
                }
                SelfIntroduce.plugin().onReload(args.length == 2 && args[1].equals("all"));
                sender.sendMessage(ConfigManager.getMessage("reload"));
                sender.sendMessage(ConfigManager.buffer("&6maxChar:")+ConfigManager.getCharLimit());
                sender.sendMessage(ConfigManager.buffer("&6maxLine:")+ConfigManager.getLineLimit());
            }else{
                if(args.length==1){
                    if(!sender.hasPermission("intro.change.other")){
                        sender.sendMessage(ConfigManager.getMessage("nopermission"));
                        return true;
                    }

                    Player player = Bukkit.getPlayer(args[0]);
                    if(player==null){
                        sender.sendMessage(ConfigManager.getMessage("playernotexist"));
                        return true;
                    }
                    List<String> list = ConfigManager.startEditing();
                    for(String str : list){
                        player.sendMessage(str);
                    }
                    Conversation.askForMessage(player);

                }else {
                    sender.sendMessage(ConfigManager.getMessage("help"));
                }
            }
            return true;
        }
        sender.sendMessage(ConfigManager.getMessage("help"));
        return true;
    }
}
