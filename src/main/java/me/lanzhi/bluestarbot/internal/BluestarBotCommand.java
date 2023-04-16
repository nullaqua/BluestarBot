package me.lanzhi.bluestarbot.internal;

import me.lanzhi.api.command.*;
import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;
/**
 * 插件指令处理类
 */
@Internal
@CommandName("bluestarbot")
@CommandAlias({"bsbot"})
public final class BluestarBotCommand
{
    @ParseTab("verify <qq> <?>")
    public final String verify="<code>";
    @ParseTab({"autologin add <?>","login <?>"})
    public final String addAutoLogin0="<QQId>";
    @ParseTab({"autologin add <qq> <?>","login <qq> <?>"})
    public final String addAutoLogin1="<password>";
    @ParseTab({"autologin add <qq> <pw> <?>","login <qq> <pw> <?>"})
    public final Object addAutoLogin2=BluestarBot.Protocol.values();
    @ParseTab({"sendfriend <bot> <friend> <?>","sendgroup <bot> <group> <?>","sendmember <bot> <group> <member> <?>"})
    public final String sendFriend="<message>";
    @ParseCommand("...")
    public final String unknownCommand=ChatColor.RED+"未知指令";
    private final BluestarBotPlugin plugin;
    @ParseTab("addbind <player> <?>")
    public String addBind="<QQId>";

    public BluestarBotCommand(BluestarBotPlugin plugin)
    {
        this.plugin=plugin;
    }

