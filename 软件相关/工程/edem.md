# 1 安装

## 2018
### 安装好的包
```bash
/work/home/jsyadmin/L4/liucheng/app/EDEM/EDEM_2018.tar   # 安装好的 西安
/work/home/jsyadmin/L4/liucheng/app/EDEM/SentinelLM  # 这是license 
```
### 安装的配置文件注意修改用户啥的

```bash
/work/home/aclw9qseba/software/edem/EDEM_2018/EDEM_2018.conf
/work/home/aclw9qseba/software/edem/EDEM_2018/edemrc
/work/home/aclw9qseba/software/edem/EDEM_2018/bin/edem
/work/home/aclw9qseba/software/edem/EDEM_2018/bin/edem-4.0.0
/work/home/aclw9qseba/software/edem/EDEM_2018/bin/start-edem
```

> https://www.cfd-china.com/topic/7111/edem%E5%AE%89%E8%A3%85
### license 启动

```bash
# 创建 目录下这个文件 # 前提是licens启动过了 # 西安有 要root
mkdir -p ~/.config/DEMSolutions/EDEM_2018/
vim ~/.config/DEMSolutions/EDEM_2018/EDEM_2018.conf

# EDEM_2018.conf的内容如下

[General]
OpenGL%20Warning=false
Theme=Legacy Theme

[Display]
Legacy%20Particle%20display=true

[Licensing]
ServerName=i18r4n15

[Window%20Configuration]
Position="@ByteArray(\0\0\0\xff\0\x4\0\0\xfd\0\0\0\x2\0\0\0\0\0\0\x1\xf1\0\0\x1t\xfc\x2\0\0\0\x3\xfb\0\0\0$\0M\0\x61\0i\0n\0\x44\0o\0\x63\0k\0 \0(\0\x43\0r\0\x65\0\x61\0t\0o\0r\0)\0\0\0\0\x46\0\0\x1t\0\0\x1\x10\0\xff\xff\xff\xfb\0\0\0(\0M\0\x61\0i\0n\0\x44\0o\0\x63\0k\0 \0(\0S\0i\0m\0u\0l\0\x61\0t\0o\0r\0)\0\0\0\0\0\xff\xff\xff\xff\0\0\0`\0\xff\xff\xff\xfb\0\0\0$\0M\0\x61\0i\0n\0\x44\0o\0\x63\0k\0 \0(\0\x41\0n\0\x61\0l\0y\0s\0t\0)\0\0\0\0\0\xff\xff\xff\xff\0\0\x1\x10\0\xff\xff\xff\0\0\0\x3\0\0\x3\x88\0\0\0\xd4\xfc\x1\0\0\0\x3\xfb\0\0\0,\0\x44\0\x61\0t\0\x61\0 \0\x42\0r\0o\0w\0s\0\x65\0r\0 \0(\0\x43\0r\0\x65\0\x61\0t\0o\0r\0)\0\0\0\0`\0\0\x3\x88\0\0\0L\0\xff\xff\xff\xfb\0\0\0\x18\0S\0o\0l\0v\0\x65\0 \0R\0\x65\0p\0o\0r\0t\0\0\0\0\0\xff\xff\xff\xff\0\0\0L\0\xff\xff\xff\xfb\0\0\0,\0\x44\0\x61\0t\0\x61\0 \0\x42\0r\0o\0w\0s\0\x65\0r\0 \0(\0\x41\0n\0\x61\0l\0y\0s\0t\0)\0\0\0\0\0\xff\xff\xff\xff\0\0\0L\0\xff\xff\xff\0\0\x1\x91\0\0\x1t\0\0\0\x4\0\0\0\x4\0\0\0\b\0\0\0\b\xfc\0\0\0\x3\0\0\0\0\0\0\0\x1\0\0\0.\0\x43\0r\0\x65\0\x61\0t\0o\0r\0 \0P\0h\0y\0s\0i\0\x63\0s\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x3\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\x2\0\0\0\x30\0\x43\0r\0\x65\0\x61\0t\0o\0r\0 \0G\0\x65\0o\0m\0\x65\0t\0r\0y\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x3\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\x1e\0\x41\0n\0\x61\0l\0y\0s\0t\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x2\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\x2\0\0\0\t\0\0\0\x18\0M\0\x61\0i\0n\0 \0T\0o\0o\0l\0\x42\0\x61\0r\x1\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\x18\0T\0i\0m\0\x65\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x1\0\0\x1>\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\"\0V\0i\0\x65\0w\0 \0M\0o\0\x64\0\x65\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x1\0\0\x2\f\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0*\0G\0\x65\0o\0m\0\x65\0t\0r\0y\0 \0M\0o\0\x64\0\x65\0 \0T\0o\0o\0l\0\x62\0\x61\0r\x1\0\0\x3)\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\x1e\0U\0t\0i\0l\0i\0t\0y\0 \0T\0o\0o\0l\0\x62\0\x61\0r\0\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\x1a\0\x46\0i\0\x65\0l\0\x64\0 \0T\0o\0o\0l\0\x62\0\x61\0r\0\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0$\0T\0\x65\0x\0t\0 \0L\0\x61\0\x62\0\x65\0l\0 \0T\0o\0o\0l\0\x62\0\x61\0r\0\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0*\0P\0\x61\0r\0t\0i\0\x63\0l\0\x65\0 \0V\0i\0\x65\0w\0 \0T\0o\0o\0l\0\x62\0\x61\0r\0\0\0\0\0\xff\xff\xff\xff\0\0\0\0\0\0\0\0\0\0\0\xe\0\x43\0S\0\x65\0r\0v\0\x65\0r\x1\0\0\x3\x86\xff\xff\xff\xff\0\0\0\0\0\0\0\0)"

[Window%20Position]
Window%20Height=683
Window%20State=8
Window%20Width=1000
Window%20X%20Position=164
Window%20Y%20Position=31

[Window%20Toolbar]
Visibility\Creator%20Geometry%20Toolbar=true
Visibility\Creator%20Physics%20Toolbar=true
Visibility\Geometry%20Mode%20Toolbar=true
```

# 4 脚本

## 4.1 图形
- 脚本
```bash
#!/bin/bash
#SBATCH -J edem
#SBATCH -p xahcnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=1
#SBATCH -c 32

export DISPLAY=vadmin26:16  # 修改端口号，图形桌面提交后，图形管理处可以获取

echo "yes" > input.txt

/work/home/acc90ozeg2/software/edem/EDEM_2018/bin/start-edem < input.txt
```
- 图形模板
写一个脚本作业启动可执行程序
```bash
#!/bin/bash
echo "yes" > input.txt
/work/home/aclf593sro/soft/edem/EDEM_2018/bin/start-edem < input.txt
```
![[Pasted image 20250924150348.png]]