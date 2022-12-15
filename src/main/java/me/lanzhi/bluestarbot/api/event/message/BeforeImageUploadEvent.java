package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import me.lanzhi.bluestarbot.internal.Mapping;
import org.bukkit.event.Cancellable;

import java.io.InputStream;

/**
 * 机器人上传图片事件
 *
 * @see Contact#sendMessage(MessageChain) 发送图片触发,取消此事件可能导致发送失败
 */
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
