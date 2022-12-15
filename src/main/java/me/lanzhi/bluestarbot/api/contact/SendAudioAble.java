package me.lanzhi.bluestarbot.api.contact;

import net.mamoe.mirai.contact.AudioSupported;

/**
 * 支持发送音频的聊天
 *
 * @see me.lanzhi.bluestarbot.api.message.Audio 音频消息
 */
public interface SendAudioAble
{
    public AudioSupported getAudioSupported();
}
