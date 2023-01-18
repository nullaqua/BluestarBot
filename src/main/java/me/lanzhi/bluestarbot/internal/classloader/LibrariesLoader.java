package me.lanzhi.bluestarbot.internal.classloader;

import me.lanzhi.bluestarbot.BluestarBotPlugin;
import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.internal.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.Warning;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static me.lanzhi.bluestarbot.internal.Utils.logger;

@Internal
public class LibrariesLoader
{
    @Warning
    @Internal
    static String getLibraryVersionMaven(String groupId,String artifactId,String repoUrl,String xmlTag) throws RuntimeException, IOException, ParserConfigurationException, SAXException
    {
        File CacheDir=new File(JavaPlugin.getPlugin(BluestarBotPlugin.class).getDataFolder(),"cache");
        if (!CacheDir.exists()&&!CacheDir.mkdirs())
        {
            throw new RuntimeException("Failed to create "+CacheDir.getPath());
        }
        String metaFileName="maven-metadata-"+groupId+"."+artifactId+".xml";
        File metaFile=new File(CacheDir,metaFileName);
        if (!repoUrl.endsWith("/"))
        {
            repoUrl+="/";
        }
        repoUrl+="%s/%s/";
        String repoFormat=String.format(repoUrl,groupId.replace(".","/"),artifactId);
        File metaFileMD5=new File(CacheDir,metaFileName+".md5");
        if (metaFileMD5.exists()&&!metaFileMD5.delete())
        {
            throw new RuntimeException("Failed to delete "+metaFileMD5.getPath());
        }
        URL metaFileMD5Url=new URL(repoFormat+"maven-metadata.xml.md5");
        downloadFile(metaFileMD5,metaFileMD5Url);
        if (!metaFileMD5.exists())
        {
            throw new RuntimeException("Failed to download "+metaFileMD5Url);
        }
        logger().info("Verifying "+metaFileName);
        if (metaFile.exists())
        {
            try (FileInputStream fis=new FileInputStream(metaFile))
            {
                if (!DigestUtils.md5Hex(fis)
                                .equals(new String(Files.readAllBytes(metaFileMD5.toPath()),StandardCharsets.UTF_8)))
                {
                    fis.close();
                    if (!metaFile.delete())
                    {
                        throw new RuntimeException("Failed to delete "+metaFile.getPath());
                    }

                    URL metaFileUrl=new URL(repoFormat+"maven-metadata.xml");
                    downloadFile(metaFile,metaFileUrl);
                    if (!metaFileMD5.exists())
                    {
                        throw new RuntimeException("Failed to download "+metaFileUrl);
                    }
                }
            }
        }
        else
        {
            URL metaFileUrl=new URL(repoFormat+"maven-metadata.xml");
            logger().info("Downloading "+metaFileUrl);
            downloadFile(metaFile,metaFileUrl);
            if (!metaFileMD5.exists())
            {
                throw new RuntimeException("Failed to download "+metaFileUrl);
            }
        }
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document doc=builder.parse(metaFile);
        return doc.getElementsByTagName(xmlTag).item(0).getFirstChild().getNodeValue();
    }

    @Internal
    private static void downloadFile(File file,URL url) throws IOException
    {
        try (InputStream is=url.openStream())
        {
            Files.copy(is,file.toPath());
        }
    }

    @Internal
    static void loadLibraryClassMaven(String groupId,String artifactId,String version,String extra,String repo,File path) throws Throwable
    {
        String name=artifactId+"-"+version+".jar";
        File saveLocation=new File(path,name);
        logger().info("Verifying "+name);
        if (!downloadLibraryMaven(groupId,artifactId,version,extra,repo,saveLocation,true))
        {
            throw new RuntimeException("Failed to download libraries!");
        }
        loadLibraryClassLocal(saveLocation);
    }

    @Internal
    static boolean downloadLibraryMaven(String groupId,String artifactId,String version,String extra,String repo,File file,boolean checkMD5) throws RuntimeException, IOException
    {
        if (!file.getParentFile().exists()&&!file.getParentFile().mkdirs())
        {
            throw new RuntimeException("Failed to create "+file.getParentFile().getPath());
        }
        if (!repo.endsWith("/"))
        {
            repo+="/";
        }
        repo+="%s/%s/%s/%s-%s%s.jar";
        String DownloadURL=String.format(repo,groupId.replace(".","/"),artifactId,version,artifactId,version,extra);
        String FileName=artifactId+"-"+version+".jar";

        if (checkMD5)
        {
            File FileMD5=new File(file.getParentFile(),FileName+".md5");
            String DownloadMD5Url=DownloadURL+".md5";
            URL DownloadMD5UrlFormat=new URL(DownloadMD5Url);

            if (FileMD5.exists()&&!FileMD5.delete())
            {
                throw new RuntimeException("Failed to delete "+FileMD5.getPath());
            }
            downloadFile(FileMD5,DownloadMD5UrlFormat);
            if (!FileMD5.exists())
            {
                throw new RuntimeException("Failed to download "+DownloadMD5Url);
            }

            if (file.exists())
            {
                FileInputStream fis=new FileInputStream(file);
                boolean isSame=DigestUtils.md5Hex(fis)
                                          .equals(new String(Files.readAllBytes(FileMD5.toPath()),
                                                             StandardCharsets.UTF_8));
                if (!isSame)
                {
                    fis.close();
                    if (!file.delete())
                    {
                        throw new RuntimeException("Failed to delete "+file.getPath());
                    }
                }
            }
        }
        else if (file.exists()&&!file.delete())
        {
            throw new RuntimeException("Failed to delete "+file.getPath());
        }
        if (!file.exists())
        {
            logger().info("Downloading "+DownloadURL);
            downloadFile(file,new URL(DownloadURL));
        }
        return file.exists();
    }

    @Internal
    static void loadLibraryClassLocal(File file) throws Throwable
    {
        logger().info("Loading library "+file);
        Utils.classLoaderAccessor().addURL(file.toURI().toURL());
    }
}
