package me.lanzhi.bluestarbot;

import me.lanzhi.api.Bluestar;
import me.lanzhi.api.reflect.FieldAccessor;
import me.lanzhi.api.reflect.URLClassLoaderAccessor;
import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.internal.BluestarBotCommand;
import me.lanzhi.bluestarbot.internal.Manager;
import me.lanzhi.bluestarbot.internal.TestListener;
import me.lanzhi.bluestarbot.internal.Utils;
import me.lanzhi.bluestarbot.internal.classloader.MiraiLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 插件本体类,勿动
 */
@Internal
public final class BluestarBotPlugin extends JavaPlugin
{
    public static BluestarBotPlugin getInstance()
    {
        return JavaPlugin.getPlugin(BluestarBotPlugin.class);
    }

    private final Set<Consumer<BluestarBotEvent>> listeners=new HashSet<>();
    private AutoLogin autoLogin;
    private Bind bind;

    @Override
    public void onLoad()
    {
        Utils.setLogger(this.getLogger());
        try
        {
            Utils.setClassLoaderAccess(URLClassLoaderAccessor.of((URLClassLoader) this.getClassLoader()));
            MiraiLoader.loadMiraiCore();
            System.setProperty("mirai.no-desktop","BluestarBot");
            System.setProperty("mirai.slider.captcha.supported","BluestarBot");
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }

        Utils.logger().warning(getDataFolder()::mkdirs,"数据文件夹不存在,已自动创建");
        try
        {
            FieldAccessor.getDeclaredField(BluestarBot.class,"plugin").set(null,this);
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }

        Utils.logger().severe(this::loadBinds,"账号绑定数据文件可能没有正常加载!");
        Utils.logger().severe(this::loadAutoLogin,"自动登录数据文件可能没有正常加载!");
    }

    @Override
    public void onEnable()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this,()->autoLogin.loginAll());
        //getCommand("bluestarbot").setExecutor(new BluestarBotCommand(this));
        Bluestar.getCommandManager().registerPluginCommand(this,new BluestarBotCommand(this));
        Manager.registerEvents();
        Bukkit.getPluginManager().registerEvents(new TestListener(this),this);
    }

    @Override
    public void onDisable()
    {
        Manager.unregisterEvents();

        Utils.logger().severe(this::saveAutoLogin,"自动登录的数据文件保存失败,可能影响下次运行");
        Utils.logger().severe(this::saveBinds,"账号绑定的数据文件保存失败,可能影响下次运行");
    }

    public void loadAutoLogin() throws IOException
    {
        try
        {
            this.autoLogin=AutoLogin.load();
        }
        catch (IOException e)
        {
            this.autoLogin=new AutoLogin();
            throw e;
        }
    }

    public boolean loadBinds()
    {
        try
        {
            this.bind=Bind.load();
            return false;
        }
        catch (IOException e)
        {
            return true;
        }
    }

    public void saveAutoLogin() throws IOException
    {
        autoLogin.save();
    }

    public void saveBinds() throws IOException
    {
        bind.save();
    }

    public Set<Consumer<BluestarBotEvent>> getListeners()
    {
        return listeners;
    }

    public <T extends BluestarBotEvent> void addListener(Class<T> clazz,Consumer<T> consumer,Plugin plugin)
    {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(plugin);
        listeners.add(new Consumer<>()
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
                    if (clazz.isAssignableFrom(event.getClass()))
                    {
                        consumer.accept(clazz.cast(event));
                    }
                }
                catch (Exception ignored)
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