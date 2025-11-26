# 安装

- 手册：
https://www2.mmm.ucar.edu/wrf/users/docs/user_guide_v4/v4.4/users_guide_chap6.html#_Installing_WRFDA_for_1

```bash
module purge
module load compiler/intel/2017.5.239  mpi/intelmpi/2017.4.239
module load mathlib/netcdf/4.4.1/intel  mathlib/hdf5/1.12.0/intel-fortran-parallel mathlib/pnetcdf/1.12.0/intel

export PATH=/work/home/acx80w2p1k/apprepo/WRFDA/WRF-4.5/var/build:$PATH
```

```bash
cd /work/home/acx80w2p1k/apprepo/WRFDA/WRF-4.5
./configure wrfda
nohup ./compile all_wrfvar > "nohup_$(date +%Y%m%d_%H%M%S).out" 2>&1 &
```

# 脚本

```bash
#!/bin/bash
#SBATCH -J wrf #作业名称
#SBATCH -p wzhcnormal #队列名称
#SBATCH -N 1 #节点数量
#SBATCH --ntasks-per-node=32 #每节点核心数
#SBATCH --constraint="32core"

module purge
source /work/home/acx80w2p1k/apprepo/WRFDA/WRF-4.5/var/env.sh 

srun --mpi=pmi2 da_wrfvar.exe 
```