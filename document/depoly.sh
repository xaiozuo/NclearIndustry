#!/bin/bash
rm nuclear.war
mv *.war nuclear.war
if [ ! -d "D:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\nuclear" ];then
  echo "directory isn't exist"
else
  rm -rf D:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\nuclear
fi
if [ ! -f "D:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\nuclear.war" ];then
  echo "file isn't exist"
else
  rm -f D:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\nuclear.war
fi
cp -i nuclear.war D:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\nuclear.war