# 使用

- 启动
```bash
/work/home/jsyadmin/L4/kunshan/dwj/CST2022/CST/cst_design_environment_gui # 图形  
/public/home/jsyadmin/liucheng/app/CST/env.sh  # 昆山 export CSTD_LICENSE_FILE=27000@license02

# 海光上这个几个环境要加
export CST_SYSTEM_OVERWRITE="1 2 32 2"
export CST_MPI_SC_NSLOOKUP=0
export CST_MPI_VERSION=IMPI2017U3


/work/share/acdgoqqo1y/apprepo/cst/2022.5-none/app/CST/LinuxAMD64/cst_design_environment_AMD64 # 非图形
/work/share/acdgoqqo1y/apprepo/cst/2022.5-none/scripts/env.sh # 太原
```

- dcu # 只支持 time_domain 求解器
![[Pasted image 20250929172653.png]]
# 脚本

- 需要在图形里设置好算例，才能用 dcu
```bash
#!/bin/bash
#SBATCH -J cst
#SBATCH -p kshdnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=8
#SBATCH --gres=dcu:1

module purge
module load compiler/dtk/25.04.1
source  /public/software/compiler/rocm/dtk-25.04.1/env.sh 
export LD_PRELOAD=/public/home/hfcas_user14/apprepo/cst/2022-none/app/libamdocl64.so
export CST_HWACC_ALLOW_UNVERIFIED_HARDWARE=1 # 使用不在支持列表但同架构 AMDGPU 卡
export HIP_PLATFORM_COMPATIBILITY=amd # 将 HYGON DCU 的 Platform Name 识别为 AMD
export CST_SYSTEM_OVERWRITE="1 2 32 2"
export CST_MPI_SC_NSLOOKUP=0
export CST_MPI_VERSION=IMPI2017U3
export CSTD_LICENSE_FILE=27001@license02

export TMPDIR=~/tmp/cst-temp
mkdir -p $TMPDIR
export XDG_RUNTIME_DIR=~/tmp/cst-runtime
mkdir -p $XDG_RUNTIME_DIR


INPUT_FILE=4-4_RCS.cst

WORK_DIR=`pwd`    ##获取当前路径
cd $WORK_DIR
PROCS=$SLURM_NPROCS  ##总进程数
srun hostname|sort|uniq -c|awk '{print $2}' > ./hostfile_$PROCS
HOST_FILE=./hostfile_$PROCS

/public/home/hfcas_user14/apprepo/cst/2022.5/CST/LinuxAMD64/cst_design_environment_AMD64 --m -r -numthreads=$SLURM_NTASKS_PER_NODE -dump 1 --withmpi --machinefile  $HOST_FILE  $INPUT_FILE  -withgpu=1
```

# 报错

- 求解器选择不对
```bash
/../Result/Model.log 里
```
![[Pasted image 20251015115624.png]]
![[Pasted image 20251015115948.png]]