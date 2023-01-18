package me.lanzhi.bluestarbot.api.contact.group;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.SendAudioAble;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.contact.AudioSupported;
import net.mamoe.mirai.contact.NormalMember;

import java.util.ArrayList;
import java.util.List;

/**
 * 群聊
 *
 * @see Contact 聊天频道
 * @see SendAudioAble 支持发送语音
 */
public final class Group extends Contact implements SendAudioAble
{
    @Internal
    public Group(net.mamoe.mirai.contact.Group group)
    {
        super(group);
    }

    @Internal
    public net.mamoe.mirai.contact.Group getGroup()
    {
        return (net.mamoe.mirai.contact.Group) super.getContact();
    }

    /**
     * 向群内某人发生一个戳一戳
     *
     * @param id 发送对象
     * @return 若群内存在此人,且发送成功返回true,否则返回false
     * @see me.lanzhi.bluestarbot.api.event.message.NudgeEvent 戳一戳事件
     */
    @Override
    public boolean nudge(long id)
    {
        NormalMember member=getGroup().get(id);
        if (member==null)
        {
            return false;
        }
        return member.nudge().sendTo(getGroup());
    }

    /**
     * 与 {@link #getMember(long)} 相同
     */
    @Override
    public NormalGroupMember getUser(long id)
    {
        return getMember(id);
    }

    /**
     * 群聊名称
     *
     * @return 名称
     * @see me.lanzhi.bluestarbot.api.event.group.setting.GroupNameChangeEvent 群聊改名事件
     */
    @Override
    public String getName()
    {
        return getGroup().getName();
    }

    /**
     * 设置群聊名称
     *
     * @param name 名称
     * @see me.lanzhi.bluestarbot.api.event.group.setting.GroupNameChangeEvent 群聊改名事件
     */
    public void setName(String name)
    {
        getGroup().setName(name);
    }

    @Override
    public Type getType()
    {
        return Type.Group;
    }

    /**
     * 获取群员,当不存在时返回null
     *
     * @param id 群员的id
     * @return 群员实例
     */
    public NormalGroupMember getMember(long id)
    {
        return Mapping.map(getGroup().get(id));
    }

    /**
     * 将机器人以群员的方式获取
     *
     * @return 群员形式的机器人
     */
    public NormalGroupMember getBotAsMember()
    {
        return Mapping.map(getGroup().getBotAsMember());
    }

    /**
     * 获取群主,以群员形式返回
     *
     * @return 此群聊的群主
     */
    public NormalGroupMember getOwner()
    {
        return Mapping.map(getGroup().getOwner());
    }

    /**
     * 群内是否有某人,用户类型无所谓,仅匹配ID
     * 与{@link #containsMember(long)}结果相同
     * @param user 用户
     * @return 若有则返回true,否则返回false
     */
    public boolean containsMember(User user)
    {
        return containsMember(user.getId());
    }

    /**
     * 群内是否有某人
     *
     * @param id qqid
     * @return 若有则返回true,否则返回false
     */
    public boolean containsMember(long id)
    {
        return getGroup().contains(id);
    }

    /**
     * 让机器人退出群聊
     *
     * @return 退出成功返回true,若机器人是群主或已退出返回false
     */
    public boolean quit()
    {
        try
        {
            return getGroup().quit();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 获取群设置
     *
     * @return 群设置
     * @see GroupSetting 群聊设置
     */
    public GroupSetting getSetting()
    {
        return Mapping.map(getGroup().getSettings());
    }

    /**
     * 获取所有群员
     *
     * @return 所有群员
     */
    public List<NormalGroupMember> getMembers()
    {
        List<NormalGroupMember> members=new ArrayList<>();
        for (NormalMember member: getGroup().getMembers())
        {
            members.add(Mapping.map(member));
        }
        return members;
    }

    /**
     * 获取机器人在群中的权限
     *
     * @return 机器人的权限
     * @see MemberPermission 群员的权限
     */
    public MemberPermission getBotPermission()
    {
        return Mapping.map(getGroup().getBotPermission());
    }

    /**
     * 将一用户以群成员的形式返回,等同于getMember(user.getId()),若user为null或不存在此人则返回null
     *
     * @param user 用户
     * @return 群员形式的用户
     */
    public NormalGroupMember asGroupMember(User user)
    {
        if (user==null)
        {
            return null;
        }
        return getMember(user.getId());
    }

    @Override
    public AudioSupported getAudioSupported()
    {
        return getGroup();
    }

    @Override
    public String toString()
    {
        return "Group{bot:"+getBot().getId()+",id:"+getId()+"}";
    }
}
