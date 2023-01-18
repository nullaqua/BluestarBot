package me.lanzhi.bluestarbot;

import me.lanzhi.api.reflect.FieldAccessor;
import me.lanzhi.api.reflect.URLClassLoaderAccessor;
import me.lanzhi.api.util.io.IOAccessor;
import me.lanzhi.api.util.io.IOStreamKey;
import me.lanzhi.api.util.io.KeyObjectInputStream;
import me.lanzhi.api.util.io.file.FileWithVersionReader;
import me.lanzhi.api.util.io.file.FileWithVersionWriter;
import me.lanzhi.api.util.io.file.ReadVersion;
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

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashSet;
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
        getCommand("bluestarbot").setExecutor(new BluestarBotCommand(this));
        Manager.registerEvents();
        Bukkit.getPluginManager().registerEvents(new TestListener(this),this);
    }

    @Override
    public void onDisable()
    {
        Manager.unregisterEvents();

        Utils.logger().severe(this::saveAutoLogin,"自动登录的数据文件报错失败,可能影响下次运行");
        Utils.logger().severe(this::saveBinds,"账号绑定的数据文件报错失败,可能影响下次运行");
    }

    private void loadAutoLogin()
    {
        File data=new File(getDataFolder(),"autologin.db");
        if (!data.exists()||data.isDirectory())
        {
            Utils.logger().warning("自动登录数据文件不存在,已自动创建");
            autoLogin=new AutoLogin();
            return;
        }
        var reader=new FileWithVersionReader(data);
        reader.read(new FileWithVersionReader.Worker()
        {
            @ReadVersion("1.0.0")
            public void read1(KeyObjectInputStream in) throws Throwable
            {
                Utils.logger().warning("自动登录数据文件版本为1.0.0,正在读取");
                autoLogin=(AutoLogin) in.readObject();
            }

            @Override
            public void defaultRead(String version,KeyObjectInputStream stream)
            {
                try
                {
                    Utils.logger().warning("自动登录数据文件版本为"+version+",可能不兼容,正在尝试读取");
                    KeyObjectInputStream kois=KeyObjectInputStream.create(Files.newInputStream(data.toPath()));
                    IOStreamKey key=(IOStreamKey) kois.readObject();
                    autoLogin=(AutoLogin) kois.readObject(key);
                }
                catch (Throwable e)
                {
                    Utils.logger().warning("自动登录数据文件版本为"+version+",可能不兼容,读取失败");
                    autoLogin=new AutoLogin();
                    throw new RuntimeException(e);
                }
            }
        },null,IOAccessor.hexKey());
    }

    private void loadBinds()
    {
        File data=new File(getDataFolder(),"bind.db");
        if (!data.exists()||data.isDirectory())
        {
            Utils.logger().warning("账号绑定数据文件不存在,已自动创建");
            bind=new Bind();
            return;
        }
        var reader=new FileWithVersionReader(data);
        reader.read(new FileWithVersionReader.Worker()
        {
            @ReadVersion("1.0.0")
            public void read1(KeyObjectInputStream in) throws Throwable
            {
                Utils.logger().warning("正在读取账号绑定数据文件: 1.0.0");
                bind=(Bind) in.readObject();
            }

            @Override
            public void defaultRead(String version,KeyObjectInputStream stream)
            {
                try
                {
                    Utils.logger().warning("正在读取账号绑定数据文件: "+version);
                    KeyObjectInputStream kois=KeyObjectInputStream.create(Files.newInputStream(data.toPath()));
                    IOStreamKey key=(IOStreamKey) kois.readObject();
                    bind=(Bind) kois.readObject(key);
                }
                catch (Throwable e)
                {
                    Utils.logger().warning("账号绑定数据文件读取失败,已自动创建");
                    bind=new Bind();
                    throw new RuntimeException(e);
                }
            }
        },null,IOAccessor.hexKey());
    }

    public void saveAutoLogin() throws IOException
    {
        File data=new File(getDataFolder(),"autologin.db");
        var writer=new FileWithVersionWriter(data);
        writer.saveFile(null,IOAccessor.hexKey(),"1.0.0",out->out.writeObject(autoLogin));
    }

    public void saveBinds() throws IOException
    {
        File data=new File(getDataFolder(),"bind.db");
        var writer=new FileWithVersionWriter(data);
        writer.saveFile(null,IOAccessor.hexKey(),"1.0.0",out->out.writeObject(bind));
    }

    public Set<Consumer<BluestarBotEvent>> getListeners()
    {
        return listeners;
    }

    public <T extends BluestarBotEvent> void addListener(Class<T> clazz,Consumer<T> consumer,Plugin plugin)
    {
        if (consumer==null||plugin==null)
        {
            return;
        }
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