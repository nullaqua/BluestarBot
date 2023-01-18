package me.lanzhi.bluestarbot.api.contact.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.contact.Member;

/**
 * 一个群员
 * <p>注: 使用{@code GroupMember.sendMessage()}是以群临时会话的形式发送消息
 * 若想在群内发送,请使用{@code getGroup().sendMessage()}
 *
 * <p>一个群员实例是特定群聊的某个群员。即使是不同群聊的同一人，也需要获取不同实例
 *
 * @see AnonymousGroupMember 匿名群员
 * @see NormalGroupMember 普通群员
 */
public class GroupMember extends User
{
    @Internal
    public GroupMember(Member member)
    {
        super(member);
    }

    @Internal
    public Member getMember()
    {
        return (Member) super.getUser();
    }

    /**
     * 让此群员禁言,机器人需要有权限
     *
     * @param time 禁言时间,单位:秒,最小0秒,最多30天
     * @return 禁言成功返回true,否则返回false
     * @see NormalGroupMember#isMute() 正在被禁言
     * @see NormalGroupMember#unmute() 取消禁言
     */
    public boolean mute(int time)
    {
        try
        {
            getMember().mute(time);
            return true;
        }
        catch (Throwable e)
        {
            return false;
        }
    }

    /**
     * 群员在群聊中的显示名称,既优先显示群昵称,不存在时返回昵称
     *
     * @return 名称
     */
    @Override
    public String getName()
    {
        return getMember().getNameCard().isEmpty()?getMember().getNick():getMember().getNameCard();
    }

    @Override
    public Type getType()
    {
        return Type.Unknown;
    }

    /**
     * 获取群昵称,未设置时为空
     *
     * @return 群昵称
     * @see #getName() 获取显示名称
     */
    public String getNameCard()
    {
        return getMember().getNameCard();
    }

    /**
     * 获取昵称,不一定是群内显示的名称
     *
     * @return 昵称
     * @see #getName() 获取显示名称
     */
    public String getNick()
    {
        return getMember().getNick();
    }

    /**
     * 获取群员在群内权限
     * @see MemberPermission 群员的权限
     * @return 群员的权限
     */
    public MemberPermission getPermission()
    {
        return Mapping.map(getMember().getPermission());
    }

    /**
     * 此群员所属的群聊
     * @return 所属的群聊
     */
    public Group getGroup()
    {
        return Mapping.map(getMember().getGroup());
    }

    @Override
    public String toString()
    {
        return "GroupMember{bot:"+getBot().getId()+",group:"+getGroup().getId()+"}";
    }
}
