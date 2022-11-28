package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.User;

public interface UserEvent
{
    public Bot getBot();

    public User getUser();
}
