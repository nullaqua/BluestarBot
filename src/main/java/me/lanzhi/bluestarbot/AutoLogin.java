package me.lanzhi.bluestarbot;

import me.lanzhi.api.config.YamlFile;
import me.lanzhi.bluestarbot.api.BluestarBot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AutoLogin
{
    private final BluestarBotPlugin plugin;
    private final YamlFile config;

    public AutoLogin(BluestarBotPlugin plugin)
    {
        this.plugin=plugin;
        config=YamlFile.loadYamlFile(new File(plugin.getDataFolder(),"autologin_data.yml"));
    }

    public void addAutologin(long id,String password)
    {
        config.set(""+id,password);
        config.save();
    }
    public void removeAutoLogin(long id)
    {
        addAutologin(id,null);
    }
    public List<Long> getList()
    {
        ArrayList<Long>id=new ArrayList<>();
        config.getKeys(false).forEach(s -> id.add(Long.parseLong(s)));
        return id;
    }
    public void loginAll()
    {
        for (Map.Entry<String,Object> entry:config.getValues(false).entrySet())
        {
            try
            {
                long id=Long.parseLong(entry.getKey());
                String password=(String) entry.getValue();
                BluestarBot.createBot(id,password);
            }
            catch (Exception e){}
        }
    }
}
