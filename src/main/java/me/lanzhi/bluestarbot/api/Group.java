package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.utils.ExternalResource;

import java.util.ArrayList;
import java.util.List;

public final class Group extends Contact implements SendAudioAble
{
    public Group(net.mamoe.mirai.contact.Group group)
    {
        super(group);
    }

    public net.mamoe.mirai.contact.Group getGroup()
    {
        return (net.mamoe.mirai.contact.Group) super.getContact();
    }

    @Override
    public String getName()
    {
        return getGroup().getName();
    }

    @Override
    public boolean nudge(long id)
    {
        NormalMember member=getGroup().get(id);
        if (member==null)
        {
            return false;
        }
        member.nudge().sendTo(getGroup());
        return true;
    }

    public void setName(String name)
    {
        getGroup().setName(name);
    }

    @Override
    public Type getType()
    {
        return Type.Group;
    }

    public NormalGroupMember getMember(long id)
    {
        return Mapping.map(getGroup().get(id));
    }

    public NormalGroupMember getBotAsMember()
    {
        return Mapping.map(getGroup().getBotAsMember());
    }

    public NormalGroupMember getOwner()
    {
        return Mapping.map(getGroup().getOwner());
    }

    public boolean containsMember(long id)
    {
        return getGroup().contains(id);
    }

    public boolean containsMember(User user)
    {
        return containsMember(user.getId());
    }

    public boolean quit()
    {
        return getGroup().quit();
    }

    public GroupSetting getSetting()
    {
        return Mapping.map(getGroup().getSettings());
    }

    public List<NormalGroupMember> getMembers()
    {
        List<NormalGroupMember> members=new ArrayList<>();
        for (NormalMember member: getGroup().getMembers())
        {
            members.add(Mapping.map(member));
        }
        return members;
    }

    public MemberPermission getBotPermission()
    {
        return Mapping.map(getGroup().getBotPermission());
    }

    public NormalGroupMember asGroupMember(User user)
    {
        if (user==null)
        {
            return null;
        }
        return Mapping.map(getGroup().get(user.getId()));
    }

    @Override
    public void sendAudio0(ExternalResource resource)
    {
        this.getGroup().sendMessage(this.getGroup().uploadAudio(resource.toAutoCloseable()));
    }

    @Override
    public String toString()
    {
        return "Group{bot:"+getBot().getId()+",id:"+getId()+"}";
    }
}
