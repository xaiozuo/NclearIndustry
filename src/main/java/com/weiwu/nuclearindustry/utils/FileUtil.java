package com.weiwu.nuclearindustry.utils;

import com.weiwu.nuclearindustry.config.SystemConfig;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;


public class FileUtil {

    public static String filePrefixName(String filename){
        StringBuilder prefix = new StringBuilder();
        if(filename.endsWith(".tar.gz")){
            int lastIndexOf = filename.lastIndexOf(".tar.gz");
            prefix.append(filename.substring(0, lastIndexOf));
        }
        if(filename.endsWith(".tar")){
            int lastIndexOf = filename.lastIndexOf(".tar");
            prefix.append(filename.substring(0, lastIndexOf));
        }
        return prefix.toString();
    }

    private static String fileSuffixName(String filename){
        return filename;
    }

    public static String getBaseDirectory(String unTarGzPath, String prefix){
        return unTarGzPath + File.separator + prefix;
    }

    public static String getFilePath(String unTarGzPath, String prefix, String filename){
        return unTarGzPath + File.separator + prefix + File.separator + filename;
    }

    @SneakyThrows
    public static File unTarGz(String unTarGzPath, File file){
        String fileName = file.getName();
        String prefix = filePrefixName(fileName);
        File directory = null;
        if(fileName.endsWith(".tar.gz") || fileName.endsWith(".tar")){
            TarArchiveInputStream tai = null;
            if(fileName.endsWith(".tar.gz")){
                GzipCompressorInputStream gci = new GzipCompressorInputStream(
                        Files.newInputStream(file.toPath()), true);
                tai = new TarArchiveInputStream(gci);
            }
            if(fileName.endsWith(".tar")){
                tai = new TarArchiveInputStream(Files.newInputStream(file.toPath()));
            }

            byte[] buffer = new byte[1024];
            TarArchiveEntry entry;
            while((entry = tai.getNextTarEntry()) != null){
                String entryName = entry.getName();
                if(entry.isDirectory() || (!entryName.endsWith(".xml") && !entryName.endsWith(".jpg"))){
                    continue;
                }
                if(entryName.endsWith(".xml") || entryName.endsWith(".jpg")){
                    String entryFilename = getFilePath(unTarGzPath, prefix, entryName);
                    File entryFile = new File(entryFilename);
                    if(!entryFile.getParentFile().exists()){
                        entryFile.getParentFile().mkdirs();
                    }
                    if(directory == null) directory = entryFile.getParentFile();

                    FileOutputStream fileOutputStream = new FileOutputStream(entryFile);
                    int len;////Write as much as you read
                    while ( (len = tai.read(buffer)) > 0){
                        fileOutputStream. write(buffer, 0, len);
                    }
                }
            }
        }
        return directory;
    }

    public static File getFile(InputStream is, String filename){
        File file = new File(filename);

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
