#!/bin/bash
#system info
uname -a #kernel/OS system CPU info
head -n 1 /etc/issue #view OS system
cat /proc/cpuinfo
hostname
lspci -tv #list all pci device
lsusb -tv #list all usb device
lsmod #list loaded kernel module
env # view env variable

#memory source
free -m #view memory used info
df -h #view partition used condition
du -sh dirname #view directory size
grep MemTotal /proc/meminfo
grep MemFree /proc/meminfo
uptime #view system running time,user number,load
cat /proc/loadavg #view system load

#disk and partition
mount | column -t #view partition status
fdisk -l #view all partition
swapon -s #view all exchange partition
hdparm -i /dev/hda #view disk parameter
dmesg | grep IDE

#network
ifconfig #view all network interface paramter
iptables #view firewall settings
route -n #view route table
netstat -lntp #view all listen port
netstat -antp #view all established connection
netstat -s #statistic network info

#process
ps -ef #view all process
top #realtime display process status

#user
w #view active user
id username #view user info
last #view user login log
cut -d: -f1 /etc/passwd #view system all users
cut -d: -f1 /etc/group #view system all group
crontab -l #view current user plan task

#service
chkconfig --list #view all system service
chkconfig --list | grep on #view all active service
systemctl list-unit-files

#app
rpm -qa #view all install package

