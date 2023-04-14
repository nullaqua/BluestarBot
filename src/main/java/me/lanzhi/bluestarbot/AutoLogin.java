package me.lanzhi.bluestarbot;

import me.lanzhi.api.io.IOAccessor;
import me.lanzhi.api.io.KeyInputStream;
import me.lanzhi.api.io.KeyOutputStream;
import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.IAutoLogin;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Utils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理自动登录的机器人,属于内部类,勿动
 * <p>通过实现{@link Serializable}进行直接储存</p>
 */
@Internal
public final class AutoLogin implements IAutoLogin
{
    private final Map<Long,Map.Entry<String,BluestarBot.Protocol>> bots;

    /**
     * 此构造函数理论上仅在插件初次加载时使用,之后除非删除自动登录储存文件,否则不会重新构造
     */
    public AutoLogin()
    {
        bots=new HashMap<>();
    }

    static AutoLogin load() throws IOException
    {
        BluestarBotPlugin plugin=BluestarBotPlugin.getInstance();
        File data=new File(plugin.getDataFolder(),"autologin.db");
        if (!data.exists())
        {
            return new AutoLogin();
        }
        var in=new DataInputStream(new KeyInputStream(Files.newInputStream(data.toPath()),IOAccessor.hexKey()));
        var ret=new AutoLogin();
        long size=in.readLong();
        for (int i=0;i<size;i++)
        {
            long id=in.readLong();
            String password=in.readUTF();
            BluestarBot.Protocol protocol=BluestarBot.Protocol.valueOf(in.readUTF());
            ret.bots.put(id,new HashMap.SimpleEntry<>(password,protocol));
        }
        return ret;
    }

    /**
     * 添加一个自动登录机器人
     *
     * @param id       机器人ID
     * @param password 密码
     */
    public void addAutologin(long id,String password)
    {
        this.addAutologin(id,password,BluestarBot.Protocol.ANDROID_PAD);
    }

    @Override
    public void addAutologin(long id,String password,BluestarBot.Protocol protocol)
    {
        try
        {
            bots.put(id,new HashMap.SimpleEntry<>(password,protocol));
        }
        finally
        {
            Utils.logger().severe(this::save,"自动登录的数据文件保存失败,可能影响下次运行");
        }
    }

    void save() throws IOException
    {
        BluestarBotPlugin plugin=BluestarBotPlugin.getInstance();
        File data=new File(plugin.getDataFolder(),"autologin.db");
        var out=new DataOutputStream(new KeyOutputStream(Files.newOutputStream(data.toPath()),IOAccessor.hexKey()));
        out.writeLong(bots.size());
        for (var entry: bots.entrySet())
        {
            out.writeLong(entry.getKey());
            out.writeUTF(entry.getValue().getKey());
            out.writeUTF(entry.getValue().getValue().name());
        }
        out.close();
    }

    /**
     * 移除一个机器人
     *
     * @param id 机器人ID
     */
    public void removeAutoLogin(long id)
    {
        try
        {
            bots.remove(id);
        }
        finally
        {
            Utils.logger().severe(this::save,"自动登录的数据文件保存失败,可能影响下次运行");
        }
    }

    /**
     * 获取自动登录机器人列表
     *
     * @return 自动登录机器人列表
     */
    public List<Long> getList()
    {
        return new ArrayList<>(bots.keySet());
    }

    /**
     * 登录所有机器人
     */
    public void loginAll()
    {
        for (var entry: bots.entrySet())
        {
            try
            {
                BluestarBot.createBot(entry.getKey(),entry.getValue().getKey(),entry.getValue().getValue());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
