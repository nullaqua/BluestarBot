package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Contact;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import org.bukkit.event.Cancellable;

import java.io.InputStream;

public final class BeforeImageUploadEvent extends BluestarBotEvent implements Cancellable
{
    private boolean cancel=false;

    public BeforeImageUploadEvent(net.mamoe.mirai.event.events.BeforeImageUploadEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BeforeImageUploadEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BeforeImageUploadEvent) super.getEvent();
    }

    public Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    public InputStream getImage()
    {
        return getEvent().getSource().inputStream();
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
