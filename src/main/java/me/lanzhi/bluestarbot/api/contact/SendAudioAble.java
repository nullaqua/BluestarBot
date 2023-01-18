package me.lanzhi.bluestarbot.api.contact;

import me.lanzhi.bluestarbot.api.Internal;
import net.mamoe.mirai.contact.AudioSupported;

/**
 * 支持发送音频的聊天频道
 *
 * @see me.lanzhi.bluestarbot.api.message.Audio 音频消息
 */
public interface SendAudioAble
{
    /**
     * 内部实现
     */
    @Internal
    public AudioSupported getAudioSupported();
}
