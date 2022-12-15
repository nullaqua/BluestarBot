package me.lanzhi.bluestarbot.internal;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.contact.OtherClient;
import me.lanzhi.bluestarbot.api.contact.Stranger;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.api.contact.group.MemberPermission;
import me.lanzhi.bluestarbot.api.contact.group.*;
import me.lanzhi.bluestarbot.api.event.message.received.GroupMessageEvent;
import me.lanzhi.bluestarbot.api.message.Message;
import me.lanzhi.bluestarbot.api.message.MessageChain;
import me.lanzhi.bluestarbot.api.message.*;
import me.lanzhi.bluestarbot.internal.message.*;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Dice;
import net.mamoe.mirai.message.data.Face;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.ForwardMessage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MusicShare;
import net.mamoe.mirai.message.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
                public Contact.Type getType()
                {
                    return Type.Unknown;
                }
            };
        }
        if (contact instanceof net.mamoe.mirai.contact.Contact)
        {
            return new Contact((ContactOrBot) contact)
            {
                @Override
                public boolean nudge(long id)
                {
                    return false;
                }

                @Override
                public User getUser(long id)
                {
                    return null;
                }

                @Override
                public String getName()
                {
                    return "Unknown";
                }

                @Override
                public Type getType()
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
        return settings!=null?new GroupSetting(settings):null;
    }

    public static OtherClient map(net.mamoe.mirai.contact.OtherClient otherClient)
    {
        return (OtherClient) map((ContactOrBot) otherClient);
    }

    public static MessageChain asMessageChain(Message message)
    {
        if (message instanceof MessageChain)
        {
            return (MessageChain) message;
        }
        return MessageChain.builder().append(message).build();
    }

    public static MessageChain map(net.mamoe.mirai.message.data.MessageChain messages)
    {
        MessageChain.Builder builder=MessageChain.builder();
        for (SingleMessage message: messages)
        {
            builder.append(map(message));
        }
        return builder.build();
    }

    private static Message map(net.mamoe.mirai.message.data.Message message)
    {
        if (message instanceof net.mamoe.mirai.message.data.MessageChain)
        {
            return map((net.mamoe.mirai.message.data.MessageChain) message);
        }
        if (message instanceof At)
        {
            return me.lanzhi.bluestarbot.api.message.At.create(((At) message).getTarget());
        }
        if (message instanceof AtAll)
        {
            return me.lanzhi.bluestarbot.api.message.At.AT_ALL;
        }
        if (message instanceof OnlineAudio)
        {
            return AudioImpl.create((OnlineAudio) message);
        }
        if (message instanceof Dice)
        {
            return me.lanzhi.bluestarbot.api.message.Dice.create(((Dice) message).getId());
        }
        if (message instanceof Face)
        {
            return me.lanzhi.bluestarbot.api.message.Face.getFace(((Face) message).getId());
        }
        if (message instanceof FlashImage)
        {
            return FlashImageImpl.create((FlashImage) message);
        }
        if (message instanceof ForwardMessage)
        {
            return new ForwardMessageImpl((ForwardMessage) message);
        }
        if (message instanceof Image)
        {
            return ImageImpl.create((Image) message);
        }
        if (message instanceof MusicShare)
        {
            return new MusicShareImpl((MusicShare) message);
        }
        if (message instanceof PokeMessage)
        {
            return Poke.getPoke((PokeMessage) message);
        }
        if (message instanceof PlainText)
        {
            return Text.create(message.contentToString());
        }
        if (message instanceof VipFace)
        {
            return VIPFace.getFace((VipFace) message);
        }
        return new VoidMessageImpl(message);
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

    public static String toString(GroupMessageEvent event)
    {
        StringBuilder builder=new StringBuilder();
        Consumer<SingleMessage> consumer=singleMessage->
        {
            if (singleMessage instanceof At)
            {
                builder.append("@").append(event.getContact().getMember(((At) singleMessage).getTarget()).getName());
            }
            else
            {
                builder.append(singleMessage.contentToString());
            }
        };
        event.getEvent().getMessage().forEach(consumer);
        return builder.toString();
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
