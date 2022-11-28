package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.contact.AnonymousMember;

public final class AnonymousGroupMember extends GroupMember
{
    public AnonymousGroupMember(AnonymousMember member)
    {
        super(member);
    }

    @Override
    public String toString()
    {
        return "AnonymousGroupMember{bot:"+getBot().getId()+",group:"+getGroup().getId()+"}";
    }
}
