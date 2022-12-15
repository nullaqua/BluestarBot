package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.message.code.MiraiCode;

public interface Message
{
    /**
     * 通过Mirai码创建Message
     *
     * @param code Mirai码
     * @return 消息
     */
    static MessageChain getFromMiraiCode(String code)
    {
        return Mapping.map(MiraiCode.deserializeMiraiCode(code));
    }

    /**
     * 消息在某一聊天中的显示
     *
     * @param contact 聊天
     * @return 显示
     */
    default String getDisplay(Contact contact)
    {
        return contentToString();
    }

    /**
     * 直接转换为String,大多情况下与{@linkplain Message#getDisplay(Contact)}相同,但AT等显示不如其准确,
     * 故建议使用{@linkplain Message#getDisplay(Contact)}
     */
    String contentToString();
}
