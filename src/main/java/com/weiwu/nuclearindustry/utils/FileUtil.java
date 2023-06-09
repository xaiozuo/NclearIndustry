package com.weiwu.nuclearindustry.utils;

import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Method;
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
    public static void doJpg(String imagesPath, File file, String directoryName, Object object) {
        String newPath = imagesPath + File.separator + directoryName + File.separator + file.getName();
        File newFile = new File(newPath);
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        Class<?> aClass = object.getClass();
        if(prefix.equals(directoryName) || prefix.contains(directoryName) ||
                NameUtil.nameEqual(prefix, directoryName)){
            if(!prefix.contains("thumb") && !prefix.contains("Thumb")){
                Method getImageUrl = aClass.getDeclaredMethod("getImageUrl");
                Method setImageUrl = aClass.getDeclaredMethod("setImageUrl", String.class);
                String imageUrl = (String) getImageUrl.invoke(object);
                if(imageUrl == null){
                    setImageUrl.invoke(object, fileName);
                    FileUtils.copyFile(file, newFile);
                }
            }
            if(prefix.contains("thumb") || prefix.contains("Thumb")){
                Method getThumbUrl = aClass.getDeclaredMethod("getThumbUrl");
                Method setThumbUrl = aClass.getDeclaredMethod("setThumbUrl", String.class);
                String thumbUrl = (String) getThumbUrl.invoke(object);
                if(thumbUrl == null){
                    setThumbUrl.invoke(object, fileName);
                    FileUtils.copyFile(file, newFile);
                }
            }
        }
    }

    /**
     * Ability to parse multiple XML files
     *
     * @param file
     * @param directoryName
     * @param object
     */
    @SneakyThrows
    public static void doXml(String filePath, File file, String directoryName, Object object) {
        String newPath = filePath + File.separator + directoryName + File.separator + file.getName();
        File newFile = new File(newPath);
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        if (fileName.startsWith("GF3") || fileName.startsWith("ZY") ||
                prefix.equals(directoryName) || prefix.contains(directoryName)) {
            BeanUtil.build(new XmlParser(), file, object);
            FileUtils.copyFile(file, newFile);
        }
    }

    public static void doFiles(String filesPath, File[] files, String directoryName, Object object)  {
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith("jpg") || fileName.endsWith("xml")) {
                if (fileName.endsWith("xml")) {
                    doXml(filesPath, file, directoryName, object);
                }
                if (fileName.endsWith("jpg")) {
                    doJpg(filesPath, file, directoryName, object);
                }
            }
        }
    }

    /**
     * only decompress xml and jpg file
     * @param unTarGzPath
     * @param file
     * @return
     */
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

            byte[] buffer = new byte[1024 * 16]; //注意缓存的大小
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

    public static File unTarGzBy7Zip(String unTarGzPath, File file){
        File directory = null;
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

    public static void deleteFile(String path){
        File file = new File(path);
        if(file.isFile() && file.exists()){
            file.delete();
        }
    }

    public static void deleteDir(File directory){
        File[] files = directory.listFiles();
        assert files != null;
        for(File file : files){
            if(file.isDirectory()){
                deleteDir(file);
            } else {
                file.delete();
            }
        }
        directory.delete();
    }
}
