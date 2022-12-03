package me.lanzhi.bluestarbot.api;

import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface SendAudioAble
{
    public default void sendAudio(File audio)
    {
        this.sendAudio0(ExternalResource.create(audio).toAutoCloseable());
    }

    void sendAudio0(ExternalResource resource);

    public default void sendAudio(InputStream stream) throws IOException
    {
        this.sendAudio0(ExternalResource.create(stream).toAutoCloseable());
    }
}
