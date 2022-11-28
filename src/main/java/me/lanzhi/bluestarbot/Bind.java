package me.lanzhi.bluestarbot;

import me.lanzhi.api.player.gui.GuiItem;
import me.lanzhi.api.player.gui.PageGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Bind implements Serializable
{
    private static final long serialVersionUID=0L;
    private final Map<UUID,Long> binds;

    public Bind()
    {
        binds=new HashMap<>();
    }

    public void addBind(UUID uuid,long id)
    {
        if (uuid==null)
        {
            return;
        }
        binds.put(uuid,id);
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

    public Long getBind(UUID uuid)
    {
        if (uuid==null)
        {
            return null;
        }
        return binds.get(uuid);
    }

    public Long removeBind(UUID uuid)
    {
        return binds.remove(uuid);
    }

    public UUID removeBind(long id)
    {
        UUID uuid=getBind(id);
        binds.values().remove(id);
        return uuid;
    }

    public PageGui.Builder getGui()
    {
        PageGui.Builder builder=PageGui.builder();
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
}
