package me.xjqsh.selfintroduce;

import me.xjqsh.selfintroduce.listener.IntroduceManager;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversation {

    public static void askForMessage(Player player){
        new ConversationFactory(SelfIntroduce.plugin())
                .withModality(true)
                .withLocalEcho(false)
                .withTimeout(90)
                .withFirstPrompt(new introPromptCycle(true))
                .withEscapeSequence("cancel")
                .buildConversation(player).begin();
    }

    private static class introPromptCycle implements Prompt {
        private final boolean isFirst;
        private introPromptCycle(boolean isFirst){
            this.isFirst=isFirst;
        }
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            if(isFirst)return "";
            else return ConfigManager.getMessage("illegalinput");
        }
        @Override
        public boolean blocksForInput(@NotNull ConversationContext context) {
            return true;
        }
        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            if(input==null)throw new NullPointerException();
            if(input.length()>ConfigManager.getCharLimit())return new introPromptCycle(false);
            int counter=0;
            for(int i=0;i<input.length();i++){
                if(input.charAt(i)=='\\')counter++;
                if(counter>ConfigManager.getLineLimit()-1)return new introPromptCycle(false);
            }
            Pattern pattern = Pattern.compile(ConfigManager.getRegex());
            Matcher matcher = pattern.matcher(input);
            if(matcher.find())
                return new introPromptCycle(false);
            else {
                String player=((Player)context.getForWhom()).getName();
                IntroduceManager.setIntro(player,input);
                context.getForWhom().sendRawMessage(ConfigManager.getMessage("success") +'\n'+input.replace('\\','\n'));
                return END_OF_CONVERSATION;
            }
        }
    }
}
