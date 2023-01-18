package me.lanzhi.bluestarbot.api.contact.group;

import me.lanzhi.bluestarbot.api.Internal;
import net.mamoe.mirai.contact.NormalMember;

import java.util.Date;

/**
 * 普通群成员
 */
public final class NormalGroupMember extends GroupMember
{
    @Internal
    public NormalGroupMember(NormalMember member)
    {
        super(member);
    }

    @Internal
    public NormalMember getMember()
    {
        return (NormalMember) super.getMember();
    }

    /**
     * 加入群聊的时间
     *
     * @return 时间
     */
    public Date getJoinTime()
    {
        return new Date(getMember().getJoinTimestamp()*1000L);
    }

    /**
     * 最后一次在群内发言的时间
     *
     * @return 时间
     */
    public Date getLastSpeakTime()
    {
        return new Date(getMember().getLastSpeakTimestamp()*1000L);
    }

    /**
     * 被禁言时间
     *
     * @return 时间,单位秒
     */
    public int getMuteTime()
    {
        return getMember().getMuteTimeRemaining();
    }

    /**
     * 将此群员提出群,需要权限
     *
     * @return 成功为true
     */
    public boolean kick()
    {
        return kick("");
    }

    /**
     * 将此成员踢出群,需要权限
     *
     * @param message 踢出原因
     * @return 成功为true
     */
    public boolean kick(String message)
    {
        return kick(message,false);
    }

    /**
     * 将此成员踢出群,需要权限
     *
     * @param message 踢出原因
     * @param lock    加入黑名单
     * @return 成功为true
     */
    public boolean kick(String message,boolean lock)
    {
        try
        {
            getMember().kick(message,lock);
            return true;
        }
        catch (Throwable e)
        {
            return false;
        }
    }

    /**
     * 解除禁言,需要权限
     *
     * @return 成功为true
     * @see GroupMember#mute(int) 禁言
     * @see NormalMember#isMuted() 正在被禁言
     */
    public boolean unmute()
    {
        try
        {
            getMember().unmute();
            return true;
        }
        catch (Throwable throwable)
        {
            return false;
        }
    }

    /**
     * 正在被禁言
     *
     * @return 若正在禁言为true
     * @see GroupMember#mute(int) 禁言
     * @see NormalMember#unmute() 解除禁言
     */
    public boolean isMute()
    {
        return getMember().isMuted();
    }

    /**
     * 将此成员赋予/取消管理员权限,需要权限
     *
     * @param b true为赋予,false取消
     * @return 成功为true
     * @see NormalMember#getPermission() 获取权限
     */
    public boolean setAdmin(boolean b)
    {
        try
        {
            getMember().modifyAdmin(b);
            return true;
        }
        catch (Throwable throwable)
        {
            return false;
        }
    }

    /**
     * 群员的头衔
     *
     * @return 群员头衔
     * @see NormalGroupMember#setTitle(String)
     */
    public String getTitle()
    {
        return getMember().getSpecialTitle();
    }

    /**
     * 设置群员头衔
     *
     * @param title 头衔
     * @return 成功为true
     * @see NormalGroupMember#getTitle() 获取头衔
     */
    public boolean setTitle(String title)
    {
        try
        {
            getMember().setSpecialTitle(title);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 设置群昵称(群名片)
     *
     * @param name 名称
     * @return 成功为true
     */
    public boolean setNameCard(String name)
    {
        try
        {
            getMember().setNameCard(name);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public Type getType()
    {
        return Type.NormalMember;
    }

    @Override
    public String toString()
    {
        return "NormalGroupMember{bot:"+getBot().getId()+",group:"+getGroup().getId()+",id:"+getId()+"}";
    }
}
