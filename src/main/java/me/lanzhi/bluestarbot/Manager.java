package me.lanzhi.bluestarbot;

import kotlin.coroutines.Continuation;
import me.lanzhi.api.Bluestar;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.api.event.bot.*;
import me.lanzhi.bluestarbot.api.event.friend.*;
import me.lanzhi.bluestarbot.api.event.group.*;
import me.lanzhi.bluestarbot.api.event.group.member.*;
import me.lanzhi.bluestarbot.api.event.group.member.list.BotInvitedJoinGroupRequestEvent;
import me.lanzhi.bluestarbot.api.event.group.member.list.MemberJoinEvent;
import me.lanzhi.bluestarbot.api.event.group.member.list.MemberJoinRequestEvent;
import me.lanzhi.bluestarbot.api.event.group.member.list.MemberLeaveEvent;
import me.lanzhi.bluestarbot.api.event.group.setting.GroupAllowAnonymousChatEvent;
import me.lanzhi.bluestarbot.api.event.group.setting.GroupAllowMemberInviteEvent;
import me.lanzhi.bluestarbot.api.event.group.setting.GroupMuteAllEvent;
import me.lanzhi.bluestarbot.api.event.group.setting.GroupNameChangeEvent;
import me.lanzhi.bluestarbot.api.event.message.BeforeImageUploadEvent;
import me.lanzhi.bluestarbot.api.event.message.ImageUploadEvent;
import me.lanzhi.bluestarbot.api.event.message.NudgeEvent;
import me.lanzhi.bluestarbot.api.event.message.postsend.FriendMessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.message.postsend.GroupMessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.message.postsend.GroupTempMessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.message.postsend.StrangerMessagePostSendEvent;
import me.lanzhi.bluestarbot.api.event.message.presend.FriendMessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.message.presend.GroupMessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.message.presend.GroupTempMessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.message.presend.StrangerMessagePreSendEvent;
import me.lanzhi.bluestarbot.api.event.message.recall.FriendRecallEvent;
import me.lanzhi.bluestarbot.api.event.message.recall.GroupRecallEvent;
import me.lanzhi.bluestarbot.api.event.message.received.*;
import net.mamoe.mirai.event.CancellableEvent;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.network.CustomLoginFailedException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Manager
{
    public final static List<Long> bots=new ArrayList<>();
    public final static Map<Long,String> res=new HashMap<>();
    private static final List<Listener<?>> listeners=new ArrayList<>();

    @Nullable
    public static Object onSolvePicCaptcha(@NotNull Bot bot,@NotNull byte[] bytes,@NotNull Continuation<? super String> continuation)
    {
        File imageFile=new File(JavaPlugin.getPlugin(BluestarBotPlugin.class).getDataFolder(),"verify-images");
        imageFile.mkdirs();
        imageFile=new File(imageFile,bot.getId()+"-verify");
        try
        {
            imageFile.createNewFile();
        }
        catch (IOException e)
        {
            Bukkit.getLogger().warning("QQ机器人: "+bot.getId()+"在进行登录验证时图片保存失败,原因: "+e);
            throw new CustomLoginFailedException(true,"无法保存验证图片")
            {
            };
        }

        try (FileOutputStream stream=new FileOutputStream(imageFile))
        {
            stream.write(bytes);
            stream.flush();
        }
        catch (Exception e)
        {
            Bukkit.getLogger().warning("QQ机器人: "+bot.getId()+"在进行登录验证时图片保存失败,原因: "+e);
            throw new CustomLoginFailedException(true,"无法保存验证图片")
            {
            };
        }

        Bukkit.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要文字验证码验证");
        Bukkit.getLogger().warning("请找到下面的文件并识别文字验证码");
        Bukkit.getLogger().warning(imageFile.getPath());
        Bukkit.getLogger().warning("识别完成后，请输入指令 /bluestarbot verify "+bot.getId()+" <验证码>");
        Bukkit.getLogger().warning("如需取消登录，请输入指令 /bluestarbot cancel "+bot.getId());

        return getRes(bot);
    }

    @Nullable
    public static Object onSolveSliderCaptcha(@NotNull Bot bot,@NotNull String s,@NotNull Continuation<? super String> continuation)
    {
        Bukkit.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要滑动验证码验证");
        Bukkit.getLogger().warning("请打开以下链接进行验证");
        Bukkit.getLogger().warning(s);
        Bukkit.getLogger().warning("验证完成后，请输入指令 /bluestarbot verify "+bot.getId()+" <ticket>");
        Bukkit.getLogger().warning("如需取消登录，请输入指令 /bluestarbot cancel "+bot.getId());

        return getRes(bot);
    }

    @Nullable
    public static Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot,@NotNull String s,@NotNull Continuation<? super String> continuation)
    {
        Bukkit.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要设备锁验证");
        Bukkit.getLogger().warning("请打开以下链接进行验证");
        Bukkit.getLogger().warning(s);
        Bukkit.getLogger().warning("验证完成后，请输入指令 /bluestarbot verify "+bot.getId());
        Bukkit.getLogger().warning("如需取消登录，请输入指令 /bluestarbot cancel "+bot.getId());

        getRes(bot);
        return null;
    }

    private static @Nullable Object getRes(@NotNull Bot bot)
    {
        bots.add(bot.getId());
        try
        {
            Thread threads=new Thread()
            {
                @Override
                public void run()
                {
                    while (bots.contains(bot.getId())&&!res.containsKey(bot.getId()))
                    {
                    }
                }
            };
            threads.start();
            threads.join();
        }
        catch (Exception e)
        {
            Bukkit.getLogger().warning("QQ机器人: "+bot.getId()+"创建登录线程时失败,原因: "+e);
            throw new CustomLoginFailedException(true,"无法创建登录线程")
            {
            };
        }

        if (res.containsKey(bot.getId()))
        {
            bots.remove(bot.getId());
            return res.remove(bot.getId());
        }
        else
        {
            bots.remove(bot.getId());
            res.remove(bot.getId());
            throw new CustomLoginFailedException(true,"用户取消登录操作")
            {
            };
        }
    }

    public static void registerEvents()
    {
        //bot
        add(net.mamoe.mirai.event.events.BotAvatarChangedEvent.class,BotAvatarChangedEvent::new);
        add(net.mamoe.mirai.event.events.BotNickChangedEvent.class,BotNickChangedEvent::new);
        add(net.mamoe.mirai.event.events.BotOfflineEvent.class,BotOfflineEvent::new);
        add(net.mamoe.mirai.event.events.BotOnlineEvent.class,BotOnlineEvent::new);
        add(net.mamoe.mirai.event.events.BotReloginEvent.class,BotReLoginEvent::new);

        //friend
        add(net.mamoe.mirai.event.events.FriendAddEvent.class,FriendAddEvent::new);
        add(net.mamoe.mirai.event.events.FriendAvatarChangedEvent.class,FriendAvatarChangedEvent::new);
        add(net.mamoe.mirai.event.events.FriendDeleteEvent.class,FriendDeleteEvent::new);
        add(net.mamoe.mirai.event.events.FriendInputStatusChangedEvent.class,FriendInputStatusChangedEvent::new);
        add(net.mamoe.mirai.event.events.FriendNickChangedEvent.class,FriendNickChangedEvent::new);
        add(net.mamoe.mirai.event.events.FriendRemarkChangeEvent.class,FriendRemarkChangeEvent::new);
        add(net.mamoe.mirai.event.events.NewFriendRequestEvent.class,NewFriendRequestEvent::new);

        //group
        add(BotLeaveEvent.class,BotGroupLeaveEvent::new);
        add(net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent.class,BotGroupPermissionChangeEvent::new);
        add(net.mamoe.mirai.event.events.BotJoinGroupEvent.class,BotJoinGroupEvent::new);
        add(net.mamoe.mirai.event.events.BotMuteEvent.class,BotMuteEvent::new);
        add(net.mamoe.mirai.event.events.BotUnmuteEvent.class,BotUnmuteEvent::new);

        //group.member
        add(net.mamoe.mirai.event.events.MemberMuteEvent.class,MemberMuteEvent::new);
        add(net.mamoe.mirai.event.events.MemberCardChangeEvent.class,MemberNameCardChangeEvent::new);
        add(net.mamoe.mirai.event.events.MemberPermissionChangeEvent.class,MemberPermissionChangeEvent::new);
        add(net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent.class,MemberTitleChangeEvent::new);
        add(net.mamoe.mirai.event.events.MemberUnmuteEvent.class,MemberUnmuteEvent::new);

        //group.member.list
        add(net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent.class,BotInvitedJoinGroupRequestEvent::new);
        add(net.mamoe.mirai.event.events.MemberJoinEvent.class,MemberJoinEvent::new);
        add(net.mamoe.mirai.event.events.MemberJoinRequestEvent.class,MemberJoinRequestEvent::new);
        add(net.mamoe.mirai.event.events.MemberLeaveEvent.class,MemberLeaveEvent::new);

        //group.setting
        add(net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent.class,GroupAllowAnonymousChatEvent::new);
        add(net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent.class,GroupAllowMemberInviteEvent::new);
        add(net.mamoe.mirai.event.events.GroupMuteAllEvent.class,GroupMuteAllEvent::new);
        add(net.mamoe.mirai.event.events.GroupNameChangeEvent.class,GroupNameChangeEvent::new);

        //message
        add(net.mamoe.mirai.event.events.BeforeImageUploadEvent.class,BeforeImageUploadEvent::new);
        add(net.mamoe.mirai.event.events.ImageUploadEvent.class,ImageUploadEvent::new);
        add(net.mamoe.mirai.event.events.NudgeEvent.class,NudgeEvent::new);

        //message.postSend
        add(net.mamoe.mirai.event.events.FriendMessagePostSendEvent.class,FriendMessagePostSendEvent::new);
        add(net.mamoe.mirai.event.events.GroupMessagePostSendEvent.class,GroupMessagePostSendEvent::new);
        add(net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent.class,GroupTempMessagePostSendEvent::new);
        add(net.mamoe.mirai.event.events.StrangerMessagePostSendEvent.class,StrangerMessagePostSendEvent::new);

        //message.preSend
        add(net.mamoe.mirai.event.events.FriendMessagePreSendEvent.class,FriendMessagePreSendEvent::new);
        add(net.mamoe.mirai.event.events.GroupMessagePreSendEvent.class,GroupMessagePreSendEvent::new);
        add(net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent.class,GroupTempMessagePreSendEvent::new);
        add(net.mamoe.mirai.event.events.StrangerMessagePreSendEvent.class,StrangerMessagePreSendEvent::new);

        //message.recall
        add(MessageRecallEvent.FriendRecall.class,FriendRecallEvent::new);
        add(MessageRecallEvent.GroupRecall.class,GroupRecallEvent::new);

        //message.received
        add(net.mamoe.mirai.event.events.FriendMessageEvent.class,FriendMessageEvent::new);
        add(net.mamoe.mirai.event.events.GroupMessageEvent.class,GroupMessageEvent::new);
        add(net.mamoe.mirai.event.events.GroupTempMessageEvent.class,GroupTempMessageEvent::new);
        add(net.mamoe.mirai.event.events.OtherClientMessageEvent.class,OtherClientMessageEvent::new);
        add(net.mamoe.mirai.event.events.StrangerMessageEvent.class,StrangerMessageEvent::new);

    }

    public static void unregisterEvents()
    {
        for (Listener<?> listener:listeners)
        {
            listener.complete();
        }
    }

    private static void callEvent(BluestarBotEvent event)
    {
        Bluestar.getMainManager().callEvent(event);

        if (event.getEvent() instanceof CancellableEvent&&event.isCancelled())
        {
            ((CancellableEvent) event.getEvent()).cancel();
        }
    }

    private static <T extends Event,E extends BluestarBotEvent> void add(Class<T> c,Function<T,E> function)
    {
        listeners.add(GlobalEventChannel.INSTANCE.subscribeAlways(c,event->callEvent(function.apply(event))));
    }
}
