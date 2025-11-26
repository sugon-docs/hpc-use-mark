# 安装

- 高版本
```bash
# 用sif环境
./STAR-CCM+2510_007_linux-x86_64-2.28_clang17.0.aol -i console  -DINSTALL_LICENSING=false 
```

```bash
# 19.06 镜像 昆山 
/public/home/jsyadmin/tanjj/paks/starccm/build/1906/1906_2.sif

# 20.06 安装好的 昆山
/public/home/jsyadmin/tanjj/paks/starccm/build/2006

# 测试算例 昆山 ~/apprepo/starccmplus/19.02.009-null/case/test.sim
rayfile-c -a ksefile.hpccube.com -P 65245 -u jsyadmin -w 9380ec0f4bf330072f-4360-4ec3-a813-76d5f1f67556 -no-meta -symbolic-links follow -retry 10 -retrytimeout 30 -o download -s '/apprepo/starccmplus/19.02.009-null/case/test.sim' -d .


```
## starccm

### 网格配比

```bash
10-20w 每个核心
```

### 一些指令

```bash
-mesa -mpidriver openmpi  -jvmargs -Xmx20g  -mpiflags "-mca btl '^vader,tcp,openib,uct'  -mca pml ucx -x UCX_TLS=self,sm,rc_x"

```

> 🔧 ​**​参数详细解析​**​

1. ​**​`-mca btl '^vader,tcp,openib,uct'`​**​
    
    - ​**​功能​**​：禁用指定的MPI传输模块（BTL, Byte Transfer Layer）。
        
        - `vader`：Linux共享内存通信（可能因内存竞争导致性能下降）。
            
        - `tcp`：TCP/IP网络传输（延迟高，适合备用而非高性能场景）。
            
        - `openib`/`uct`：InfiniBand驱动（若集群未正确配置RDMA，可能引发通信失败）。
            
    - ​**​目的​**​：排除低效或不稳定的传输方式，强制使用更优选项（如UCX）
        
        
2. ​**​`-mca pml ucx`​**​
    
    - ​**​功能​**​：指定使用​**​UCX（Unified Communication X）​**​作为点对点通信层（PML）。
        
    - ​**​优势​**​：UCX专为高性能网络（如InfiniBand、RoCE）优化，支持RDMA（远程直接内存访问），显著降低延迟并提升吞吐量
        
        。
        
3. ​**​`-x UCX_TLS=self,sm,rc_x`​**​
    
    - ​**​功能​**​：设置UCX的传输层协议（TLS, Transport Layer Selector）。
        
        - `self`：进程内通信（同一节点内）。
            
        - `sm`：共享内存通信（跨进程同节点）。
            
        - `rc_x`：基于可靠连接（RC）的InfiniBand传输（跨节点）。
            
    - ​**​目的​**​：精细化控制通信路径，避免冗余协议栈，提升效率
        

> ​应用场景与作用​​

- ​**​高性能计算集群​**​：
    
    在配备InfiniBand/RDMA的HPC集群中（如Azure A8/A9实例
    
    ），此配置可最大化利用硬件加速通信，减少CPU开销，尤其适用于大规模网格（>1亿单元）的CFD模拟
    
- ​**​兼容性调优​**​：
    
    排除`openib`和`tcp`可避免因驱动未安装或网络配置错误导致的MPI初始化失败（常见于混合集群环境）
    
- ​**​性能优化​**​：
    
    UCX相比传统BTL模块，在InfiniBand上可实现​**​80%+的并行效率​**​（即使扩展至数千核）


### mpi 切换

```bash
# 加载openmpi intel不推荐
module purge
module load compiler/devtoolset/7.3.1 mpi/openmpi/4.1.1/gcc-7.3.1

# 要设置下 $OPENMPI_DIR 这个变量
export OPENMPI_DIR=/work/share/caeri_dom/software/openmpi/openmpi
export PATH=/$OPENMPI_DIR/bin:$PATH
export LD_LIBRARY_PATH=$OPENMPI_DIR/lib:$LD_LIBRARY_PATH

## 1402 汽车研究院的openmpi在 /work/home/jsyadmin/tanjj/share/openmpi.zip 乌镇

# 启动参数调用
-mpi openmpi  # 等效 -mpidriver openmpi 
```


```bash
export I_MPI_FABRICS=shm:ofa  # intelmpi通信设置
```
###  报错
-  图形输出卡死

  - LLVM ERROR: Do not know how to split the result of this operator! 
   -  java. lang. OutOfMemoryError: Java heap space ![[4fb1be947188f8a9f604cb0fe6497e2f.png]]


