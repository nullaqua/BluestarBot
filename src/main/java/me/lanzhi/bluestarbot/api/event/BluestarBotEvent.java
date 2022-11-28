package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Bot;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class BluestarBotEvent extends Event implements Cancellable
{
    private final net.mamoe.mirai.event.events.BotEvent event;
    private boolean cancel=false;

    public net.mamoe.mirai.event.events.BotEvent getEvent()
    {
        return event;
    }

    public BluestarBotEvent(net.mamoe.mirai.event.events.BotEvent event)
    {
        super(true);
        this.event=event;
    }

    @Override
    public final HandlerList getHandlers()
    {
        return handlerList;
    }

    public static HandlerList handlerList=new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlerList;
    }

    public final Bot getBot()
    {
        return Mapping.map(getEvent().getBot());
    }

    @Override
    public boolean isCancelled()
    {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        this.cancel=cancel;
    }
}
