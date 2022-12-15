package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.contact.User;
import me.lanzhi.bluestarbot.internal.message.MessageImpl;
import net.mamoe.mirai.message.data.PokeMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 戳一戳,指的是那种一个手指的表情,能使窗口抖动的"超级表情"
 * <p>
 *
 * @see Contact#nudge(User) 双击头像的那种戳一戳(类似微信的拍一拍)
 * @see Contact#nudge(long) 双击头像的那种戳一戳(类似微信的拍一拍)
 */
public enum Poke implements MessageImpl, Message
{
    戳一戳(PokeMessage.ChuoYiChuo),
    比心(PokeMessage.BiXin),
    点赞(PokeMessage.DianZan),
    心碎(PokeMessage.XinSui),
    _666(PokeMessage.LiuLiuLiu),
    放大招(PokeMessage.FangDaZhao),
    宝贝球(PokeMessage.BaoBeiQiu),
    玫瑰花(PokeMessage.Rose),
    召唤术(PokeMessage.ZhaoHuanShu),
    让你皮(PokeMessage.RangNiPi),
    结印(PokeMessage.JieYin),
    手雷(PokeMessage.ShouLei),
    勾引(PokeMessage.GouYin),
    抓一下(PokeMessage.ZhuaYiXia),
    碎屏(PokeMessage.SuiPing),
    敲门(PokeMessage.QiaoMen);

    private static final Map<PokeMessage,Poke> map=new HashMap<>();

    static
    {
        for (Poke poke: values())
        {
            map.put(poke.message,poke);
        }
    }

    private final PokeMessage message;

    Poke(PokeMessage message)
    {
        this.message=message;
    }

    public static Poke getPoke(PokeMessage pokeMessage)
    {
        return map.get(pokeMessage);
    }

    @Override
    public net.mamoe.mirai.message.data.Message toMirai()
    {
        return message;
    }
}
