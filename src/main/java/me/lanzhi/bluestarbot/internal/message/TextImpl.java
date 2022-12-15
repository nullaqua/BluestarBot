package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.message.Text;
import net.mamoe.mirai.message.data.PlainText;

public class TextImpl implements Text, MessageImpl
{
    private final String message;

    public TextImpl(String message)
    {
        this.message=message;
    }

    @Override
    public net.mamoe.mirai.message.data.Message toMirai()
    {
        return new PlainText(message);
    }
}