```bash
vim .portal/job_portal.var  #中第二行加入 或模板中命令行参数添加
GAP_CMD_OPTION_WEB='-mesa -mpidriver openmpi -jvmargs -Xmx80g' 

# -mpi 与 -mpidriver 等效
# -rr 是 ​​重启（Restart Run）​​ 选项，用于从之前的 ​​检查点文件（Checkpoint File）​​ 恢复仿真。它会加载最后一次保存的 .sim 或 .mdl 文件继续计算。
# -jvmargs -Xmx20g 是设置Java虚拟机最大堆内存为20GB  不能超过节点内存的一半

```

-  载入 sim 异常
![[Pasted image 20250515150002.png]]
![[Pasted image 20250515150228.png]]
```bash
#目录下有中文特殊字符，包括得检查home目录
```

-  starccm+ 15.02.007 西安多节点报错：oversized message may imply too few parallel processes are being used
![[Pasted image 20250530133740.png]]

- 作业计算保存报错

```bash
Maximum Temperature limited to 2000 on 2 faces on wall-disable-prism
          6000   5.844480e-04   4.642958e-06   4.223778e-05   3.850826e-05   1.060195e+00   3.895600e-09   1.133139e-07      1.000000e+00   4.543310e+02   2.545623e+02   3.519360e+02   4.452091e+02   3.848454e+02   3.324304e+02       4.989065e+04             6.946475e+01             6.827270e+01     8.999028e+01                  1.110740e+04                    2.259624e+04   9.770112e+02          -1.464697e+00        1.105696e+00       2.733956e+00
Auto save: /work/home/ac0wb5ny6n/A_CT/test/new-mesh-daojiao-retai-power-four-ture@06000.sim
Saving: /work/home/ac0wb5ny6n/A_CT/test/new-mesh-daojiao-retai-power-four-ture@06000.sim...Error: basic_filebuf::underflow error reading the file: No such file or directory


# 原始文件.sim被修改移动了
```
 
 - mpi

```bash
 pml_ucx.c:309  Error: Failed to create UCP worker
 
 可能是mpi版本问题，考虑切换外部openmpi，在starccm+脚本或模板高级参数添加：

>>>export MPI_ROOT= /public/software/mpi/openmpi/openmpi-4.1.5 #自己找到集群的openmpi路径

>>>export OMPI_HOME=$MPI_ROOT

>>>export OPENMPI_DIR=$MPI_ROOT
```


```bash
思路/解决：starccm+自带mpi类型有intelmpi、platform、openmpi三类，在跨节点多核心运行时，通常因为mpi选择错误出现报错。目前没有总结出严谨唯一的答案，根据经验大致列出方法。

1、starccm+13及更早版本

①不支持openmpi，可使用自带intelmpi，命令行参数添加：

>>>-mpidriver intel

②或者使用自带platform，命令行参数添加：

>>>-mpidriver platform -mppflags "-srq"

2、starccm+14到starccm+17

①starccm+14-17默认是intelmpi启动，可能出现各类并行问题，内置openmpi与集群IB兼容又较差，因此通常选择module或者用户自己安装的openmpi（注意四starccm+14是openmpi3）

module purge
module load compiler/gcc/9.3.0 mpi/openmpi/openmpi-4.1.5-gcc9.3.0 

export OMPI_HOME=/public/software/mpi/openmpi/openmpi-4.1.5 
export OPENMPI_DIR=/public/software/mpi/openmpi/openmpi-4.1.5 
-mpidriver openmpi

②当然starccm+15.02.007也可以使用自带的platform来解决并行问题，命令行参数添加：

>>>-mpidriver platform -mppflags "-srq"

3、starccm+17、18

starccm+17、18乌镇用户出现并行问题，仍然考虑采用module或者用户自己安装的openmpi，有时需要添加通信参数

>>>module purge

>>>module load compiler/gcc/9.3.0

>>>module load mpi/openmpi/4.1.5/gcc-9.3.0

>>>export OMPI_HOME=/public/software/mpi/openmpi/openmpi-4.1.5 

>>>export OPENMPI_DIR=/public/software/mpi/openmpi/openmpi-4.1.5 

>>>-mpidriver openmpi -mppflags "--mca btl self,tcp --mca btl_tcp_if_include ib0"（不到万不得已不加）

4、starccm+19（也叫240xxx版本，参考知识库脚本）

①为解决glibc问题，现在通常用singularity打开，可选择openmpi

②或者也可选择自带intelmpi，在singularity exec启动后添加

>>>export FI_PROVIDER=verbs #是用于设置 libfabric（一种用于高性能网络的通信中间件库）的环境变量，指定使用 verbs 作为底层网络提供者（provider）

>>>-mpidriver intel
```

