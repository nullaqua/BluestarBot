package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.message.MessageImpl;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.VipFace;

import java.util.HashMap;
import java.util.Map;

/**
 * VIP表情,支持接受但无法再次发送
 */
public enum VIPFace implements MessageImpl
{
    榴莲(VipFace.LiuLian),
    平底锅(VipFace.PingDiGuo),
    钞票(VipFace.ChaoPiao),
    略略略(VipFace.LueLueLue),
    猪头(VipFace.ZhuTou),
    便便(VipFace.BianBian),
    炸弹(VipFace.ZhaDan),
    爱心(VipFace.AiXin),
    哈哈(VipFace.HaHa),
    点赞(VipFace.DianZan),
    亲亲(VipFace.QinQin),
    药丸(VipFace.YaoWan);

    private static final Map<VipFace.Kind,VIPFace> map=new HashMap<>();

    static
    {
        for (VIPFace face: values())
        {
            map.put(face.face,face);
        }
    }

    private final VipFace.Kind face;

    VIPFace(VipFace.Kind face)
    {
        this.face=face;
    }

    public static VIPFace getFace(VipFace vipFace)
    {
        return map.get(vipFace.getKind());
    }

    @Internal
    @Override
    public Message toMirai()
    {
        return new VipFace(face,1);
    }
}
