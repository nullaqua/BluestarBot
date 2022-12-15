package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.message.MusicShare;
import me.lanzhi.bluestarbot.api.message.MusicShare.MusicKind;

public class MusicShareImpl implements MusicShare, MessageImpl
{
    private final MusicKind kind;
    /**
     * 消息卡片标题
     */
    private final String title;
    /**
     * 消息卡片内容
     */
    private final String summary;
    /**
     * 点击卡片跳转网页 URL
     */
    private final String jumpUrl;
    /**
     * 消息卡片图片 URL
     */
    private final String pictureUrl;
    /**
     * 音乐文件 URL
     */
    private final String musicUrl;
    /**
     * 在消息列表显示. 例如 `"[分享]歌曲名称"`
     */
    private final String brief;
    private final net.mamoe.mirai.message.data.MusicShare share;

    public MusicShareImpl(net.mamoe.mirai.message.data.MusicShare share)
    {
        this.share=share;
        this.kind=MusicKind.map(share.getKind());
        this.title=share.getTitle();
        this.summary=share.getSummary();
        this.jumpUrl=share.getJumpUrl();
        this.pictureUrl=share.getPictureUrl();
        this.musicUrl=share.getMusicUrl();
        this.brief=share.getBrief();
    }

    public MusicShareImpl(MusicKind kind,String title,String summary,String jumpUrl,String pictureUrl,String musicUrl
            ,String brief)
    {
        this.kind=kind;
        this.title=title;
        this.summary=summary;
        this.jumpUrl=jumpUrl;
        this.pictureUrl=pictureUrl;
        this.musicUrl=musicUrl;
        this.brief=brief;
        share=new net.mamoe.mirai.message.data.MusicShare(kind.getMusicKind(),
                                                          title,
                                                          summary,
                                                          jumpUrl,
                                                          pictureUrl,
                                                          musicUrl,
                                                          brief);
    }

    public me.lanzhi.bluestarbot.api.message.MusicShare.MusicKind getKind()
    {
        return kind;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSummary()
    {
        return summary;
    }

    public String getJumpUrl()
    {
        return jumpUrl;
    }

    public String getPictureUrl()
    {
        return pictureUrl;
    }

    public String getMusicUrl()
    {
        return musicUrl;
    }

    public String getBrief()
    {
        return brief;
    }

    @Override
    public net.mamoe.mirai.message.data.Message toMirai()
    {
        return share;
    }
}
