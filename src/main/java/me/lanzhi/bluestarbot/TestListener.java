package me.lanzhi.bluestarbot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.MessageReceivedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
        plugin.getListeners().forEach(consumer -> consumer.accept(event));
        if (event instanceof MessageReceivedEvent)
        {
            MessageReceivedEvent messageReceivedEvent=(MessageReceivedEvent)event;
            System.out.println(messageReceivedEvent.getSender()+": "+messageReceivedEvent.getMessageAsCode());
        }
    }
}