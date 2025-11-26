
## 1 安装
### 1.1 安装启动

```bash
# 安装好的16.1包
/public/home/jsyadmin/tanjj/paks/cfd++/build/cfd++16.1.zip # 昆山 # 可以打开图形
# 跨节点并行 要修改mpi 可以用的pmpi 在
/public/home/jsyadmin/tanjj/paks/cfd++/build/pmpi.zip # 昆山 # 就是abaqus安装里的 
## 修改 mcfdenv.sh 里
#======================================================se set up the following environment variables
export METACOMP_LICENSE_FILE=/public/home/acsugk0cw8/apprepo/cfdplus/16.1-none/app/license.dat
export METACOMP_HOME=/public/home/acsugk0cw8/apprepo/cfdplus/16.1-none/app
export MCFD_HOME=/public/home/acsugk0cw8/apprepo/cfdplus/16.1-none/app/mlib/mcfd.16.1
export MCFD_VERSION=16.1
#export MCFD_MAXMEM=Multi_CPU_Memory_Limit
export MCFD_MAXMEM=200G
#export MCFD_MAXMEM=2G
#export MCFD_PROCMEM=Single_CPU_Memory_Limit
#export MCFD_PROCMEM=2G
export MCFD_PROCMEM=200G
export MCFD_TCLTK=$METACOMP_HOME/mlib/mcfd.16.1/exec/gui_src
export MCFD_HTML=$METACOMP_HOME/mlib/mcfd.16.1/html
#export MCFD_GUIOPT1=AskMETACOMP1
#export MCFD_GUIOPT2=AskMETACOMP2
#export -n MCFD_TOGL
export MCFD_TOGL=yes
export TCL_LIBRARY=$METACOMP_HOME/mlib/tcltk8/lib/tcl8.0
export TK_LIBRARY=$METACOMP_HOME/mlib/tcltk8/lib/tk8.0
export PATH=$METACOMP_HOME/pmpi/bin:$PATH
export LD_LIBRARY_PATH=$METACOMP_HOME/pmpi/lib/linux_amd64/:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=$METACOMP_HOME/glib:$LD_LIBRARY_PATH
#======================================================
#Please set up the following path addition
export PATH=$METACOMP_HOME/mbin/mcfd.16.1:$PATH
export PATH=$METACOMP_HOME/mbin:$PATH
export PATH=$METACOMP_HOME/mlib/mcfd.16.1/exec:$PATH
if [ -z "$LD_LIBRARY_PATH" ]; then
export LD_LIBRARY_PATH=$METACOMP_HOME/glib
else
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:$METACOMP_HOME/glib
fi

## 脚本

#!/bin/bash                       
#SBATCH -J cfd++               #指定作业名称
#SBATCH -p xhhcnormal           #指定队列/分区名称
#SBATCH -N 2                  #指定节点数
#SBATCH --ntasks-per-node=30        #指定每节点的任务数量
#SBATCH --exclusive

module purge
source  /work/home/acu2nos4mt/apprepo/cfdplus/16.1-none/app/mcfdenv.sh
#source  /work/home/acu2nos4mt/apprepo/cfdplus/16.1-none/CFD++16.1/LINUX64/mcfdenv.sh.template
export LM_LICENSE_FILE=20780@10.21.120.9
module load compiler/gcc/9.3.0
#tometis kmetis  $SLURM_NPROCS
export PBS_NODEFILE=`generate_pbs_nodefile`
sort $PBS_NODEFILE | uniq -c | awk '{print $2":"$1}' > hosts
export MPI_REMSH=ssh
PROCS=$SLURM_NPROCS

export MPI_IC_ORDER="uDAPL:TCP" # 昆山加

mpirun -np $PROCS -hostfile ./hosts -srq /work/home/acu2nos4mt/apprepo/cfdplus/16.1-none/app/mlib/mcfd.16.1/exec/hpmpimcfd
#srun --mpi=pmi2  /work/home/acu2nos4mt/apprepo/cfdplus/16.1-none/app/mlib/mcfd.16.1/exec/hpmpimcfd



# 安装过程

## 创建METACOMP
将mcfdall.tar  glib hpmpi mbin  MPICH-3.1.4 OPENMPI-1.8.7 msetenv.bash msetup.bash 全部复制到METACOMP下
### 注意glib里将 cp /usr/lib/libGL* glib/ 全部复制过来

./msetup.bash 安装
### 最后报错bash: ./msetsgi.bash: No such file or directory 不用管

## 检查是否成功
cd METACOMP/mlib/mcfd.16.1 
./check.execs-list  #弹出 grep not 则成功

## 配置文件
cp mcfdenv.sh.template mcfdenv.sh
vim mcfdenv.sh,改变MAXMEM和PROCMEM 其中MAXMEM为总内存，PROCMEM 为一个进程的内存。 改成200G 2G就行

## 运行
### 图形
source /public/home/ac99fewrx5/apprepo/cfdplus/16.1-install/METACOMP/mcfdenv.sh
/public/home/ac99fewrx5/apprepo/cfdplus/16.1-install/METACOMP/mlib/mcfd.16.1/exec/mcfdgui

### 命令行
source /public/home/ac99fewrx5/apprepo/cfdplus/16.1-none/scripts/env.sh
tometis kmetis  $SLURM_NPROCS # 网格分区
HOST_STRING=`srun hostname | sort | uniq -c | awk '{print $2":"$1}' | tr '\n' ',' | sed 's/,$//'` ### slurm2pbs
mpirun -np $SLURM_NPROCS  -hostlist $HOST_STRING  hpmpimcfd 
```
#### 手册
![[cfd++_liunx_141安装说明_详细.docx]]
#### 运行手册

```bash
cd METACOMP/mlib/mcfd.16.1/html/index3.html
firefox index3.html
```

# 3  FAQ
- 内存限制
![[Pasted image 20250515224525.png]]
```bash
vim /work/home/ac0g1wbtzx/CFD++14.1_Linux64/CFD++14.1_Linux64/METACOMP/amd64/mcfdenv.sh 

修改其中
export MCFD_PROCMEM=200G
```
- mpi 问题
![[5bcd16c980f2a5d443a5c71418f4bb75.png]]
```bash
# 报错
ibv XXXX failed 

# 昆山区域要加
export MPI_IC_ORDER="uDAPL:TCP"
```

- 报libstdc++.so.5找不到的
```bash
export LD_PRELOAD=/public/home/acsugk0cw8/apprepo/cfdplus/16.1-none/app/glib/libstdc++.so.5
export LD_LIBRARY_PATH=/public/home/acsugk0cw8/apprepo/cfdplus/16.1-none/app/glib:$LD_LIBRARY_PATH
# 写到bashrc里要

# 昆山有
/public/home/jsyadmin/tanjj/paks/lib/libstdc++.so.6.0.34 
ln -s libstdc++.so.6.0.34  libstdc++.so.6
strings /public/home/jsyadmin/tanjj/paks/lib/libstdc++.so.6.0.34 | grep xxx
```