package com.weiwu.nuclearindustry.utils;

public class NameUtil {

    /**
     * transform name to upper camel case
     * @param name
     * @return
     */
    public static String getUpperCamelCase(String name){
        if(name == null || "".equals(name)){
            return "";
        }
        String[] strings = name.split("[^a-zA-Z0-9]+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(getTitleCase(strings[i]));
        }
        return sb.toString();
    }

    /**
     * transform name to lower camel case
     * @param name
     * @return
     */
    public static String getLowerCamelCase(String name){
        name = getUpperCamelCase(name);
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * transform name from lower camel case to upper
     * @param name
     * @return
     */
    public static String lowerToUpperCamelCase(String name){
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String getTitleCase(String name){
        if (name == null || "".equals(name)){
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static boolean nameEqual(String filename, String directoryName){
        String[] fileNames = filename.split("_");
        String[] directoryNames = directoryName.split("_");
        StringBuilder name1 = new StringBuilder();
        StringBuilder name2 = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            name1 = name1.append(fileNames[i]);
            name2 = name2.append(directoryNames[i]);
        }
        return name1 == name2;
    }

}
