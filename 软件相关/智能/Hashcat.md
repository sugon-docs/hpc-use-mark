# 安装

```bash
# dcu不太清楚
# gpu cpu基本一致
# 安装
git clone https://github.com/hashcat/hashcat.git
# git clone https://kgithub.com/hashcat/hashcat.git # 国内镜像克隆
cd hashcat
make
make install

# 测试
hashcat -I  # 查看硬件信息
hashcat -b -m 22000  # 测试是否正常运行以及破解速度
```

# 脚本

```bash
#!/bin/bash
#SBATCH -J test     # 作业名称  %x获取作业名
#SBATCH -p xhhgnormal    # 队列名称
#SBATCH --ntasks-per-node 8       # 设置单节点核数
#SBATCH -N 1       #设置节点数量
#SBATCH --gres=gpu:1

/work/home/ssct0076t/soft/hashcat_v6.2.6/env.sh
export PATH=/work/home/ssct0076t/soft/hashcat_v6.2.6:$PATH
#source ~/dtk-25.04/env.sh
#./hashcat -a 3 -m 22100 -D 1 -d 2 --custom-charset1=?d test.txt --increment --increment-min 10 --increment-max 10 zhyl?1?1?1?1?1?1
./hashcat -a 3 -m 22100 test.txt --increment --increment-min 10 --increment-max 10 zhyl?1?1?1?1?1?1
```

```c
// test.txt 内容
$bitlocker$1$16$e6beff172e479b099581f1912ea69e1a$1048576$12$90cfcf6131cedb0103000000$60$a4eca4ed2f5b4a18d09d4723006d37037e033f3f08bf2930f147a01165bfe8352ae80bc3bca5c7e0ee3a2f71f9f307458abef6ccc6a1a76a54c509db
```