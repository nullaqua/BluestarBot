package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface SendAudioAble
{
    public default void sendAudio(File audio)
    {
        this.sendAudio(ExternalResource.create(audio).toAutoCloseable());
    }

    public default void sendAudio(InputStream stream) throws IOException
    {
        this.sendAudio(ExternalResource.create(stream).toAutoCloseable());
    }

    public void sendAudio(ExternalResource resource);
}
