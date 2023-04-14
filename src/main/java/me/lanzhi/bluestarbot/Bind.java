package me.lanzhi.bluestarbot;

import me.lanzhi.api.player.gui.GuiItem;
import me.lanzhi.api.player.gui.PageGui;
import me.lanzhi.api.io.IOAccessor;
import me.lanzhi.api.io.KeyInputStream;
import me.lanzhi.api.io.KeyOutputStream;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 处理Minecraft-QQ绑定,属于内部类,勿动
 */
@Internal
public final class Bind
{
    private final Map<UUID,Long> binds;

    public Bind()
    {
        binds=new HashMap<>();
    }

    public static Bind getInstance()
    {
        return BluestarBotPlugin.getInstance().getBind();
    }

    public void addBind(UUID uuid,long id)
    {
        try
        {
            if (uuid==null)
            {
                return;
            }
            binds.put(uuid,id);
        }
        finally
        {
            Utils.logger().severe(this::save,"账号绑定的数据文件保存失败,可能影响下次运行");
        }
    }

    /**
     * 保存
     */
    void save() throws IOException
    {
        BluestarBotPlugin plugin=BluestarBotPlugin.getInstance();
        File data=new File(plugin.getDataFolder(),"bind.db");
        var out=new DataOutputStream(new KeyOutputStream(Files.newOutputStream(data.toPath()),IOAccessor.hexKey()));
        out.writeLong(binds.size());
        for (Map.Entry<UUID,Long> entry: binds.entrySet())
        {
            out.writeLong(entry.getValue());
            out.writeLong(entry.getKey().getMostSignificantBits());
            out.writeLong(entry.getKey().getLeastSignificantBits());
        }
        out.close();
    }

    static Bind load() throws IOException
    {
        BluestarBotPlugin plugin=BluestarBotPlugin.getInstance();
        File data=new File(plugin.getDataFolder(),"bind.db");
        if (!data.exists())
        {
            return new Bind();
        }
        var in=new DataInputStream(new KeyInputStream(Files.newInputStream(data.toPath()),IOAccessor.hexKey()));
        Bind bind=new Bind();
        long size=in.readLong();
        for (int i=0;i<size;i++)
        {
            long id=in.readLong();
            long most=in.readLong();
            long least=in.readLong();
            bind.binds.put(new UUID(most,least),id);
        }
        in.close();
        return bind;
    }

    public Long getBind(UUID uuid)
    {
        if (uuid==null)
        {
            return null;
        }
        return binds.get(uuid);
    }

    public UUID removeBind(long id)
    {
        try
        {
            UUID uuid=getBind(id);
            binds.values().remove(id);
            return uuid;
        }
        finally
        {
            Utils.logger().severe(this::save,"账号绑定的数据文件保存失败,可能影响下次运行");
        }
    }

    public UUID getBind(long id)
    {
        for (Map.Entry<UUID,Long> entry: binds.entrySet())
        {
            if (entry.getValue()==id)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 创建一个绑定编辑器的GUI
     *
     * @return 创建的GUI
     */
    public PageGui.Builder getGui()
    {
        PageGui.Builder builder=PageGui.builder(JavaPlugin.getProvidingPlugin(getClass()));
        int id=0;
        for (Map.Entry<UUID,Long> e: binds.entrySet())
        {
            ItemStack itemStack=new ItemStack(Material.GOLD_BLOCK);
            ItemMeta meta=itemStack.getItemMeta();
            meta.setDisplayName(Bukkit.getOfflinePlayer(e.getKey()).getName()+"-"+e.getValue());
            meta.setLore(Collections.singletonList(ChatColor.RED+"丢弃物品以删除绑定"));
            itemStack.setItemMeta(meta);
            GuiItem item=new GuiItem();
            item.setItem(itemStack);
            item.setOnClick((gui,clickType)->
                            {
                                if (clickType==ClickType.DROP||clickType==ClickType.CONTROL_DROP)
                                {
                                    removeBind(e.getKey());
                                    return GuiItem.Response.close();
                                }
                                return GuiItem.Response.nothing();
                            });
            builder.item(id++,item);
        }
        return builder.plugin(JavaPlugin.getProvidingPlugin(Bind.class)).title("绑定列表");
    }

    public Long removeBind(UUID uuid)
    {
        try
        {
            return binds.remove(uuid);
        }
        finally
        {
            Utils.logger().severe(this::save,"账号绑定的数据文件保存失败,可能影响下次运行");
        }
    }
}
