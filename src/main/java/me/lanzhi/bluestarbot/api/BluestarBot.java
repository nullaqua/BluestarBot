package me.lanzhi.bluestarbot.api;

import kotlin.coroutines.Continuation;
import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.Manager;
import me.lanzhi.bluestarbot.Mapping;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoginSolver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BluestarBot
{
    private static final BluestarBotPlugin plugin=null;
    private BluestarBot()
    {
    }

    public static List<Bot> getBots()
    {
        List<Bot> list=new ArrayList<>();
        for (net.mamoe.mirai.Bot bot: net.mamoe.mirai.Bot.getInstances())
        {
            list.add(Mapping.map(bot));
        }
        return list;
    }

    public static Bot getBot(long id)
    {
        return Mapping.map(net.mamoe.mirai.Bot.getInstanceOrNull(id));
    }

    @NotNull
    public static Bot createBot(long id,String password)
    {
        Bot bot=getBot(id);
        if (bot!=null)
        {
            return bot;
        }
        BotConfiguration configuration=new BotConfiguration();
        configuration.setLoginSolver(new LoginSolver()
        {
            @Nullable
            @Override
            public Object onSolvePicCaptcha(@NotNull net.mamoe.mirai.Bot bot,@NotNull byte[] bytes,@NotNull Continuation<? super String> continuation)
            {
                return Manager.onSolvePicCaptcha(new Bot(bot),bytes,continuation);
            }

            @Nullable
            @Override
            public Object onSolveSliderCaptcha(@NotNull net.mamoe.mirai.Bot bot,@NotNull String s,@NotNull Continuation<? super String> continuation)
            {
                return Manager.onSolveSliderCaptcha(new Bot(bot),s,continuation);
            }

            @Nullable
            @Override
            public Object onSolveUnsafeDeviceLoginVerify(@NotNull net.mamoe.mirai.Bot bot,@NotNull String s,@NotNull Continuation<? super String> continuation)
            {
                return Manager.onSolveUnsafeDeviceLoginVerify(new Bot(bot),s,continuation);
            }
        });
        configuration.setWorkingDir(new File(JavaPlugin.getPlugin(BluestarBotPlugin.class).getDataFolder(),"bots"));
        configuration.noNetworkLog();
        configuration.noBotLog();
        configuration.randomDeviceInfo();
        configuration.setShowingVerboseEventLog(false);
        configuration.setProtocol(BotConfiguration.MiraiProtocol.IPAD);
        Bukkit.getLogger().warning("[BluestarBot]预备工作完成,开始新建bot");
        bot=Mapping.map(Mirai.getInstance().getBotFactory().newBot(id,password,configuration));
        Bukkit.getLogger().warning("[BluestarBot]新建bot完成,开始登录");
        bot.login();
        Bukkit.getLogger().warning("[BluestarBot]登录成功!");
        return bot;
    }

    public void addBind(UUID uuid,long id)
    {
        plugin.getBind().addBind(uuid,id);
    }

    public UUID getBind(long id)
    {
        return plugin.getBind().getBind(id);
    }

    public Long getBind(UUID uuid)
    {
        return plugin.getBind().getBind(uuid);
    }

    public Long removeBind(UUID uuid)
    {
        return plugin.getBind().removeBind(uuid);
    }

    public UUID removeBind(long id)
    {
        return plugin.getBind().removeBind(id);
    }
}