-   启动错误
```bash
-mpi openmpi40  #乌镇starccm1806启动推荐
```

- 通信
```bash
# 报错 dc_mlx5.c:1155 UCX ERROR dc interface can have at most 15 dcis (requested: 16)

export UCX_TLS=rc, sm
```

- 图形查看日志
```bash
# 图形日志查看
# 计算节点上
/tmp/output1762335793903

# 或者
~/.star-20.02.007/var/log/
```

- server 链接  
```bash
# 链接server
打开的界面可以实时监控，可以暂停保存，修改相关设置再保存模型。但是，若暂停之后重新计算，虽然计算所占用的资源还是最开始的后台作业节点，但此时关闭图形界面，则会停掉server，计算终止。因此建议暂停修改设置后，保存新的sim文件，重新提交后台计算作业
```
![[Pasted image 20251106135920.png]] ![[Pasted image 20251106135929.png]]

- 渲染

```bash
-mpi intel -rr dcv3
```

- java 运行
```bash
#!/bin/bash
#SBATCH -J XJrotorV00A00
#SBATCH -p tyhcnormal
#SBATCH -N 2
#SBATCH --ntasks-per-node=60
#SBATCH --exclusive

WDIR=`pwd`        #获取当前目录
cd $WDIR

export CDLMD_LICENSE_FILE="/work/home/acvu1y291c/software/starccm-install/18.04.009/license.dat"

APP="/work/home/acvu1y291c/software/starccm-install/18.04.009/STAR-CCM+18.04.009/star/bin/starccm+ -power -mesa"

NP=$SLURM_NPROCS

INPUT_FILE=N5000RPMV00A00@00480.sim
SCRIPT_FILE=timestepANDstop.java,run  # run 是相当于 又传了一个指令给starccm+可执行文件

NNODE=`srun hostname |sort |uniq | wc -l`
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE

#$APP $INPUT_FILE -batch -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver  openmpi  2>&1
$APP $INPUT_FILE -batch $SCRIPT_FILE -machinefile $HOSTFILE -np $NP -rsh ssh  -mpi openmpi40  2>&1

```

- glibc
```bash
# 西安
#SBATCH --comment={glibcVersion:2.28}  
#SBATCH --exclusive 
starccm1906  不要用高版本的 -mpi openmpi40
starccm2006 -mpi openmpi 
```

![[b88a5219dafebcfca7bfcad7d2792292.png]]
```bash

-mpi openmpi40 -fabric UCX # 图形 走ucx通信

# env里加下面这些识别不了 得加在starccm+可执行文件里
export OMPI_MCA_pml=ucx
export OMPI_MCA_btl=^openib
export UCX_TLS=rc,dc_x,ud
export UCX_NET_DEVICES=mlx5_0:1
export UCX_IB_PCI_BW=mlx5_0:100Gbs
```
### starccm 启动调试

- 启动前的检查文件
```bash
/public/home/jsyadmin/apprepo/starccmplus/19.02.009-null/app/19.02.009-R8/STAR-CCM+19.02.009-R8/star/bin/starenv
```

# 脚本

## 命令行

- 普通
```bash
#!/bin/bash
#SBATCH -J test   #作业名
#SBATCH -p pxxx  #队列名
#SBATCH -N 4    #节点数
#SBATCH -n 512  #进程数
#SBATCH --ntasks-per-node=128     #每节点进程数
#SBATCH --ntasks-per-socket=16     #一般取每节点进程数的八分之一
#SBATCH --cpus-per-task=1     #每进程占用核心数
##SBATCH --exclusive
#SBATCH -o %j.out
#SBATCH -e %j.err

date

WDIR=`pwd`        #获取当前目录
cd $WDIR

APP="/public/home/acervf3klc/soft/16.04.007/16.04.007/STAR-CCM+16.04.007/star/bin/starccm+ -power -mesa"   #软件安装路径
INPUT_FILE=$WDIR/test_0829.sim      #输入sim文件
#SCRIPT_FILE=$WDIR/ccm.java    #输入java文件
##################################################################

NP=$SLURM_NPROCS
NNODE=`srun hostname |sort |uniq | wc -l` 
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log 
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE

module purge
###############运行主程序
#$APP $INPUT_FILE -batch $SCRIPT_FILE -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver intel  2>&1
$APP $INPUT_FILE -batch -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver intel -fabricverbose 2>&1  
```

