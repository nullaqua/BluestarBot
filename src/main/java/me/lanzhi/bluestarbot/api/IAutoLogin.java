package me.lanzhi.bluestarbot.api;

import java.util.List;

public interface IAutoLogin
{
    /**
     * 添加一个自动登录机器人
     *
     * @param id       机器人ID
     * @param password 密码
     */
    void addAutologin(long id,String password);

    /**
     * 移除一个机器人
     *
     * @param id 机器人ID
     */
    void removeAutoLogin(long id);

    /**
     * 获取自动登录机器人列表
     *
     * @return 自动登录机器人列表
     */
    List<Long> getList();

    /**
     * 登录所有机器人
     */
    void loginAll();
}
