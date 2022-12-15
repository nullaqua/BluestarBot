package me.lanzhi.bluestarbot.api.contact.group;

/**
 * 群员在群内的权限
 */
public enum MemberPermission
{
    /**
     * 普通群成员
     */
    MEMBER,
    /**
     * 管理员
     */
    ADMIN,
    /**
     * 群主
     */
    OWNER;

    /**
     * 是否具有管理员权限,既是管理员或群主
     *
     * @return 有则返回true
     */
    public boolean isAdmin()
    {
        return this!=MEMBER;
    }

    /**
     * 是否是群主
     *
     * @return 是则返回true
     */
    public boolean isOwner()
    {
        return this==OWNER;
    }
}
