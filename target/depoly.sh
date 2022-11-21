#!/bin/bash
rm nuclear.war
mv *.war nuclear.war
if [ ! -f "E:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/webapps/nuclear.war" ];then
  echo "file isn't exist"
else
  rm -f "E:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/webapps/nuclear.war"
fi
cp -i nuclear.war "E:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/webapps/nuclear.war"