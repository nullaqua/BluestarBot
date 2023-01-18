package me.lanzhi.bluestarbot.internal.classloader;

import me.lanzhi.api.reflect.URLClassLoaderAccessor;
import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.Authenticator;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static me.lanzhi.bluestarbot.internal.Utils.logger;

@Internal
public class MiraiLoader
{
    @Internal
    public static void loadMiraiCore() throws Throwable
    {
        try
        {
            ClassLoader classLoader=Class.forName("me.dreamvoid.miraimc.api.MiraiMC").getClassLoader();
            Utils.setClassLoaderAccess(URLClassLoaderAccessor.of((URLClassLoader) classLoader));
            logger().warning("检测到MiraiMC,已挂钩,跳过Mirai核心加载");
            return;
        }
        catch (Throwable ignored)
        {
        }
        try
        {
            ClassLoader classLoader=Class.forName("net.mamoe.mirai.Mirai").getClassLoader();
            Utils.setClassLoaderAccess(URLClassLoaderAccessor.of((URLClassLoader) classLoader));
            logger().warning("检测到Mirai核心,跳过Mirai核心加载");
            return;
        }
        catch (Throwable ignored)
        {
        }
        logger().warning("正在加载Mirai核心");
        load("net.mamoe","mirai-core-all","latest");
    }

    @Internal
    public static void load(String group,String id,String version) throws Throwable
    {
        if (version.equalsIgnoreCase("latest"))
        {
            version=LibrariesLoader.getLibraryVersionMaven(group,
                                                           id,
                                                           "https://maven.aliyun.com/nexus/content/groups/public/",
                                                           "release");
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
            LibrariesLoader.loadLibraryClassMaven(group,
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
        catch (Throwable e)
        {
            logger().warning("Unable to download mirai core from remote server("+e+"), try to use local core.");
            if (writeName.exists())
            {
                String content=new String(Files.readAllBytes(writeName.toPath()),StandardCharsets.UTF_8);
                if (!content.equals(""))
                {
                    String name=id+"-"+content+".jar";
                    File coreFile=new File(LibrariesDir,name);
                    LibrariesLoader.loadLibraryClassLocal(coreFile);
                }
                else
                {
                    logger().warning("Unable to use local core.");
                }
            }
            else
            {
                logger().warning("No local core found.");
            }
        }
    }

    @Internal
    public static void load(String group,String id) throws Throwable
    {
        load(group,id,"latest");
    }
}
