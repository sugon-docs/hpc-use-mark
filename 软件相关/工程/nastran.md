
## nastran

### 启动 license

```bash
# 2017
nohup /work/share/xinj002/sourcecode/nastran2017/Helium/lmgrd -c license.dat -l 20250624.log &

# 2020
文件破解

export SPLM_LICENSE_SERVER=/work/home/llb2660481743/apprepo/nastran/2020-none/app/nastran/ugslmd_SSQ.dat
```

### 脚本

不指定 memory 的话，默认节点的一半内存

```bash
#!/bin/sh
#SBATCH -N 1
#SBATCH -J nastran-job    ##指定作业名
#SBATCH --ntasks-per-node=1
#SBATCH --cpus-per-task=16 # 跑线程的
#SBATCH -p xhacnormalc    ##队列 
#SBATCH --exclusive

export MSC_LICENSE_FILE=27500@login01
nastran="/work/share/xinj002/apprepo/bin/nast20171"

unset SLURM_GTIDS
export KMP_AFFINITY=disabled

WDIR=`pwd`

data_file=$WDIR/plate_hole_static.bdf
hostlist=`scontrol show hostname | paste -d: -s`
echo $hostlist
mkdir -p $WDIR/tmp/scratch
$nastran jid=$data_file smp=$SLURM_CPUS_PER_TASK  hosts=$hostlist  memory=5G memorymax=100G   sdir=$WDIR/tmp/scratch
# dmp=$SLURM_NTASKS smp=$SLURM_CPUS_PER_TASK    memory=5G memorymax=6000G
```