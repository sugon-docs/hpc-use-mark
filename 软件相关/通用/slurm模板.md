## 模板 slurm

```bash
#!/bin/bash

#SBATCH -J test # 作业名
#SBATCH -p xahcnormal # 队列名
#SBATCH -N 3 # 节点个数
#SBATCH -n 64 # 总核数
#SBATCH --ntasks-per-node=60  # 单节点进程
#SBATCH --cpus-per-task=8  # 单进程线程
#SBATCH --comment={appType: STARCCM_PLUS, appVersion: 17.04.008-1789946212517220353, appVersionName: 17.04.008, appShopId: 1786991862996054017, submitWay: portal} 
#SBATCH -o %j.out
#SBATCH -e %j.err
#SBATCH --exclusive
#SBATCH --constraint="32core"
#SBATCH --time 72:00:00
#SBATCH --time 333-08:00:00
#SBATCH --mem=300G
#SBATCH --mem=0
#SBATCH -w node1 # 指定节点
#SBATCH -x h09r2n27,h09r2n28,h17r1n38,h17r1n39,h17r2n00,h17r2n01 # 排除节点
#SBATCH -x g04r1n[05-22],j01r4n[07-19],j02r1n0 

#SBATCH --constraint="32core"   # tyhcnormal、xahcnormal、wzhcnormal，三个混合队列的独占要注意，你本想独占单路，因为未加导致独占到双路
#SBATCH --constraint="64core"
#SBATCH --ntasks-per-node=2 # 每节点起多少进程，需要和 dcu 数量保持一致
#SBATCH --cpus-per-task=8 # 每进程使用多少核
#SBATCH --gres=gpu:2
#SBATCH --gres=dcu:2 

# 西安 必须配合独占一起使用
#SBATCH --comment={glibcVersion:2.31}  
#SBATCH --exclusive 

#xahcnormal   2.28, 2.31, 2.33 
#xahctest   2.28, 2.31, 2.33 
#xahdnormal   2.33 
#xahdtest   2.33 
#xahdexclu02   2.33


module purge
module load compiler/gcc/9.3.0 mpi/openmpi/openmpi-4.1.5-gcc9.3.0 

ulimit -s unlimited # 堆栈大小无限制
ulimit -l unlimited # 锁定内存无限制 仅 root 用户可设置

mpirun -np $SLURM_NPROCS rhoCentralFoam  -parallel  > "log.compile" 2>&1 & 

export LD_LIBRARY_PATH=/work/home/acyzhmmsjm/apprepo/parmetis/4.0.3-hpcx_gcc7.3.1/app/lib:$LD_LIBRARY_PATH

INCLUDE
PATH

module purge
module load 


图形提交脚本：/work/home/ac1yrz5h74/software/sent.slurm 
图形提交方式：sbatch sent.slurm 
软件激活环境：source /work/home/ac1yrz5h74/software/sentaurus2018/env.sh 
安装位置：/work/home/ac1yrz5h74/software/sentaurus2018

图形脚本中的DISPLAY，从图形管理处获取
然后进入linux桌面可以进行图形操作，老师

$SBATCH_JOB_NAME
$SLURM_JOB_ID
```
> 各位同事好，Glibc版本热切换功能已上线西北一区【西安】共享队列，供用户自由选择glibc版本，使用说明如下：
> 1. Glibc版本在作业运行期间切换，运行结束后还原，仅限在以下共享队列使用相应版本
> 1）CPU队列
> xahcnormal   2.28,2.31,2.33 
> xahctest   2.28,2.31,2.33 
> 2）DCU队列
> xahdnormal   2.33 
> xahdtest   2.33 
> xahdexclu02   2.33
> 2. 提交方式作业提交参数加上指定版本--comment={glibcVersion:2.31}和独占参数--exclusive，支持sbatch/srun/salloc/作业模版，如遇报错请仔细阅读错误提示
> 
> 预计10.15陆续上线至其他中心，请各位同事引导用户使用共享队列，感谢各位同事理解和支持~


### gpu

```bash
#!/bin/bash
#SBATCH -J test 
#SBATCH -p dzagnormal 
#SBATCH -N 1 
#SBATCH --cpus-per-task=8
#SBATCH --ntasks-per-node=2  
#SBATCH --gres=gpu:2  

module purge
source ~/miniconda3/bin/activate torch251 # 激活py环境
module load compiler/intel/2017.5.239
module load mpi/intelmpi/2017.4.239
module load nvidia/cuda/11.3

# 程序调用
# python xxx.py
```