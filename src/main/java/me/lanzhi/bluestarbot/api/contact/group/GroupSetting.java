package me.lanzhi.bluestarbot.api.contact.group;

import net.mamoe.mirai.contact.GroupSettings;

/**
 * 群聊设置
 */
public final class GroupSetting
{
    private final GroupSettings settings;

    public GroupSetting(GroupSettings settings)
    {
        assert settings!=null;
        this.settings=settings;
    }

    /**
     * 是否允许邀请其他用户进群
     *
     * @return 允许则返回true
     * @see me.lanzhi.bluestarbot.api.event.group.setting.GroupAllowMemberInviteEvent
     */
    public boolean isAllowMemberInvite()
    {
        return settings.isAllowMemberInvite();
    }

    /**
     * 设置是否允许邀请进群,需要管理员权限
     *
     * @param b 允许为true
     * @return 操作成功返回true
     */
    public boolean setAllowMemberInvite(boolean b)
    {
        try
        {
            settings.setAllowMemberInvite(b);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 是否允许匿名聊天
     *
     * @return 允许为true
     */
    public boolean isAnonymousChatEnabled()
    {
        return settings.isAnonymousChatEnabled();
    }

    /**
     * 设置是否允许匿名聊天
     *
     * @param b 允许为true
     * @return 操作成功返回true
     */
    public boolean setAnonymousChatEnabled(boolean b)
    {
        try
        {
            {
                settings.setAnonymousChatEnabled(b);
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 是否加群自动审批
     *
     * @return 自动审批为true
     */
    public boolean isAutoApproveEnabled()
    {
        return settings.isAutoApproveEnabled();
    }

    /**
     * 正在全体禁言
     *
     * @return 禁言为true
     */
    public boolean isMuteAll()
    {
        return settings.isMuteAll();
    }

    /**
     * 设置全体禁言
     *
     * @param b true为禁言
     * @return 操作成功为true
     */
    public boolean setMuteAll(boolean b)
    {
        try
        {
            {
                settings.setMuteAll(b);
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof GroupSetting)
        {
            return settings.equals(((GroupSetting) obj).settings);
        }
        return settings.equals(obj);
    }
}
