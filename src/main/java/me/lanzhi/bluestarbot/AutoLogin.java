package me.lanzhi.bluestarbot;

import me.lanzhi.bluestarbot.api.BluestarBot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理自动登录的机器人,属于内部类,勿动
 */
public final class AutoLogin implements Serializable
{
    private static final long serialVersionUID=0L;
    private final Map<Long,String> bots;

    public AutoLogin()
    {
        bots=new HashMap<>();
    }

    public void addAutologin(long id,String password)
    {
        bots.put(id,password);
    }

    public void removeAutoLogin(long id)
    {
        bots.remove(id);
    }
    public List<Long> getList()
    {
        return new ArrayList<>(bots.keySet());
    }
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
