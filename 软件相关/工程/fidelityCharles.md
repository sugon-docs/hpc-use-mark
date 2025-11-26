
# 脚本
```bash
#!/bin/bash
#SBATCH -J test # 作业名
#SBATCH -p xahcnormal # 队列名
#SBATCH -N 2 # 节点个数
#SBATCH --ntasks-per-node=4  # 进程

#source ~/charles.sh

module purge
module load compiler/gcc/9.3.0 mpi/openmpi/openmpi-4.1.5-gcc9.3.0
#module load compiler/devtoolset/7.3.1 mpi/hpcx/gcc-7.3.1
#export OMPI_HOME=/work/home/zhouhao/soft/openmpi/install
#export PATH=$OMPI_HOME/bin:$PATH
#export LD_LIBRARY_PATH=$OMPI_HOME/lib:$LD_LIBRARY_PATH

export CDS_LIC_FILE=5280@login05  # 已启动在login05节点
export PATH=$PATH:/work/home/zhouhao/apprepo/fidelityCharles_2024.2.1/bin/
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/work/home/zhouhao/apprepo/fidelityCharles_2024.2.1/lib/

/work/home/zhouhao/apprepo/fidelityCharles_2024.2.1/bin/charles_launch.sh -np 8 -mpi ompi -mpi_dir  /public/software/mpi/openmpi/openmpi-4.1.5 stitch.exe -i stitch.in  > stitch.log
```