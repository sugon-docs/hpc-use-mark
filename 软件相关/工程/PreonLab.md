# 安装

图形安装

```bash
# 有这个几个安装文件
PreonLab.4.3.3.Crack  PreonLab.4.3.3.Linux64

cd  PreonLab.4.3.3.Linux64

./PreonLabInstaller_Linux_v4_3_3
# 遵循图形安装路径等，装完到license 退出来

# 破解
cp PreonLab.4.3.3.Crack/fify2_SSQ.lic /xx/install/
cp PreonLab.4.3.3.Crack/PreonLab 4.3.3/* /xx/install/  
```

# 脚本

```bash
#!/bin/bash
#SBATCH -J test
#SBATCH -p xhacnormalc
#SBATCH -N 1
#SBATCH --ntasks-per-node=1
#SBATCH -c 32

###env
INPUT_FILE=60km.prscene
WDIR=`pwd`
PROCS=$SLURM_NPROCS
#export EXEC=/work/home/acx6ck6wd8/software/PreonLab/install/PreonLab
export PBS_NODEFILE=`generate_pbs_nodefile`
sort $PBS_NODEFILE | uniq -c | awk '{print $2}' > ./hostfile
HOST_FILE=./hostfile
export OMP_NUM_THREADS=$SLURM_CPUS_PER_TASK
export fifty2_LICENSE=/work/home/acx6ck6wd8/software/PreonLab/install/fify2_SSQ.lic
EXEC=/work/home/acx6ck6wd8/software/PreonLab/install/PreonCLI

###run
#$EXEC  -nogui  -np  $PROCS -input $input
$EXEC $INPUT_FILE 0s 10s --lBoost sim_threads_max
```