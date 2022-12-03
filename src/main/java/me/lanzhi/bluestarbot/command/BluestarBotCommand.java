package me.lanzhi.bluestarbot.command;

import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.Manager;
import me.lanzhi.bluestarbot.api.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class BluestarBotCommand implements CommandExecutor, TabExecutor
{
    private final BluestarBotPlugin plugin;

    public BluestarBotCommand(BluestarBotPlugin plugin)
    {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args)
    {
        try
        {
            onCommand0(sender,command,label,args);
            return true;
        }
        catch (Exception e)
        {
            sender.sendMessage(ChatColor.RED+"格式错误,详细信息:");
            e.printStackTrace();
            return false;
        }
    }

    private void onCommand0(CommandSender sender,Command command,String label,String[] args)
    {
        if (args.length<1)
        {
            return;
        }
        switch (args[0].toLowerCase())
        {
            case "bind":
            {
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(ChatColor.RED+"此指令仅允许玩家使用");
                    return;
                }
                plugin.getBind().getGui().open((Player) sender);
                return;
            }
            case "addbind":
            {
                OfflinePlayer player=Bukkit.getOfflinePlayer(args[1]);
                long id=Long.parseLong(args[2]);
                sender.sendMessage(ChatColor.AQUA+"添加绑定:"+player.getUniqueId()+" "+id);
                plugin.getBind().addBind(player.getUniqueId(),id);
                sender.sendMessage(ChatColor.GREEN+"绑定成功!");
                return;
            }
            case "login":
            {
                if (args.length<3)
                {
                    sender.sendMessage("/bluestarqq login qqid password");
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(plugin,()->
                {
                    BluestarBot.createBot(Long.parseLong(args[1]),args[2]);
                    sender.sendMessage(ChatColor.GREEN+"操作成功");
                });
                return;
            }
            case "verify":
            {
                if (args.length>2)
                {
                    Manager.res.put(Long.parseLong(args[1]),args[2]);
                }
                else if (args.length>1)
                {
                    Manager.res.put(Long.parseLong(args[1]),"");
                }
                else
                {
                    sender.sendMessage("/bluestarqq verify qqid message");
                }
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
            case "cancel":
            {
                if (args.length<2)
                {
                    sender.sendMessage("/bluestarqq cancel qqid");
                    return;
                }
                Manager.bots.remove((Object) Long.parseLong(args[1]));
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
            case "logout":
            {
                if (args.length<2)
                {
                    sender.sendMessage("/bluestarqq logout qqid");
                    return;
                }
                BluestarBot.getBot(Long.parseLong(args[1])).close();
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
            case "list":
            {
                sender.sendMessage(ChatColor.AQUA+"[BluestarBot] 已登录的机器人:");
                BluestarBot.getBots().forEach(bot->sender.sendMessage(ChatColor.AQUA+bot.getNick()+"-"+bot.getId()));

                sender.sendMessage(ChatColor.GREEN+"操作成功"); return;
            }
            case "autologin":
            {
                switch (args[1])
                {
                    case "add":
                    {
                        plugin.getAutoLogin().addAutologin(Long.parseLong(args[2]),args[3]);
                        sender.sendMessage(ChatColor.GREEN+"操作成功");
                        return;
                    }
                    case "remove":
                    {
                        plugin.getAutoLogin().removeAutoLogin(Long.parseLong(args[2]));
                        sender.sendMessage(ChatColor.GREEN+"操作成功");
                        return;
                    }
                    case "list":
                    {
                        sender.sendMessage(ChatColor.AQUA+"自动登录机器人:");
                        Consumer<Long> consumer=id->
                        {
                            if (BluestarBot.getBot(id)!=null)
                            {
                                sender.sendMessage(id+"-"+ChatColor.GREEN+"已登录");
                            }
                            else
                            {
                                sender.sendMessage(id+"-"+ChatColor.RED+"未登录");
                            }
                        };
                        plugin.getAutoLogin().getList().forEach(consumer);
                        return;
                    }
                    case "loginall":
                    {
                        Bukkit.getScheduler().runTaskAsynchronously(plugin,()->
                        {
                            plugin.getAutoLogin().loginAll();
                            sender.sendMessage(ChatColor.GREEN+"操作成功");
                        });
                        return;
                    }
                    default:
                        return;
                }
            }
            case "sendfriend":
            {
                Friend friend=BluestarBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2]));
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=3;i<args.length;i++)
                {
                    stringBuilder.append(args[i]).append(' ');
                }
                friend.sendMessageCode(stringBuilder.toString());
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
            case "sendgroup":
            {
                Group group=BluestarBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2]));
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=3;i<args.length;i++)
                {
                    stringBuilder.append(args[i]).append(' ');
                }
                group.sendMessageCode(stringBuilder.toString());
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
            case "sendmember":
            {
                NormalGroupMember member=BluestarBot.getBot(Long.parseLong(args[1]))
                                                    .getGroup(Long.parseLong(args[2]))
                                                    .getMember(Long.parseLong(args[3]));
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=4;i<args.length;i++)
                {
                    stringBuilder.append(args[i]).append(' ');
                }
                member.sendMessageCode(stringBuilder.toString());
                sender.sendMessage(ChatColor.GREEN+"操作成功");
                return;
            }
        }
        return;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args)
    {
        switch (args.length)
        {
            case 1:
            {
                return Arrays.asList("login",
                                     "verify",
                                     "cancel",
                                     "logout",
                                     "list",
                                     "autologin",
                                     "sendfriend",
                                     "sendgroup",
                                     "sendmember",
                                     "bind",
                                     "addbind");
            }
            case 2:
            {
                switch (args[0].toLowerCase())
                {
                    case "addbind":
                        return null;
                    case "login":
                    case "verify":
                    case "cancel":
                    {
                        return Collections.singletonList("qqid");
                    }
                    case "logout":
                    case "sendfriend":
                    case "sendgroup":
                    case "sendmember":
                    {
                        ArrayList<String> arrayList=new ArrayList<>();
                        BluestarBot.getBots().forEach(bot->arrayList.add(bot.getId()+""));
                        return arrayList;
                    }
                    case "autologin":
                    {
                        return Arrays.asList("list","add","remove","loginall");
                    }
                    default:
                        return Collections.emptyList();
                }
            }
            case 3:
            {
                if ("autologin".equalsIgnoreCase(args[0]))
                {
                    if ("add".equalsIgnoreCase(args[1])||"remove".equalsIgnoreCase(args[1]))
                    {
                        return Collections.singletonList("qqid");
                    }
                    return Collections.emptyList();
                }
                if ("addbind".equalsIgnoreCase(args[0]))
                {
                    return Collections.singletonList("qqid");
                }
                List<String> a=new ArrayList<>();
                Bot bot;
                try
                {
                    bot=BluestarBot.getBot(Long.parseLong(args[1]));
                    if (bot==null)
                    {
                        return Collections.emptyList();
                    }
                }
                catch (Exception e)
                {
                    return Collections.emptyList();
                }
                switch (args[0].toLowerCase())
                {
                    case "sendfriend":
                    {
                        bot.getFriends().forEach(friend->a.add(friend.getId()+""));
                        return a;
                    }
                    case "sendgroup":
                    case "sendmember":
                    {
                        ArrayList<String> arrayList=new ArrayList<>();
                        bot.getGroups().forEach(group->arrayList.add(group.getId()+""));
                        return arrayList;
                    }
                    default:
                        return Collections.emptyList();
                }
            }
            case 4:
            {
                if ("autologin".equalsIgnoreCase(args[0])&&"add".equalsIgnoreCase(args[1]))
                {
                    return Collections.singletonList("password");
                }
                if (!"sendmember".equalsIgnoreCase(args[0]))
                {
                    return Collections.emptyList();
                }
                Group group;
                try
                {
                    Bot bot=BluestarBot.getBot(Long.parseLong(args[1]));
                    group=bot.getGroup(Long.parseLong(args[2]));
                    if (group==null)
                    {
                        return Collections.emptyList();
                    }
                }
                catch (Exception e)
                {
                    return Collections.emptyList();
                }
                ArrayList<String> s=new ArrayList<>();
                group.getMembers().forEach(normalGroupMember->s.add(normalGroupMember.getId()+""));
                return s;
            }
        }
        return Collections.emptyList();
    }
}
