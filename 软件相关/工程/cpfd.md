# 安装

```bash
/public/home/jsyadmin/tanjj/paks/cpdf/25.0/source # 昆山 原始 包 license 也在
/public/home/jsyadmin/tanjj/paks/cpdf/25.0/build # 昆山 安装好的包

./barracuda_virtual_reactor-25.0.0-Linux.run
 
# licene启动
./rlm -ws 5980 -dlog log -c cpfd_SSQ.lic & 
```

# 脚本

```bash
#!/bin/bash
#SBATCH -J cpfd
#SBATCH -p wzhcnormal
#SBATCH -N 1
#SBATCH -n 64
#SBATCU --exclusive

export DISPLAY=i03r4n13:60

export CPFD_LICENSE=27015@login02

/work/home/litao1/soft/cpfd25-install/bin/barracuda
```