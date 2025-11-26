

# 使用

- 图形  启动程序 `/xxx/Lumerical_Suite_2023_R1_CentOS/lumerical/v231/bin/fdtd-solutions`
![[Pasted image 20250912112852.png]]
```bash
# 修改mpi方式如图 ，启动进程要改
## 跨节点 第一可以add节点名称，第二可以在custom那边选slurm方式，类似matalb跨节点并行的方式 
# custom改启动路径 这边，openmpi的都有 
/work/home/ac6jy1wytr/soft/fdtd-2023/install/Lumerical_Suite_2023_R1_CentOS/lumerical/v231/bin/fdtd-engine-ompi-lcl

# mpirun的路径 可以用集群的
export LM_LICENSE_FILE=27000@license01
module purge
#module load compiler/devtoolset/7.3.1  mpi/hpcx/2.11.0/gcc-7.3.1 
module load compiler/intel/2017.5.239    mpi/intelmpi/2017.4.239
export I_MPI_ROOT=/public/software/mpi/intelmpi/2017.4.239
export PATH=/public/software/mpi/intelmpi/2017.4.239/intel64/bin:$PATH
export LD_LIBRARY_PATH=/public/software/mpi/intelmpi/2017.4.239/intel64/lib:$LD_LIBRARY_PATH
export INCLUDE=/public/software/mpi/intelmpi/2017.4.239/intel64/include:$INCLUDE
```

- 脚本
```bash
#!/bin/bash
#SBATCH -J fdtd
#SBATCH -p wzhctest
#SBATCH -N 1
#SBATCH --ntasks-per-node=8

source /work/home/ac6jy1wytr/apprepo/fdtd/2016-none/scripts/env.sh
WORK_DIR=`pwd`    ##获取当前路径


INPUT_FILE=nanowire.fsp       ##输入文件

machines=""
for i in $(scontrol show hostnames=$SLURM_JOB_NODELIST); do
        machines=$machines:$i:$SLURM_NTASKS_PER_NODE
done
machines=${machines#:*}  ##获取节点列表

echo $machines 
module purge
module load compiler/intel/2017.5.239    mpi/intelmpi/2017.4.239
mpirun fdtd-engine-impi-lcl  $INPUT_FILE

```