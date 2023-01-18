package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.SendAudioAble;
import me.lanzhi.bluestarbot.api.message.Audio;
import net.mamoe.mirai.message.data.OfflineAudio;
import net.mamoe.mirai.message.data.OnlineAudio;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Internal
public abstract class AudioImpl implements Audio, MessageImpl
{
    public static AudioImpl create(File file,SendAudioAble sendAudioAble)
    {
        return new Custom(file,sendAudioAble);
    }

    public static AudioImpl create(InputStream stream,SendAudioAble sendAudioAble) throws IOException
    {
        return new Custom(stream,sendAudioAble);
    }

    public static AudioImpl create(OnlineAudio audio)
    {
        return new FromMirai(audio);
    }

    @Override
    public long getSize()
    {
        return toMirai().getFileSize();
    }

    @Override
    public byte[] md5()
    {
        return toMirai().getFileMd5();
    }

    @Override
    public String fileName()
    {
        return toMirai().getFilename();
    }

    @Override
    public abstract net.mamoe.mirai.message.data.Audio toMirai();

    @Internal
    private static final class Custom extends AudioImpl
    {
        private final ExternalResource resource;
        private final SendAudioAble sendAudioAble;

        private Custom(File file,SendAudioAble sendAudioAble)
        {
            this.resource=ExternalResource.create(file).toAutoCloseable();
            this.sendAudioAble=sendAudioAble;
        }

        private Custom(InputStream file,SendAudioAble sendAudioAble) throws IOException
        {
            this.resource=ExternalResource.create(file).toAutoCloseable();
            this.sendAudioAble=sendAudioAble;
        }

        @Override
        public OfflineAudio toMirai()
        {
            return sendAudioAble.getAudioSupported().uploadAudio(resource);
        }

        @Override
        public String getDownloadURL()
        {
            return null;
        }

        @Override
        public Long length()
        {
            return null;
        }
    }

    @Internal
    private static final class FromMirai extends AudioImpl
    {
        private final OnlineAudio onlineAudio;

        private FromMirai(OnlineAudio onlineAudio)
        {
            this.onlineAudio=onlineAudio;
        }

        @Override
        public String getDownloadURL()
        {
            return onlineAudio.getUrlForDownload();
        }

        @Override
        public Long length()
        {
            return onlineAudio.getLength();
        }

        @Override
        public net.mamoe.mirai.message.data.Audio toMirai()
        {
            return onlineAudio;
        }
    }
}
