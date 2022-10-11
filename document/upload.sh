#!/bin/bash
#加一个判断文件夹下面war,取war(mv)
rm nuclear3.war
mv *.war nuclear3.war
scp -P 22 ./nuclear3.war hexi@47.104.172.28:/home/hexi/dock_hexi/project_hexi_java/tomcat/webapps/
#scp -r ./dist hexi@47.104.172.28:/home/hexi/dock_hexi/project_hexi_java/tomcat/webapps/
