package me.lanzhi.bluestarbot.api.event.bot;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.BotEvent;
import org.jetbrains.annotations.Nullable;

/**
 * bot退出登录事件
 * 注: 除被挤下线外,会自动尝试重新连接,被挤下线不会
 */
public final class BotOfflineEvent extends BluestarBotEvent implements BotEvent
{
    @Internal
    public BotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event)
    {
        super(event);
    }

    /**
     * 获取掉线消息,可能为null
     *
     * @return 消息
     */
    @Nullable
    public String getMessage()
    {
        if (getType()==Type.Force)
        {
            ((net.mamoe.mirai.event.events.BotOfflineEvent.Force) getEvent()).getMessage();
        }
        return null;
    }

    /**
     * 退出原因类型
     *
     * @return 类型
     * @see Type 类型
     */
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

    @Internal
    @Override
    public net.mamoe.mirai.event.events.BotOfflineEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BotOfflineEvent) super.getEvent();
    }

    public enum Type
    {
        /**
         * 主动退出
         */
        Active,
        /**
         * 被挤下线
         */
        Force,
        /**
         * 因网络问题断开
         */
        Dropped,
        /**
         * 服务器主动要求更换另一个服务器
         */
        RequireReconnect,
        /**
         * 被服务器断开
         */
        MsfOffline
    }
}
