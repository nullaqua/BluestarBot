package me.lanzhi.bluestarbot.api.event;

import me.lanzhi.bluestarbot.api.Bot;
import me.lanzhi.bluestarbot.api.OtherClient;

public interface OtherClientEvent
{
    public Bot getBot();

    public OtherClient getOtherClient();
}
