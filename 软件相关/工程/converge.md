# 安装

# 报错

## 

# 脚本

- 图形启动
```bash
#!/bin/bash
#SBATCH -J converge
#SBATCH -N 1 #节点数
#SBATCH --ntasks-per-node=2   #每个节点核数 
#SBATCH -p xhacnormalc  #队列名

module purge
source /work/home/ac820ioche/apprepo/converge/3.1-none/scripts/env.sh
export DISPLAY=vvnc01:2
export MESA_GL_VERSION_OVERRIDE=3.3
export CONVERGE_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin
export PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin:$PATH

/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin/CONVERGE_Studio -o
```

- intel
```bash
#!/bin/bash
#SBATCH -J HY-converge-2MPa-ELSA-V30-1
#SBATCH -p xahcnormal
#SBATCH -N 8 
#SBATCH -n 512

module purge
source /work/home/wangning1022/apprepo/converge/3.0-none/scripts/env.sh

export I_MPI_SHM_HEAP_VSIZE=512
export UCX_TLS=all
export I_MPI_ADJUST_ALLREDUCE_COMPOSITION=0
export I_MPI_ADJUST_ALLREDUCE_NETWORK=2
export UCX_MEM_EVENTS=n

srun --mpi=pmi2 /work/home/wangning1022/apprepo/converge/3.0-none/app/Convergent_Science/CONVERGE/3.0.17/bin/converge-intelmpi >converge_4.log
```

- hpcx 稳定点
```bash
#!/bin/bash
#SBATCH -J CVG
#SBATCH -N 2
#SBATCH --ntasks-per-node=64
#SBATCH -p wzhctest
#SBATCH --exclusive

module purge
module load compiler/devtoolset/7.3.1 mpi/hpcx/2.11.0/gcc-7.3.1
export LD_LIBRARY_PATH=/public/software/compiler/intel-compiler/2021.3.0/mkl/lib/intel64:$LD_LIBRARY_PATH
export RLM_LICENSE=50531@license01
#export RLM_LICENSE=/work/home/ac9tnidq96/soft/converge2.3/v2.3/license/license.lic

export CONVERGE_ROOT=/work/home/acynjupr6q/software/Convergent_Science/CONVERGE/3.0.28
export CONVERGE_PATH=$CONVERGE_ROOT/bin
export PATH=$CONVERGE_PATH:$PATH

export I_MPI_SHM_HEAP_VSIZE=512
export UCX_TLS=all
export I_MPI_ADJUST_ALLREDUCE_COMPOSITION=0
export I_MPI_ADJUST_ALLREDUCE_NETWORK=2
export UCX_MEM_EVENTS=n
#mpirun /work/home/acynjupr6q/software/Convergent_Science/CONVERGE/3.0.28/bin/converge-intelmpi
mpirun /work/home/acynjupr6q/software/Convergent_Science/CONVERGE/3.0.28/bin/converge-hpcx                                                                                           
```


- 图形启动
	- 图形脚本
```bash
#!/bin/bash
#SBATCH -J converge
#SBATCH -N 2 #节点数
#SBATCH --ntasks-per-node=32   #每个节点核数 
#SBATCH -p xhacnormalc  #队列名

module purge
#module load  mpi/intelmpi/2020.1.217 compiler/intel/2020.1.217
module load compiler/devtoolset/7.3.1 mpi/hpcx/gcc-7.3.1
export DISPLAY=vvnc01:2
export MESA_GL_VERSION_OVERRIDE=3.3
export RLM_LICENSE=50531@license01
#source /work/home/ac820ioche/apprepo/converge/3.1-none/scripts/env.sh
#export LD_LIBRARY_PATH=/public/software/compiler/intel-compiler/2021.3.0/mkl/lib/intel64:$LD_LIBRARY_PATH
#export RLM_LICENSE=/work/home/ac9tnidq96/soft/converge2.3/v2.3/license/license.lic
export CATALYST_IMPLEMENTATION_NAME=paraview
export LD_LIBRARY_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/CONVERGE/3.1.8/lib64/HPCX:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/CONVERGE/3.1.8/lib64:$LD_LIBRARY_PATH
#export CONVERGE_ROOT=/work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/CONVERGE/3.1.8
#export CONVERGE_PATH=$CONVERGE_ROOT/bin
#export PATH=$CONVERGE_PATH:$PATH
#source /work/home/ac820ioche/apprepo/converge/3.1-none/scripts/env.sh
#export DISPLAY=vvnc01:2
#export MESA_GL_VERSION_OVERRIDE=3.3
export CONVERGE_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin
export PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin:$PATH

/work/home/ac820ioche/apprepo/converge/3.1-none/app/v3.1/bin/CONVERGE_Studio -o
```

	- 软件启动设置要

```bash
# 还需要在这个软件自带的启动环境里添加些环境，slurm脚本里不识别
vim /work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/Environment/scripts/CONVERGE/CONVERGE-HPCX/3.1.8.sh

export CATALYST_IMPLEMENTATION_NAME=paraview
export LD_LIBRARY_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/CONVERGE/3.1.8/lib64/HPCX:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=/work/home/ac820ioche/apprepo/converge/3.1-none/app/Convergent_Science/CONVERGE/3.1.8/lib64:$LD_LIBRARY_PATH
```

	-  图形设置 如果mpitype没刷新出来 就点下ok再打开就有了

![[Pasted image 20251030144614.png]]

```bash
.cvg 用来打开图形
输入文件是.in .dat 
输出的有 .echo   outputs_original  .out  等
```
# 报错

- license
```bash
# 2.4 # 本地许可
export RLM_LICENSE=/public/home/acqyqobnfj/apprepo/converge/2.4-none/app/v2.4/license/license.lic

# 3.0 3.1 
export RLM_LICENSE=50531@license01
```

