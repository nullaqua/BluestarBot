package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.contact.NormalMember;

public final class NormalGroupMember extends GroupMember
{

    public NormalGroupMember(NormalMember member)
    {
        super(member);
    }

    public NormalMember getMember()
    {
        return (NormalMember) super.getMember();
    }

    public int getJoinTime()
    {
        return getMember().getJoinTimestamp();
    }

    public int getLastSpeakTime()
    {
        return getMember().getLastSpeakTimestamp();
    }

    public int getMuteTime()
    {
        return getMember().getMuteTimeRemaining();
    }

    public boolean kick()
    {
        return kick("");
    }

    public boolean kick(String message)
    {
        return kick(message,false);
    }

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

    public boolean isMute()
    {
        return getMember().isMuted();
    }

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

    public String getTitle()
    {
        return getMember().getSpecialTitle();
    }

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
