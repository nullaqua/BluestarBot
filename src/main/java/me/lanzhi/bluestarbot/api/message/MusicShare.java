package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.internal.message.MusicShareImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个音乐分享
 */
public interface MusicShare extends Message
{
    /**
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    static MusicShare create(MusicKind kind,String title,String summary,String jumpUrl,String pictureUrl,
                             String musicUrl)
    {
        return create(kind,title,summary,jumpUrl,pictureUrl,musicUrl,"[分享]"+title);
    }

    /**
     * 创建一个音乐分享
     *
     * @param kind       分享类型
     * @param title      分享卡片的标题
     * @param summary    分享卡片的显示内容
     * @param jumpUrl    点击分享卡片后跳转的链接
     * @param pictureUrl 卡片图片的链接
     * @param musicUrl   音乐文件的链接
     * @param brief      在消息列表界面的简短显示,默认为`[分享]title`
     * @return 创建的音乐分享
     */
    static MusicShare create(MusicKind kind,String title,String summary,String jumpUrl,String pictureUrl,
                             String musicUrl,String brief)
    {
        return new MusicShareImpl(kind,title,summary,jumpUrl,pictureUrl,musicUrl,brief);
    }

    /**
     * 获取分享类型
     *
     * @return 分享类型
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    MusicKind getKind();

    /**
     * 获取分享标题
     *
     * @return 标题
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getTitle();

    /**
     * 分享卡片显示的内容
     *
     * @return 显示的内容
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getSummary();

    /**
     * 点击后跳转的URL链接
     *
     * @return URL链接
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getJumpUrl();

    /**
     * 卡片图片的URL链接
     *
     * @return URL链接
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getPictureUrl();

    /**
     * 音乐文件的URL
     *
     * @return URL链接
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getMusicUrl();

    /**
     * 在消息列表显示. 例如 `"[分享]歌曲名称"`
     *
     * @return 显示
     * @see MusicShare#create(MusicKind,String,String,String,String,String,String)
     */
    String getBrief();

    enum MusicKind
    {
        NeteaseCloudMusic(net.mamoe.mirai.message.data.MusicKind.NeteaseCloudMusic),
        QQMusic(net.mamoe.mirai.message.data.MusicKind.QQMusic),
        MiguMusic(net.mamoe.mirai.message.data.MusicKind.MiguMusic),
        KugouMusic(net.mamoe.mirai.message.data.MusicKind.KugouMusic),
        KuwoMusic(net.mamoe.mirai.message.data.MusicKind.KuwoMusic);
        private static final Map<net.mamoe.mirai.message.data.MusicKind,MusicKind> map=new HashMap<>();

        static
        {
            for (MusicKind kind: MusicKind.values())
            {
                map.put(kind.musicKind,kind);
            }
        }

        private final net.mamoe.mirai.message.data.MusicKind musicKind;

        MusicKind(net.mamoe.mirai.message.data.MusicKind musicKind)
        {
            this.musicKind=musicKind;
        }

        public static MusicKind map(net.mamoe.mirai.message.data.MusicKind musicKind)
        {
            return map.get(musicKind);
        }

        public net.mamoe.mirai.message.data.MusicKind getMusicKind()
        {
            return musicKind;
        }
    }
}
