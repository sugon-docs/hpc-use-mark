## ROCKY

### 包

```bash
/public/home/jsyadmin/liucheng/softpack/ROCKY_DEM # 昆山

cd /public/home/aclt99e3fa/softwares/rocky2023/ESSS.Rocky.2023.R1.0.Linux.x64-SSQ/Linux
tar -xjvf rocky-bin-23.1.0-linux64-ssq.tar.bz2 
```

### 破解启动

```bash
# 2022及以下 
/work/home/jsyadmin/L4/dwj/2025ans/v251/rocky/Rocky # 启动
crack下覆盖
选择文件esss_flex_SSQ.lic文件 # Crack下

# 2023以及以上
/work/home/jsyadmin/L4/dwj/2025ans/v251/rocky/Rocky # 启动
crack下覆盖
选取ansys license所在节点

# rocky只支持单节点
```
- license
 ![[Pasted image 20250627152627.png]]
 
![[Pasted image 20250627152655.png]]
![[Pasted image 20250627152723.png]]
### 耦合 fluent

```bash
# 2025版自动耦合

# 其他版本设置以下路径 写入bashrc里
export AWP_ROOT221=/work/home/acby8x9bz4/app/v221
export ANSYS231_DIR=/xxx/software/ansys_inc/v231 
```

#### 双向耦合计算
![[Pasted image 20250711173020.png]]
![[Pasted image 20250711173029.png]]
![[Pasted image 20250713170500.png]]

跨节点要设置节点名和核数，启动 fluent 要 `-pib -mpi=intel2018`
### 模板推荐使用 workbench 的

```bash
# 2025后都在大的安装包里了，license公用

/work/home/jsyadmin/L4/dwj/2025ans/v251/rocky/Rocky # 启动
export LD_PRELOAD=/../libstdc++.so.6 # 缺东西的
```

# 脚本

- 图形
```bash
#!/bin/bash
#SBATCH -J rocky-gui
#SBATCH -p wzhctest
#SBATCH -N 1
#SBATCH --ntasks-per-node=32
#SBATCH --comment={glibcVersion:2.31}  
#SBATCH --exclusive

# 你自己用的 conda 环境（如果不需要就注释掉）
# source ~/miniconda3/bin/activate mfix-25.3

# 使用你看到的 VNC 显示号（12 是例子，可改）
export DISPLAY=i03r4n16:7
export MESA_GL_VERSION_OVERRIDE=3.3 # GLSL版本问题

# 切换到工作目录
cd /work/home/lvhlong/rocky_work

# 启动 Rocky GUI
/work/home/lvhlong/software/ansys2025r2/v252/rocky/bin/Rocky
```

> 缺库：`/work/home/jsyadmin/L4/dwj/libtirpc-1.3.6/install/lib` # 西安
> `/work/home/jsyadmin/L4/dwj/libtirpc-1.3.6/libtirpc-ok.zip`    
> 不识别的话可以放在 fluent 得启动 lib 库下 `/work/home/lvhlong/software/ansys2025r2/v252/fluent/lib/lnamd64`
> 
- 脚本
```bash
#!/bin/bash
#SBATCH -J mfix-vnc
#SBATCH -p wzhctest
#SBATCH -N 1
#SBATCH --ntasks-per-node=32
#SBATCH --comment={glibcVersion:2.31}
#SBATCH --exclusive

#映射
#export DISPLAY=vncserver01:12

#启动命令
/work/home/lvhlong/software/ansys2025r2/v252/rocky/bin/Rocky \
    --headless \
    --ncpus=32 \
    "./free_settle_1mm_liuti_couple.rocky"
```
