package me.lanzhi.bluestarbot;

import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.classloader.MiraiLoader;
import me.lanzhi.bluestarbot.command.BluestarBotCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class BluestarBotPlugin extends JavaPlugin
{
    private final Set<Consumer<BluestarBotEvent>> listeners=new HashSet<>();
    private AutoLogin autoLogin;
    @Override
    public void onLoad()
    {
        getDataFolder().mkdirs();
        try
        {
            MiraiLoader.loadMiraiCore();
            System.setProperty("mirai.no-desktop","BluestarBot");
            System.setProperty("mirai.slider.captcha.supported","BluestarBot");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable()
    {
        autoLogin=new AutoLogin(this);
        autoLogin.loginAll();
        getCommand("bluestarbot").setExecutor(new BluestarBotCommand(this));
        Manager.registerEvents();
        Bukkit.getPluginManager().registerEvents(new TestListener(this),this);
    }

    @Override
    public void onDisable()
    {
        Manager.unregisterEvents();
    }

    public Set<Consumer<BluestarBotEvent>> getListeners()
    {
        return listeners;
    }

    public<T extends BluestarBotEvent> void addListener(Class<T> clazz,Consumer<T> consumer,Plugin plugin)
    {
        if (consumer==null||plugin==null)
        {
            return;
        }
        listeners.add(new Consumer<BluestarBotEvent>()
        {
            @Override
            public void accept(BluestarBotEvent event)
            {
                try
                {
                    if (!plugin.isEnabled())
                    {
                        listeners.remove(this);
                    }
                    if (clazz==null||clazz.isAssignableFrom(event.getClass()))
                    {
                        consumer.accept((T) event);
                    }
                }
                catch (Exception e){}
            }
        });
    }

    public AutoLogin getAutoLogin()
    {
        return autoLogin;
    }
}