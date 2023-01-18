package me.lanzhi.bluestarbot;

import me.lanzhi.bluestarbot.api.BluestarBot;
import me.lanzhi.bluestarbot.api.IAutoLogin;
import me.lanzhi.bluestarbot.api.Internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理自动登录的机器人,属于内部类,勿动
 * <p>通过实现{@link Serializable}进行直接储存</p>
 */
@Internal
public final class AutoLogin implements Serializable, IAutoLogin
{
    private static final long serialVersionUID=0L;
    private final Map<Long,String> bots;

    /**
     * 此构造函数理论上仅在插件初次加载时使用,之后除非删除自动登录储存文件,否则不会重新构造
     */
    public AutoLogin()
    {
        bots=new HashMap<>();
    }

    /**
     * 添加一个自动登录机器人
     *
     * @param id       机器人ID
     * @param password 密码
     */
    public void addAutologin(long id,String password)
    {
        try
        {
            bots.put(id,password);
        }
        finally
        {
            save();
        }
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
            save();
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

    public void save()
    {
        try
        {
            BluestarBotPlugin.getInstance().saveAutoLogin();
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 登录所有机器人
     */
    public void loginAll()
    {
        for (Map.Entry<Long,String> entry: bots.entrySet())
        {
            try
            {
                BluestarBot.createBot(entry.getKey(),entry.getValue());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
