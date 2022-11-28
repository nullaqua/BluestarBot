package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.Mapping;

import java.util.List;
import java.util.stream.Collectors;

public final class Bot extends User
{
    private final Friend asFriend;

    public Bot(net.mamoe.mirai.Bot bot)
    {
        super(bot);
        this.asFriend=Mapping.map(getMiraiBot().getAsFriend());
    }

    public boolean isOnline()
    {
        return getMiraiBot().isOnline();
    }

    public net.mamoe.mirai.Bot getMiraiBot()
    {
        return (net.mamoe.mirai.Bot) super.getUser();
    }

    public Group getGroup(long id)
    {
        return Mapping.map(getMiraiBot().getGroup(id));
    }

    public Friend getFriend(long id)
    {
        return Mapping.map(getMiraiBot().getFriend(id));
    }

    public Stranger getStranger(long id)
    {
        return Mapping.map(getMiraiBot().getStranger(id));
    }

    public OtherClient getOtherClient(long otherClient)
    {
        return Mapping.map(this.getMiraiBot().getOtherClients().get(otherClient));
    }

    public Friend asFriend()
    {
        return asFriend;
    }

    public Stranger asStranger()
    {
        return Mapping.map(getMiraiBot().getAsStranger());
    }

    public List<Friend> getFriends()
    {
        return this.getMiraiBot().getFriends().stream().map(Mapping::map).collect(Collectors.toList());
    }

    public List<Group> getGroups()
    {
        return this.getMiraiBot().getGroups().stream().map(Mapping::map).collect(Collectors.toList());
    }

    public List<Stranger> getStrangers()
    {
        return this.getMiraiBot().getStrangers().stream().map(Mapping::map).collect(Collectors.toList());
    }

    public List<OtherClient> getOtherClients()
    {
        return this.getMiraiBot().getOtherClients().stream().map(Mapping::map).collect(Collectors.toList());
    }

    public void login()
    {
        getMiraiBot().login();
    }

    public void join()
    {
        getMiraiBot().join();
    }

    public void close()
    {
        getMiraiBot().close();
    }

    public String getNick()
    {
        return getMiraiBot().getNick();
    }

    @Override
    public String getName()
    {
        return asFriend.getName();
    }

    @Override
    public Type getUserType()
    {
        return asFriend.getUserType();
    }

    @Override
    public Contact.Type getChatType()
    {
        return asFriend.getChatType();
    }

    @Override
    public String toString()
    {
        return "Bot:{"+getId()+"}";
    }
}
