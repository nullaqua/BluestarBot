package me.lanzhi.bluestarbot.classloader;

import me.lanzhi.bluestarbot.BluestarBotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static me.lanzhi.bluestarbot.classloader.LibrariesLoader.*;

public class MiraiLoader
{
    public static void loadMiraiCore() throws RuntimeException, IOException, ParserConfigurationException, SAXException
    {
        load("net.mamoe","mirai-core-all","2.11.1");
    }

    public static void load(String group,String id) throws IOException, ParserConfigurationException, SAXException
    {
        load(group,id,"latest");
    }

    public static void load(String group,String id,String version) throws RuntimeException, IOException, ParserConfigurationException, SAXException
    {
        if (version.equalsIgnoreCase("latest"))
        {
            version=getLibraryVersionMaven(group,id,"https://maven.aliyun.com/nexus/content/groups/public/","release");
        }

        File pluginDir=JavaPlugin.getPlugin(BluestarBotPlugin.class).getDataFolder();
        File LibrariesDir=new File(pluginDir,"libs");
        if (!LibrariesDir.exists()&&!LibrariesDir.mkdirs())
        {
            throw new RuntimeException("Failed to create "+LibrariesDir.getPath());
        }

        File writeName=new File(new File(pluginDir,"cache"),"core-ver");
        if (!writeName.getParentFile().exists()&&!writeName.getParentFile().mkdirs())
        {
            throw new RuntimeException("Failed to create "+writeName.getParentFile().getPath());
        }

        try
        {
            loadLibraryClassMaven(group,
                                  id,
                                  version,
                                  "-all",
                                  "https://maven.aliyun.com/nexus/content/groups/public/",
                                  LibrariesDir);
            try (FileWriter writer=new FileWriter(writeName);BufferedWriter out=new BufferedWriter(writer))
            {
                out.write(version);
                out.flush();
            }
        }
        catch (Exception e)
        {
            Bukkit.getLogger()
                  .warning("Unable to download mirai core from remote server("+e+"), try to use local core.");
            if (writeName.exists())
            {
                String content=new String(Files.readAllBytes(writeName.toPath()),StandardCharsets.UTF_8);
                if (!content.equals(""))
                {
                    String name=id+"-"+content+".jar";
                    File coreFile=new File(LibrariesDir,name);
                    loadLibraryClassLocal(coreFile);
                }
                else
                {
                    Bukkit.getLogger().warning("Unable to use local core.");
                }
            }
            else
            {
                Bukkit.getLogger().warning("No local core found.");
            }
        }
    }
}