- 镜像
```bash
# 镜像版 /work/home/jsyadmin/L4/dwj/2502/bin/starccm.sif 西安

#!/bin/bash
#SBATCH -J STAR
#SBATCH -p xahcnormal
#SBATCH -N 4
#SBATCH --ntasks-per-node=20
#SBATCH --exclusive
WDIR=$PWD
##输入文件
INPUT_FILE=$WDIR/Water-HVAC-106-M2-20250226-base-SPH.sim
##执行命令
WDIR=`pwd`
cd $WDIR

module purge
module load  singularity/3.7.3

APP=/opt/Siemens/20.02.007-R8/STAR-CCM+20.02.007-R8/star/bin/starccm+
export CDLMD_LICENSE_FILE=/opt/Siemens/license.dat
NP=$SLURM_NPROCS
NNODE=`srun hostname |sort |uniq | wc -l`
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE
singularity exec /work/home/jsyadmin/L4/dwj/2502/pytorch_2.sif $APP -licpath /opt/Siemens/license.dat $INPUT_FILE -batch $SCRIPT_FILE -machinefile $HOSTFILE -np $NP -rsh ssh   -mpidriver intel -fabricverbose 

 -locale zh #中文

# 写到bashrc里
export I_MPI_PIN_DOMAIN=numa
export ANS_NODEPCHECK=1
module load  singularity/3.7.3

```

```bash
/work/home/jsyadmin/L4/dwj/2502/2506.sif  # 西安 
/work/home/jsyadmin/L4/dwj/2502/2506.dat   # license
/work/home/jsyadmin/L4/dwj/2502/starccm+2506 # 启动脚本

# 脚本如下
#!/bin/bash

module load singularity/3.7.3
singularity exec /work/home/jsyadmin/L4/dwj/2502/2506.sif  /opt/Siemens/20.04.007-R8/STAR-CCM+20.04.007-R8/star/bin/starccm+ -licpath /work/home/jsyadmin/L4/dwj/2502/2506.dat -mpidriver intel "$@"



module load singularity/3.7.3
singularity exec  /work/home/acerwhubx5/apprepo/starccmplus/19.06.008-none/app/1906_2.sif /opt/Siemens/19.06.008/STAR-CCM+19.06.008-R8/star/bin/starccm+ -licpath /work/home/acerwhubx5/apprepo/starccmplus/19.06.008-none/app/license.dat -mpidriver intel "$@"
```
## 图形启动

- 普通的

```bash
#!/bin/bash
#SBATCH -J STAR    #作业名
#SBATCH -p hfacnormal01 #队列名
#SBATCH -N 1    #节点数
#SBATCH -n 64  #进程数
#SBATCH --ntasks-per-node=64     #每节点进程数
#SBATCH --ntasks-per-socket=8     #一般取每节点进程数的八分之一
#SBATCH --cpus-per-task=1     #每进程占用核心数
#SBATCH --exclusive
#SBATCH -o %j.out
#SBATCH -e %j.err

export DISPLAY=imgr8:65     #软件图形窗口号

date

WDIR=`pwd`        #获取当前目录
cd $WDIR

APP="/public/home/acervf3klc/soft/16.02.008/16.02.008/STAR-CCM+16.02.008/star/bin/starccm+ -power -mesa"   #软件安装路径
#INPUT_FILE=$WDIR/test.sim      #输入sim文件
#SCRIPT_FILE=$WDIR/HPCrun.java    #输入java文件
##################################################################

NP=$SLURM_NPROCS
NNODE=`srun hostname |sort |uniq | wc -l` 
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log 
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE

export I_MPI_FABRICS=shm:dapl
export I_MPI_DAPL_UD=enable
export I_MPI_FALLBACK_DEVICE=disable
export I_MPI_DAPL_UD_PROVIDER=ofa-v2-mlx5_0-1u

###############运行主程序
#$APP $INPUT_FILE -batch $SCRIPT_FILE -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver intel  2>&1
#$APP $INPUT_FILE -batch -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver intel 2>&1  
$APP  -machinefile $HOSTFILE -np $NP -rsh ssh  -mpidriver intel

```

- 镜像版本

```bash
# 写到bashrc里
export I_MPI_PIN_DOMAIN=numa
export ANS_NODEPCHECK=1
module load  singularity/3.7.3
```

```bash
#!/bin/bash
# 可执行脚本
export PATH=/public/software/apps/apptainer/1.2.4/bin:$PATH

/public/software/apps/apptainer/1.2.4/bin/singularity exec  /work/home/acvex07mam/apprepo/starccmplus/20.02.007-R8/app/pytorch_2.sif /opt/Siemens/20.02.007-R8/STAR-CCM+20.02.007-R8/star/bin/starccm+  -licpath /opt/Siemens/license.dat  "$@"
```


