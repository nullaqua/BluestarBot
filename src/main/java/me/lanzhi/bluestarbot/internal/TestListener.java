package me.lanzhi.bluestarbot.internal;

import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.GroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import me.lanzhi.bluestarbot.api.event.bot.BotOnlineEvent;
import me.lanzhi.bluestarbot.api.event.bot.BotReLoginEvent;
import me.lanzhi.bluestarbot.api.event.message.received.FriendMessageEvent;
import me.lanzhi.bluestarbot.api.event.message.received.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.lanzhi.bluestarbot.internal.Utils.logger;

/**
 * 消息监听,发送日志
 */
@Internal
public final class TestListener implements Listener
{
    private final BluestarBotPlugin plugin;

    public TestListener(BluestarBotPlugin plugin)
    {
        this.plugin=plugin;
    }

    @EventHandler
    public void onMessage(BluestarBotEvent event)
    {
        plugin.getListeners().forEach(consumer->consumer.accept(event));
        if (!(event instanceof MessageReceivedEvent))
        {
            return;
        }
        if (event instanceof GroupMessageEvent)
        {
            Group group=((GroupMessageEvent) event).getGroup();
            GroupMember member=((GroupMessageEvent) event).getMember();
            logger().info(String.format("[%s(%d)]%s(%d) : %s",
                                        group.getName(),
                                        group.getId(),
                                        member.getName(),
                                        member.getId(),
                                        ((GroupMessageEvent) event).getMessageAsString()));
            return;
        }
        User user=((MessageReceivedEvent) event).getSender();
        logger().info(String.format("%s%d : %s",
                                    user.getName(),
                                    user.getId(),
                                    ((MessageReceivedEvent) event).getMessageAsString()));
    }

    @EventHandler
    public void onLogin(BotOnlineEvent event)
    {
        logger().info("Bot online:"+event.getBot().getId());
    }
}