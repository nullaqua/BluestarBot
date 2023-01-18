package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Mapping;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 机器人相关消息
 * 注: 虽然事件实现Cancellable,但不一定可以撤销
 */
public abstract class BluestarBotEvent extends Event implements Cancellable
{
    private final net.mamoe.mirai.event.events.BotEvent event;
    private boolean cancel=false;

    @Internal
    public net.mamoe.mirai.event.events.BotEvent getEvent()
    {
        return event;
    }

    @Internal
    public BluestarBotEvent(net.mamoe.mirai.event.events.BotEvent event)
    {
        super(true);
        this.event=event;
    }

    @Internal
    @Override
    public final HandlerList getHandlers()
    {
        return handlerList;
    }

    @Internal
    public static HandlerList handlerList=new HandlerList();

    @Internal
    public static HandlerList getHandlerList()
    {
        return handlerList;
    }

    /**
     * @return 事件关联的机器人
     */
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
