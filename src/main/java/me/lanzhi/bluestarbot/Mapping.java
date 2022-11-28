package me.lanzhi.bluestarbot;

import me.lanzhi.bluestarbot.api.Contact;
import me.lanzhi.bluestarbot.api.Friend;
import me.lanzhi.bluestarbot.api.Group;
import me.lanzhi.bluestarbot.api.MemberPermission;
import me.lanzhi.bluestarbot.api.OtherClient;
import me.lanzhi.bluestarbot.api.Stranger;
import me.lanzhi.bluestarbot.api.User;
import me.lanzhi.bluestarbot.api.*;
import net.mamoe.mirai.contact.*;

import java.util.ArrayList;
import java.util.List;

public final class Mapping
{
    private Mapping()
    {
    }

    public static List<?> map(List<?> l)
    {
        List<Object> list=new ArrayList<>();
        for (Object o:l)
        {
            list.add(map((Object) o));
        }
        return list;
    }

    public static Object map(Object o)
    {
        if (o instanceof ContactOrBot)
        {
            return map((ContactOrBot) o);
        }
        if (o instanceof net.mamoe.mirai.contact.MemberPermission)
        {
            return map((net.mamoe.mirai.contact.MemberPermission)o);
        }
        if (o instanceof GroupSetting)
        {
            return map((net.mamoe.mirai.contact.GroupSettings) o);
        }
        return o;
    }

    public static Contact map(ContactOrBot contact)
    {
        if (contact==null)
        {
            return null;
        }
        if (contact instanceof net.mamoe.mirai.Bot)
        {
            return new Bot((net.mamoe.mirai.Bot) contact);
        }
        if (contact instanceof net.mamoe.mirai.contact.OtherClient)
        {
            return new OtherClient((net.mamoe.mirai.contact.OtherClient) contact);
        }
        if (contact instanceof net.mamoe.mirai.contact.Friend)
        {
            return new Friend((net.mamoe.mirai.contact.Friend) contact);
        }
        if (contact instanceof net.mamoe.mirai.contact.Stranger)
        {
            return new Stranger((net.mamoe.mirai.contact.Stranger) contact);
        }
        if (contact instanceof net.mamoe.mirai.contact.Group)
        {
            return new Group((net.mamoe.mirai.contact.Group) contact);
        }
        if (contact instanceof AnonymousMember)
        {
            return new AnonymousGroupMember((AnonymousMember) contact);
        }
        if (contact instanceof NormalMember)
        {
            return new NormalGroupMember((NormalMember) contact);
        }
        if (contact instanceof Member)
        {
            return new GroupMember((Member) contact);
        }
        if (contact instanceof net.mamoe.mirai.contact.User)
        {
            return new User((UserOrBot) contact)
            {
                @Override
                public Type getUserType()
                {
                    return null;
                }

                @Override
                public Contact.Type getChatType()
                {
                    return null;
                }
            };
        }
        if (contact instanceof net.mamoe.mirai.contact.Contact)
        {
            return new Contact((ContactOrBot) contact)
            {
                @Override
                public String getName()
                {
                    return "null";
                }

                @Override
                public Type getChatType()
                {
                    return null;
                }
            };
        }
        return null;
    }

    public static Bot map(net.mamoe.mirai.Bot bot)
    {
        return (Bot) map((ContactOrBot) bot);
    }

    public static User map(UserOrBot contact)
    {
        return (User) map((ContactOrBot) contact);
    }

    public static Friend map(net.mamoe.mirai.contact.Friend friend)
    {
        return (Friend) map((ContactOrBot) friend);
    }

    public static Group map(net.mamoe.mirai.contact.Group group)
    {
        return (Group) map((ContactOrBot) group);
    }

    public static Stranger map(net.mamoe.mirai.contact.Stranger stranger)
    {
        return (Stranger) map((ContactOrBot) stranger);
    }

    public static GroupMember map(Member member)
    {
        return (GroupMember) map((ContactOrBot) member);
    }

    public static AnonymousGroupMember map(AnonymousMember member)
    {
        return (AnonymousGroupMember) map((ContactOrBot) member);
    }

    public static NormalGroupMember map(NormalMember member)
    {
        return (NormalGroupMember) map((ContactOrBot) member);
    }

    public static GroupSetting map(GroupSettings settings)
    {
        if (settings instanceof GroupSetting)
        {
            return (GroupSetting) settings;
        }
        return settings!=null?new GroupSetting(settings):null;
    }

    public static OtherClient map(net.mamoe.mirai.contact.OtherClient otherClient)
    {
        return (OtherClient) map((ContactOrBot) otherClient);
    }

    public static net.mamoe.mirai.contact.Contact toContact(ContactOrBot contactOrBot)
    {
        if (contactOrBot instanceof net.mamoe.mirai.Bot)
        {
            return ((net.mamoe.mirai.Bot) contactOrBot).getAsFriend();
        }
        else if (contactOrBot instanceof net.mamoe.mirai.contact.Contact)
        {
            return (net.mamoe.mirai.contact.Contact) contactOrBot;
        }
        return null;
    }

    public static net.mamoe.mirai.contact.User toUser(UserOrBot userOrBot)
    {
        if (userOrBot instanceof net.mamoe.mirai.Bot)
        {
            return ((net.mamoe.mirai.Bot) userOrBot).getAsFriend();
        }
        if (userOrBot instanceof net.mamoe.mirai.contact.User)
        {
            return (net.mamoe.mirai.contact.User) userOrBot;
        }
        return null;
    }

    public static MemberPermission map(net.mamoe.mirai.contact.MemberPermission permission)
    {
        if (permission==null)
        {
            return null;
        }
        switch (permission)
        {
            case MEMBER:
                return MemberPermission.MEMBER;
            case ADMINISTRATOR:
                return MemberPermission.ADMIN;
            case OWNER:
                return MemberPermission.OWNER;
        }
        return null;
    }
}
