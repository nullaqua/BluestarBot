package me.lanzhi.bluestarbot.internal;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import me.lanzhi.api.Bluestar;
import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
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
import net.mamoe.mirai.utils.DeviceVerificationRequests;
import net.mamoe.mirai.utils.DeviceVerificationResult;
import net.mamoe.mirai.utils.LoginSolver;
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

/**
 * 事件注册,验证处理
 */
@Internal
public final class Manager
{
    private final static List<Long> bots=new ArrayList<>();
    private final static Map<Long,String> res=new HashMap<>();
    private static final List<Listener<?>> listeners=new ArrayList<>();

    private static final Object lock=new Object();

    public static void setRes(long qq,String result)
    {
        synchronized (lock)
        {
            res.put(qq,result);
            lock.notifyAll();
        }
    }

    public static void cancel(long qq)
    {
        synchronized (lock)
        {
            bots.remove(qq);
            lock.notifyAll();
        }
    }

    public static List<Long> getVerifyingBots()
    {
        return new ArrayList<>(bots);
    }

    public static LoginSolver createLoginSolver()
    {
        return new LoginSolver()
        {
            @Override
            public boolean isSliderCaptchaSupported()
            {
                return true;
            }


            @Override
            public @NotNull Object onSolveDeviceVerification(@NotNull net.mamoe.mirai.Bot bot,
                                                             @NotNull DeviceVerificationRequests requests,
                                                             @NotNull Continuation $completion)
            {
                var x=Manager.onSolveDeviceVerification(new Bot(bot),requests);
                $completion.resumeWith(x);
                return x;
            }

            @Nullable
            @Override
            public Object onSolvePicCaptcha(@NotNull net.mamoe.mirai.Bot bot,byte @NotNull [] bytes,
                                            @NotNull Continuation $completion)
            {
                var x=Manager.onSolvePicCaptcha(new Bot(bot),bytes);
                $completion.resumeWith(x);
                return x;
            }

            @Nullable
            @Override
            public Object onSolveSliderCaptcha(@NotNull net.mamoe.mirai.Bot bot,@NotNull String s,
                                               @NotNull Continuation $completion)
            {
                var x=Manager.onSolveSliderCaptcha(new Bot(bot),s);
                $completion.resumeWith(x);
                return x;
            }
        };
    }

    public static Object onSolveDeviceVerification(Bot bot,DeviceVerificationRequests requests)
    {
        Utils.logger().warning("当前登录的QQ ("+bot.getId()+") 需要设备锁验证");
        Utils.logger().warning("请在以下方式中选择一种验证方式");
        if (requests.getSms()!=null)
        {
            Utils.logger().warning("短信验证");
            Utils.logger().warning("验证码已发送到");
            Utils.logger().warning(requests.getSms().getCountryCode()+" "+requests.getSms().getPhoneNumber());
            Utils.logger().warning("使用此方法验证完成后，请输入指令 /bluestarbot verify "+bot.getId()+" <验证码>");
        }
        if (requests.getFallback()!=null)
        {

            Utils.logger().warning("手机扫码验证");
            Utils.logger().warning("请打开以下链接进行验证");
            Utils.logger().warning(requests.getFallback().getUrl());
            Utils.logger().warning("使用此方法验证完成后，请输入指令 /bluestarbot verify "+bot.getId());
        }
        var result=getRes(bot);
        if (result!=null&&!result.isEmpty())
        {
            Utils.logger().info("QQ机器人: "+bot.getId()+"已使用短信验证完成验证");
            return requests.getSms().solved(result);
        }
        Utils.logger().info("QQ机器人: "+bot.getId()+"已使用扫码验证完成验证");
        return requests.getFallback().solved();
    }

    @Nullable
    public static Object onSolvePicCaptcha(@NotNull Bot bot,byte @NotNull [] bytes)
    {
        File imageFile=new File(JavaPlugin.getPlugin(BluestarBotPlugin.class).getDataFolder(),"verify-images");
        imageFile.mkdirs();
        imageFile=new File(imageFile,bot.getId()+"-verify.png");
        try
        {
            imageFile.createNewFile();
        }
        catch (IOException e)
        {
            Utils.logger().warning("QQ机器人: "+bot.getId()+"在进行登录验证时图片保存失败,原因: "+e);
            throw new CustomLoginFailedException(true,"无法保存验证图片")
            {};
        }

        try (FileOutputStream stream=new FileOutputStream(imageFile))
        {
            stream.write(bytes);
            stream.flush();
        }
        catch (Exception e)
        {
            Utils.logger().warning("QQ机器人: "+bot.getId()+"在进行登录验证时图片保存失败,原因: "+e);
            throw new CustomLoginFailedException(true,"无法保存验证图片")
            {};
        }

        Utils.logger().warning("当前登录的QQ（"+bot.getId()+"）需要文字验证码验证");
        Utils.logger().warning("请找到下面的文件并识别文字验证码");
        Utils.logger().warning(imageFile.getPath());
        Utils.logger().warning("识别完成后，请输入指令 /bluestarbot verify "+bot.getId()+" <验证码>");
        Utils.logger().warning("如需取消登录，请输入指令 /bluestarbot cancel "+bot.getId());

        return getRes(bot);
    }

    @Nullable
    public static Object onSolveSliderCaptcha(@NotNull Bot bot,@NotNull String s)
    {
        Utils.logger().warning("当前登录的QQ（"+bot.getId()+"）需要滑动验证码验证");
        Utils.logger().warning("请打开以下链接进行验证");
        Utils.logger().warning(s);
        Utils.logger().warning("验证完成后，请输入指令 /bluestarbot verify "+bot.getId()+" <ticket>");
        Utils.logger().warning("如需取消登录，请输入指令 /bluestarbot cancel "+bot.getId());

        return getRes(bot);
    }

    private static @Nullable String getRes(@NotNull Bot bot)
    {
        bots.add(bot.getId());
        while (true)
        {
            synchronized (lock)
            {
                if (bots.contains(bot.getId())&&!res.containsKey(bot.getId()))
                {
                    try
                    {
                        lock.wait();
                    }
                    catch (Throwable ignored)
                    {
                    }
                }
                else if (res.containsKey(bot.getId()))
                {
                    bots.remove(bot.getId());
                    return Manager.res.remove(bot.getId());
                }
                else
                {
                    bots.remove(bot.getId());
                    res.remove(bot.getId());
                    throw new CustomLoginFailedException(true,"用户取消登录操作")
                    {};
                }
            }
        }
    }

    public static void registerEvents()
    {
        Thread.currentThread().setContextClassLoader(Utils.classLoaderAccessor().classLoader());
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
        add(BotLeaveEvent.class,BotLeaveGroupEvent::new);
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

    private static <T extends Event,E extends BluestarBotEvent> void add(Class<T> c,Function<T,E> function)
    {
        listeners.add(GlobalEventChannel.INSTANCE.subscribeAlways(c,event->callEvent(function.apply(event))));
    }

    private static void callEvent(BluestarBotEvent event)
    {
        Bluestar.getMainManager().callEvent(event);

        if (event.getEvent() instanceof CancellableEvent&&event.isCancelled())
        {
            ((CancellableEvent) event.getEvent()).cancel();
        }
    }

    public static void unregisterEvents()
    {
        for (Listener<?> listener: listeners)
        {
            listener.complete();
        }
    }
}
