package me.lanzhi.bluestarbot.api;

import me.lanzhi.bluestarbot.api.contact.Friend;
import me.lanzhi.bluestarbot.api.contact.OtherClient;
import me.lanzhi.bluestarbot.api.contact.Stranger;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.api.contact.group.Group;
import me.lanzhi.bluestarbot.internal.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一个机器人
 */
public final class Bot extends User
{
    private final Friend asFriend;

    @Internal
    public Bot(net.mamoe.mirai.Bot bot)
    {
        super(bot);
        this.asFriend=Mapping.map(getMiraiBot().getAsFriend());
    }

    /**
     * @return 是否在线,在线为true
     */
    public boolean isOnline()
    {
        return getMiraiBot().isOnline();
    }

    @Internal
    public net.mamoe.mirai.Bot getMiraiBot()
    {
        return (net.mamoe.mirai.Bot) super.getUser();
    }

    /**
     * 获取机器人已加入的一个群聊
     */
    public Group getGroup(long id)
    {
        return Mapping.map(getMiraiBot().getGroup(id));
    }

    /**
     * 获取机器人的一个好友
     */
    public Friend getFriend(long id)
    {
        return Mapping.map(getMiraiBot().getFriend(id));
    }

    /**
     * 获取一个陌生人
     */
    public Stranger getStranger(long id)
    {
        return Mapping.map(getMiraiBot().getStranger(id));
    }

    /**
     * 获取一个其他客户端
     */
    public OtherClient getOtherClient(long otherClient)
    {
        return Mapping.map(this.getMiraiBot().getOtherClients().get(otherClient));
    }

    /**
     * 转换为朋友形式,发送消息就会是自己发给自己
     */
    public Friend asFriend()
    {
        return asFriend;
    }

    /**
     * 转换为陌生人形式
     */
    public Stranger asStranger()
    {
        return Mapping.map(getMiraiBot().getAsStranger());
    }

    /**
     * 朋友列表
     */
    public List<Friend> getFriends()
    {
        return this.getMiraiBot().getFriends().stream().map(Mapping::map).collect(Collectors.toList());
    }

    /**
     * 加入的所有群聊列表
     */
    public List<Group> getGroups()
    {
        return this.getMiraiBot().getGroups().stream().map(Mapping::map).collect(Collectors.toList());
    }

    /**
     * 陌生人列表
     */
    public List<Stranger> getStrangers()
    {
        return this.getMiraiBot().getStrangers().stream().map(Mapping::map).collect(Collectors.toList());
    }

    /**
     * 其他客户端列表
     */
    public List<OtherClient> getOtherClients()
    {
        return this.getMiraiBot().getOtherClients().stream().map(Mapping::map).collect(Collectors.toList());
    }

    /**
     * 进行登录
     */
    public void login()
    {
        getMiraiBot().login();
    }

    /**
     * 堵塞线程,直到此机器人关闭
     */
    public void join()
    {
        getMiraiBot().join();
    }

    /**
     * 关闭机器人
     */
    public void close()
    {
        getMiraiBot().close();
    }

    /**
     * 机器人的昵称
     */
    public String getNick()
    {
        return getMiraiBot().getNick();
    }

    @Override
    public Type getType()
    {
        return Type.Bot;
    }

    @Override
    public String toString()
    {
        return "Bot:{"+getId()+"}";
    }
}
