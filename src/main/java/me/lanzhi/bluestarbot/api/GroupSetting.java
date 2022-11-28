package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.contact.GroupSettings;

public final class GroupSetting implements GroupSettings
{
    private final GroupSettings settings;

    public GroupSetting(GroupSettings settings)
    {
        assert settings!=null;
        this.settings=settings;
    }

    @Override
    public boolean isAllowMemberInvite()
    {
        return settings.isAllowMemberInvite();
    }

    @Override
    public void setAllowMemberInvite(boolean b)
    {
        settings.setAllowMemberInvite(b);
    }

    @Override
    public boolean isAnonymousChatEnabled()
    {
        return settings.isAnonymousChatEnabled();
    }

    @Override
    public void setAnonymousChatEnabled(boolean b)
    {
        settings.setAnonymousChatEnabled(b);
    }

    @Override
    public boolean isAutoApproveEnabled()
    {
        return settings.isAutoApproveEnabled();
    }

    @Override
    public boolean isMuteAll()
    {
        return settings.isMuteAll();
    }

    @Override
    public void setMuteAll(boolean b)
    {
        settings.setMuteAll(b);
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
