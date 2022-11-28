package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.contact.Member;

public class GroupMember extends User
{

    public GroupMember(Member member)
    {
        super(member);
    }
    
    public Member getMember()
    {
        return (Member) super.getUser();
    }

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

    @Override
    public String getName()
    {
        return getMember().getNameCard().isEmpty()?getMember().getNick():getMember().getNameCard();
    }

    @Override
    public Contact.Type getChatType()
    {
        return Contact.Type.MEMBER;
    }

    @Override
    public Type getUserType()
    {
        return Type.MEMBER;
    }

    public String getNameCard()
    {
        return getMember().getNameCard();
    }

    public MemberPermission getPermission()
    {
        return Mapping.map(getMember().getPermission());
    }

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
