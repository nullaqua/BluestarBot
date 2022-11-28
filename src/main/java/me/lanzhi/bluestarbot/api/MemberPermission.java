package me.lanzhi.bluestarbot.api;

public enum MemberPermission
{
    MEMBER,ADMIN,OWNER;

    public boolean isAdmin()
    {
        return this!=MEMBER;
    }

    public boolean isOwner()
    {
        return this==OWNER;
    }
}
