package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.Stranger;

public interface StrangerEvent extends UserEvent
{

    public Bot getBot();

    public default Stranger getStranger()
    {
        return getUser();
    }

    @Override
    public Stranger getUser();
}
