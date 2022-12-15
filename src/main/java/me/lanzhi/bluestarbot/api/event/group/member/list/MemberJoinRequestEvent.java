package me.lanzhi.bluestarbot.api.event.group.member.list;

import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.NormalGroupMember;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.GroupEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

/**
 * 用户加入群聊申请事件,只有机器人具有权限才能收到
 *
 * @see MemberJoinEvent 用户加入群聊事件
 */
public final class MemberJoinRequestEvent extends BluestarBotEvent implements GroupEvent
{
    public MemberJoinRequestEvent(net.mamoe.mirai.event.events.MemberJoinRequestEvent event)
    {
        super(event);
    }

    /**
     * @return 群聊
     */
    @Override
    public Group getGroup()
    {
        return Mapping.map(getEvent().getGroup());
    }

    @Override
    public net.mamoe.mirai.event.events.MemberJoinRequestEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.MemberJoinRequestEvent) super.getEvent();
    }

    /**
     * @return 用户的id
     */
    public long getUserId()
    {
        return getEvent().getFromId();
    }

    /**
     * @return 用户名
     */
    public String getUserName()
    {
        return getEvent().getFromNick();
    }

    /**
     * @return 申请消息
     */
    public String getMessage()
    {
        return getEvent().getMessage();
    }

    /**
     * @return 邀请人
     */
    public NormalGroupMember getInvitor()
    {
        return Mapping.map(getEvent().getInvitor());
    }

    /**
     * 同意
     *
     * @see MemberJoinEvent 用户加入群聊事件,同意后会触发
     */
    public void accept()
    {
        getEvent().accept();
    }

    /**
     * 拒绝请求
     */
    public void reject()
    {
        reject(false);
    }

    /**
     * 拒绝请求
     *
     * @param blackList 是否加入黑名单
     */
    public void reject(boolean blackList)
    {
        reject(blackList,"");
    }

    /**
     * 拒绝请求
     *
     * @param blackList 是否加入黑名单
     * @param message   拒绝原因
     */
    public void reject(boolean blackList,String message)
    {
        getEvent().reject(blackList,message);
    }

    /**
     * 忽略请求
     */
    public void ignore()
    {
        ignore(false);
    }

    /**
     * 忽略请求
     *
     * @param blackList 是否加入黑名单
     */
    public void ignore(boolean blackList)
    {
        getEvent().ignore(blackList);
    }
}
