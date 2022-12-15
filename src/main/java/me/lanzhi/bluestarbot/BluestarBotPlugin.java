package me.lanzhi.bluestarbot;

import me.lanzhi.api.reflect.FieldAccessor;
import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.internal.Manager;
import me.lanzhi.bluestarbot.internal.TestListener;
import me.lanzhi.bluestarbot.internal.Utils;
import me.lanzhi.bluestarbot.internal.classloader.MiraiLoader;
import me.lanzhi.bluestarbot.internal.classloader.URLClassLoaderAccess;
import me.lanzhi.bluestarbot.internal.command.BluestarBotCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class BluestarBotPlugin extends JavaPlugin
{
    private final Set<Consumer<BluestarBotEvent>> listeners=new HashSet<>();
    private AutoLogin autoLogin;
    private Bind bind;

    @Override
    public void onLoad()
    {
        Utils.setLogger(this.getLogger());
        try
        {
            MiraiLoader.setClassLoaderAccess(URLClassLoaderAccess.create((URLClassLoader) this.getClassLoader()));
            MiraiLoader.loadMiraiCore();
            System.setProperty("mirai.no-desktop","BluestarBot");
            System.setProperty("mirai.slider.captcha.supported","BluestarBot");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        getDataFolder().mkdirs();
        try
        {
            FieldAccessor.getDeclaredField(BluestarBot.class,"plugin").set(null,this);
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }

        File data=new File(getDataFolder(),"bind.db");
        try
        {
            bind=(Bind) new ObjectInputStream(Files.newInputStream(data.toPath())).readObject();
        }
        catch (Exception e)
        {
            bind=new Bind();
        }

        data=new File(getDataFolder(),"autologin.db");
        try
        {
            autoLogin=(AutoLogin) new ObjectInputStream(Files.newInputStream(data.toPath())).readObject();
        }
        catch (Exception e)
        {
            autoLogin=new AutoLogin();
        }
    }

    @Override
    public void onEnable()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this,()->autoLogin.loginAll());
        getCommand("bluestarbot").setExecutor(new BluestarBotCommand(this));
        Manager.registerEvents();
        Bukkit.getPluginManager().registerEvents(new TestListener(this),this);
    }

    @Override
    public void onDisable()
    {
        Manager.unregisterEvents();

        try
        {
            File data=new File(getDataFolder(),"bind.db");
            data.delete();
            data.createNewFile();
            new ObjectOutputStream(Files.newOutputStream(data.toPath())).writeObject(bind);
            data=new File(getDataFolder(),"autologin.db");
            data.delete();
            data.createNewFile();
            new ObjectOutputStream(Files.newOutputStream(data.toPath())).writeObject(autoLogin);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
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
                catch (Exception e)
                {
                }
            }
        });
    }

    public AutoLogin getAutoLogin()
    {
        return autoLogin;
    }

    public Bind getBind()
    {
        return bind;
    }
}