    @ParseCommand("bind ...")
    public void bind(CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED+"此指令仅允许玩家使用");
            return;
        }
        plugin.getBind().getGui().open((Player) sender);
    }

    @ParseCommand("addbind <p> <qq>")
    public String addBind(@Get("p") OfflinePlayer player,@Get("qq") Long qq)
    {
        if (qq==null)
        {
            return ChatColor.RED+"QQ号码不合法";
        }
        if (player==null)
        {
            return ChatColor.RED+"玩家不存在";
        }
        plugin.getBind().addBind(player.getUniqueId(),qq);
        return ChatColor.GREEN+"成功为玩家"+player.getName()+"绑定QQ号"+qq;
    }

    @ParseCommand("login <qq> <pw> [prot]")
    public String login(@Get("qq") Long qq,@Get("pw") String pw,@Get("prot") BluestarBot.Protocol prot)
    {
        if (qq==null)
        {
            return ChatColor.RED+"QQ号码不合法";
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin,()->BluestarBot.createBot(qq,pw,prot));
        return null;
    }

    @ParseCommand("logout <qq>")
    public String logout(@Get("qq") Long qq)
    {
        if (qq==null)
            return ChatColor.RED+"QQ号码不合法";
        var bot=BluestarBot.getBot(qq);
        if (bot==null)
            return ChatColor.RED+"机器人不存在";
        bot.close();
        return ChatColor.GREEN+"已登出";
    }

    @ParseCommand("verify <qq> <code>")
    public String verify(@Get("qq") Long qq,@Get("code") String code)
    {
        if (qq==null)
            return ChatColor.RED+"QQ号码不合法";
        if (code==null)
            return ChatColor.RED+"验证码不合法";
        Manager.setRes(qq,code);
        return null;
    }

    @ParseCommand("cancel <qq>")
    public String cancel(@Get("qq") Long qq)
    {
        if (qq==null)
            return ChatColor.RED+"QQ号码不合法";
        Manager.cancel(qq);
        return ChatColor.GREEN+"已取消登录";
    }

    @ParseCommand("list")
    public Object list()
    {
        var list=new ArrayList<String>();
        list.add(ChatColor.AQUA+"[BluestarBot] 已登录的机器人:");
        BluestarBot.getBots().forEach(bot->list.add(ChatColor.AQUA+bot.getNick()+"-"+bot.getId()));
        return list;
    }

    @ParseCommand("autologin add <qq> <pw> [prot]")
    public String addAutoLogin(@Get("qq") Long qq,@Get("pw") String pw,@Get("prot") BluestarBot.Protocol prot)
    {
        if (qq==null)
            return ChatColor.RED+"QQ号码不合法";
        if (pw==null)
            return ChatColor.RED+"密码不合法";
        plugin.getAutoLogin().addAutologin(qq,pw,prot);
        return ChatColor.GREEN+"已添加自动登录";
    }

    @ParseCommand("autologin remove <qq>")
    public String removeAutoLogin(@Get("qq") Long qq)
    {
        if (qq==null)
            return ChatColor.RED+"QQ号码不合法";
        plugin.getAutoLogin().removeAutoLogin(qq);
        return ChatColor.GREEN+"已移除自动登录";
    }

    @ParseCommand("autologin list")
    public Object listAutoLogin()
    {
        var list=new ArrayList<String>();
        list.add(ChatColor.AQUA+"[BluestarBot] 已添加的自动登录:");
        Consumer<Long> consumer=id->
        {
            if (BluestarBot.getBot(id)!=null)
                list.add(id+"-"+ChatColor.GREEN+"已登录");
            else
                list.add(id+"-"+ChatColor.RED+"未登录");
        };
        plugin.getAutoLogin().getList().forEach(consumer);
        return list;
    }

    @ParseCommand("autologin loginall")
    public String loginAll()
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,()->plugin.getAutoLogin().loginAll());
        return ChatColor.GREEN+"已开始登录";
    }

    @ParseCommand("sendfriend <bot> <firend> ...")
    public String sendFriend(@Get("bot") Long botqq,@Get("firend") Long friendqq,@Get("...") String msg)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return ChatColor.RED+"机器人不存在";
        Friend friend=bot.getFriend(friendqq);
        if (friend==null)
            return ChatColor.RED+"好友不存在";
        if (msg==null||msg.isEmpty())
            return ChatColor.RED+"消息不能为空";
        friend.sendMessage(msg);
        return ChatColor.GREEN+"已发送";
    }

    @ParseCommand("sendgroup <bot> <group> ...")
    public String sendGroup(@Get("bot") Long botqq,@Get("group") Long groupqq,@Get("...") String msg)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return ChatColor.RED+"机器人不存在";
        var group=bot.getGroup(groupqq);
        if (group==null)
            return ChatColor.RED+"群不存在";
        if (msg==null||msg.isEmpty())
            return ChatColor.RED+"消息不能为空";
        group.sendMessage(msg);
        return ChatColor.GREEN+"已发送";
    }

    @ParseCommand("sendmember <bot> <group> <member> ...")
    public String sendMember(@Get("bot") Long botqq,@Get("group") Long groupqq,@Get("member") Long memberqq,@Get("...") String msg)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return ChatColor.RED+"机器人不存在";
        var group=bot.getGroup(groupqq);
        if (group==null)
            return ChatColor.RED+"群不存在";
        var member=group.getMember(groupqq);
        if (member==null)
            return ChatColor.RED+"成员不存在";
        if (msg==null||msg.isEmpty())
            return ChatColor.RED+"消息不能为空";
        member.sendMessage(msg);
        return ChatColor.GREEN+"已发送";
    }

    @ParseTab("<?>")
    public Object tab()
    {
        return new String[]{"login",
                            "verify",
                            "cancel",
                            "logout",
                            "list",
                            "autologin",
                            "sendfriend",
                            "sendgroup",
                            "sendmember",
                            "bind",
                            "addbind"};
    }

    @ParseTab("addbind <?>")
    public Object tabAddBind()
    {
        return Bukkit.getOnlinePlayers();
    }

    @ParseTab("verify <?>")
    public Object tabVerify()
    {
        return Manager.getVerifyingBots();
    }

    @ParseTab("cancel <?>")
    public Object tabCancel()
    {
        return Manager.getVerifyingBots();
    }

    @ParseTab("logout <?>")
    public Object tabLogout()
    {
        return BluestarBot.getBots();
    }

    @ParseTab("autologin <?>")
    public Object tabAutoLogin()
    {
        return Arrays.asList("add","remove","list","loginall");
    }

    @ParseTab("autologin add <qq> <pw> <?>")
    public Object addAutoLogin2()
    {
        return BluestarBot.Protocol.values();
    }

    @ParseTab("autologin remove <?>")
    public Object tabAutoLoginRemove()
    {
        return plugin.getAutoLogin().getList();
    }

    @ParseTab({"sendfriend <?>","sendgroup <?>","sendmember <?>"})
    public Object tabSendFriend()
    {
        return BluestarBot.getBots().stream().map(Bot::getId).collect(Collectors.toList());
    }

    @ParseTab("sendfriend <bot> <?>")
    public Object tabSendFriend0(@Get("bot") Long botqq)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return null;
        return bot.getFriends().stream().map(Friend::getId).collect(Collectors.toList());
    }

    @ParseTab({"sendgroup <bot> <?>","sendmember <bot> <?>"})
    public Object tabSendGroup0(@Get("bot") Long botqq)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return null;
        return bot.getGroups().stream().map(Group::getId).collect(Collectors.toList());
    }

    @ParseTab("sendmember <bot> <group> <?>")
    public Object tabSendMember0(@Get("bot") Long botqq,@Get("group") Long groupqq)
    {
        Bot bot=BluestarBot.getBot(botqq);
        if (bot==null)
            return null;
        var group=bot.getGroup(groupqq);
        if (group==null)
            return null;
        return group.getMembers();
    }
}