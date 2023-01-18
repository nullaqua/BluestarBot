package me.lanzhi.bluestarbot.api.event.friend;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Mapping;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;

/**
 * 新朋友添加申请事件
 *
 * @see FriendAddEvent 好友添加事件
 */
public final class NewFriendRequestEvent extends BluestarBotEvent
{
    @Internal
    public NewFriendRequestEvent(net.mamoe.mirai.event.events.NewFriendRequestEvent event)
    {
        super(event);
    }

    /**
     * @return 对方名称
     */
    public String getUserName()
    {
        return getEvent().getFromNick();
    }

    @Internal
    @Override
    public net.mamoe.mirai.event.events.NewFriendRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.NewFriendRequestEvent) super.getEvent();
    }

    /**
     * @return 对方qqid
     */
    public long getUserId()
    {
        return getEvent().getFromId();
    }

    /**
     * 从哪个群聊申请加好友
     *
     * @return 群聊,若不是从群聊加好友则返回null
     */
    public Group getFromGroup()
    {
        return Mapping.map(getEvent().getFromGroup());
    }

    /**
     * 是否从群聊加好友
     *
     * @return true代表是从群聊加好友
     */
    public boolean isFromGroup()
    {
        return getEvent().getFromGroup()!=null;
    }

    /**
     * 拒绝好友申请
     */
    public void reject()
    {
        reject(false);
    }

    /**
     * 拒绝好友申请
     *
     * @param blackList 是否加入黑名单,true代表加入
     */
    public void reject(boolean blackList)
    {
        getEvent().reject(blackList);
    }

    /**
     * 同意好友申请
     *
     * @see FriendAddEvent 同意后会触发此事件
     */
    public void accept()
    {
        getEvent().accept();
    }

    /**
     * @return 申请消息
     */
    public String getMessage()
    {
        return getEvent().getMessage();
    }
}
