package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;

public final class BotOfflineEvent extends BluestarBotEvent implements BotEvent
{
    public BotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BotOfflineEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotOfflineEvent) super.getEvent();
    }

    public Type getType()
    {
        if (getEvent() instanceof net.mamoe.mirai.event.events.BotOfflineEvent.Active)
        {
            return Type.Active;
        }
        if (getEvent() instanceof net.mamoe.mirai.event.events.BotOfflineEvent.Force)
        {
            return Type.Force;
        }
        if (getEvent() instanceof net.mamoe.mirai.event.events.BotOfflineEvent.Dropped)
        {
            return Type.Dropped;
        }
        if (getEvent() instanceof net.mamoe.mirai.event.events.BotOfflineEvent.RequireReconnect)
        {
            return Type.RequireReconnect;
        }
        if (getEvent() instanceof net.mamoe.mirai.event.events.BotOfflineEvent.MsfOffline)
        {
            return Type.MsfOffline;
        }
        return null;
    }

    public String getMessage()
    {
        if (getType()==Type.Force)
        {
            ((net.mamoe.mirai.event.events.BotOfflineEvent.Force) getEvent()).getMessage();
        }
        return null;
    }

    public enum Type
    {
        Active,Force,Dropped,RequireReconnect,MsfOffline
    }
}
