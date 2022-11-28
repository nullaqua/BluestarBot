package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.Mapping;
import me.lanzhi.bluestarbot.api.Contact;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

import java.io.InputStream;

public final class ImageUploadEvent extends BluestarBotEvent
{
    public ImageUploadEvent(net.mamoe.mirai.event.events.ImageUploadEvent event)
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
}
