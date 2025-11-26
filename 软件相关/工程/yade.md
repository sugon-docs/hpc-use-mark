# 安装

- 官方手册
> https://yade-dem.org/doc/installation.html

- 做好的镜像
```bash
/public/home/jsyadmin/tanjj/miror/tran/   # 昆山
/public/home/jsyadmin/tanjj/miror/yade/   # 昆山
```
> 官方拉取的镜像有问题，运行找不到 `libQt5Core.so.5` 库，可以直接强制忽略，命令行也用不到图形
> 用 `patchelf --set-rpath /usr/lib/x86_64-linux-gnu:/xxx/:/xxx   /usr/lib/x86_64-linux-gnu/yade/py/yade/boot.so` 强行添加可执行库路径
> 最终解决办法：
> `apt install binutils`   
> `strip --remove-section=.note.ABI-tag /usr/lib/x86_64-linux-gnu/libQt5Core.so.5`
> 问题处理网络发现教程：`https://answers.launchpad.net/yade/+question/693806` 

# 镜像

```bash
  
# Dockerfile

FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \

	apt-get -y install wget gnupg2 && \

	bash -c 'echo "deb http://www.yade-dem.org/packages/ focal main" >> /etc/apt/sources.list' && \

	wget -O - http://www.yade-dem.org/packages/yadedev_pub.gpg | apt-key add - && \

	apt-get update && \

	apt-get -y install yadedaily yadedaily-doc yade-doc --no-install-recommends

RUN apt-get -y autoclean && \

	apt-get -y autoremove && \

	rm -rf /var/lib/apt/lists/* && \

	rm -rf /var/cache/apt/archives/*deb
```
# 脚本

- 镜像
```bash
#!/bin/bash
#SBATCH -J yade  ##作业名
#SBATCH -p wzhcnormal   ##队列名
#SBATCH -N 1   ##节点数
#SBATCH --ntasks-per-node=6   ##每节点进程
##SBATCH --cpus-per-task=64   ##每进程占用核数
##SBATCH --exclusive  # 节点独占

module purge
module load singularity/3.7.3 

SIF="/work/home/acyaednxdv/soft/yade/yade.sif"
WDIR=`pwd`       ##获取当前脚本所在路径
cd $WDIR         ##进入文件夹

INPUT=""         ## 输入文件
COMANND="yade -n ; yade --test"  ##命令执行指令   # -n 非图形
##################################################################

singularity exec $SIF bash -c "$COMANND $INPUT"
```