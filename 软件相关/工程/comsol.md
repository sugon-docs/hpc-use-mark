# 1 安装


# 2 使用
## 2.1 与 matlab 联合调用

![[comsol与matlab联用 1.docx]]

```bash
# 模板提交 命令行参数加
# -mpibootstrap slurm mphserver  matlab
slurm mphserver  matlab # -mpibootstrap 这个默认有了
```

- 指令大全

https://doc.comsol.com/5.5/doc/com.comsol.help.comsol/comsol_ref_running.29.30.html#686067

- method
```bash
$APP batch  -nn $nn  -nnhost $nnhost   -np $np   -mpmode owner -methodcall ${METHOD_TAG}  -inputfile ${INPUTFILE} -outputfile ${OUTPUTFILE} -batchlog ${LOGFILE} -blas mkl -prefermph  -mpidebug 10  -tmpdir "$WDIR/tmp$SLURM_JOB_ID"    -prefsdir  "$WDIR/tmp$SLURM_JOB_ID"

# METHOD_TAG=methodcall1
-methodcall ${METHOD_TAG}  -inputfile ${INPUTFILE}

```
# 3 脚本

## 3.1 6.3 版本
- 装在 singularity 外
```bash
#!/bin/bash
#SBATCH -J comsol    ##作业名
#SBATCH -p xahcnormal   ##队列名
#SBATCH -N 1   ##节点数
#SBATCH --ntasks-per-node=1   ##每节点进程
#SBATCH --cpus-per-task=64   ##每进程占用核数

### module load apps/apptainer/1.2.4

source /work/home/ack9hdkbj3/apprepo/comsol/6.3-singularity/scripts/env.sh
WDIR=`pwd`       ##获取当前脚本所在路径
cd $WDIR           ##进入文件夹

APP=$PROGLIST   ##软件安装路径
##################################################################

export DISPLAY=vadmin25:16
singularity exec $SIF bash -c "export LD_LIBRARY_PATH=/work/home/ack9hdkbj3/apprepo/comsol/6.3-singularity/app/libXtst-1.2.3/libXtst-1.2.3/build/lib:$LD_LIBRARY_PATH ; $APP"
```

> [!NOTE] 
> 1. `/public/home/jsyadmin/apprepo/comsol/6.3-singularity/app/libXtst-1.2.3` 缺库在昆山下
> 2. 还得盲选设置成英文

-  装在 singularity 里，做好的 sif

```bash
# 山东五区
/public/home/jsyadmin/tanjj/softwares/comsol/6.3/comsol63.sif
```


```bash
# 图形

#!/bin/bash
#SBATCH -J comsol    ##作业名
#SBATCH -p sdictest   ##队列名
#SBATCH -N 1   ##节点数
#SBATCH --ntasks-per-node=1   ##每节点进程数
#SBATCH --cpus-per-task=5    ##每进程占用核数
module purge
module load  singularity/3.7.3
#source /work/home/jsyadmin/apprepo/comsol/6.3-singularity/scripts/env.sh
WDIR=`pwd`       ##获取当前脚本所在路径
cd $WDIR           ##进入文件夹

export DISPLAY=slogin02:4 # 修改端口号，图形桌面提交后，图形管理处可以获取

#################程序正式调用命令

#容器内运行，不支持跨节点
#IF_COMMAND="export LD_LIBRARY_PATH=/work/home/jsyadmin/apprepo/comsol/6.3-singularity/lib:$LD_LIBRARY_PATH;export LD_PRELOAD=/usr/lib/x86_64-linux-gnu/libfreetype.so;export DISPLAY=vadmin26:16;$APP"

#/public/software/apps/singularity/3.7.3/bin/singularity exec /public/home/ac1xe2jebb/soft/comsol63.sif  /user/local/comsol63/multiphysics/bin/comsol 

#直接运行，需要高版本glibc队列，可跨节点
#$APP
/public/software/apps/singularity/3.7.3/bin/singularity exec /public/home/ac1xe2jebb/soft/comsol63.sif  /usr/local/comsol63/multiphysics/bin/comsol
```


```bash
# 命令行脚本
#!/bin/bash
#SBATCH -J comsol    ##作业名
#SBATCH -p sdictest   ##队列名
#SBATCH -N 1   ##节点数
#SBATCH --ntasks-per-node=1   ##每节点进程数
#SBATCH --cpus-per-task=64    ##每进程占用核数
module purge
module load  singularity/3.7.3
#source /work/home/jsyadmin/apprepo/comsol/6.3-singularity/scripts/env.sh
WDIR=`pwd`       ##获取当前脚本所在路径
cd $WDIR           ##进入文件夹

APP=$PROGLIST   ##软件安装路径
INPUTFILE=$WDIR/test.mph    ##所要计算的mph文件
OUTPUTFILE=$WDIR/output$SLURM_JOB_ID.mph
LOGFILE=$WDIR/job$SLURM_JOB_ID.log     ##求解日志
##################################################################

##################获取节点列表
NP=$SLURM_NPROCS
NNODE=`srun hostname |sort |uniq | wc -l`
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE

mkdir tmp$SLURM_JOB_ID

nn=$SLURM_NPROCS
nnhost=$SLURM_NTASKS_PER_NODE
np=$SLURM_CPUS_PER_TASK

#################程序正式调用命令

#容器内运行，不支持跨节点
#IF_COMMAND="export LD_LIBRARY_PATH=/work/home/jsyadmin/apprepo/comsol/6.3-singularity/lib:$LD_LIBRARY_PATH;export LD_PRELOAD=/usr/lib/x86_64-linux-gnu/libfreetype.so;export DISPLAY=vadmin26:16;$APP"

#/public/software/apps/singularity/3.7.3/bin/singularity exec /public/home/ac1xe2jebb/soft/comsol63.sif  /user/local/comsol63/multiphysics/bin/comsol

#直接运行，需要高版本glibc队列，可跨节点
#$APP
#/public/software/apps/singularity/3.7.3/bin/singularity exec /public/home/ac1xe2jebb/soft/comsol63.sif  /usr/local/comsol63/multiphysics/bin/comsol
/public/software/apps/singularity/3.7.3/bin/singularity exec /public/home/ac1xe2jebb/soft/comsol63.sif  /usr/local/comsol63/multiphysics/bin/comsol  batch  -nn $nn  -nnhost $nnhost   -np $np   -mpmode owner -inputfile ${INPUTFILE} -outputfile ${OUTPUTFILE} -batchlog ${LOGFILE} -blas mkl -prefermph  -mpidebug 10  -tmpdir "$WDIR/tmp$SLURM_JOB_ID"    -prefsdir  "$WDIR/tmp$SLURM_JOB_ID"
```

# 4 FAQ

### 图片无法加载
![[daddc2c6a0041f6b18089f84f1668572.png]]

```bash
# 缺库 在西安
/work/home/jsyadmin/L4/dwj/zlib/install/lib

# 环境设置
export LD_LIBRARY_PATH=/work/home/acdxza17zc/apprepo/comsol/6.3-none/app/install/lib:$LD_LIBRARY_PATH 
```

### mph 无法加载

```bash
mpi不能有中文，不支持，java加载的
```

### oom

```bash
# java oom
vim /work/home/acqkl9gfir/apprepo/comsol/6.1-none_v1/app/comsol61/multiphysics/bin/glnxa64/comsol.ini
# 修改  -Xmx20g

# Settings for COMSOL desktop on glnxa64
-startup
../../plugins/org.eclipse.equinox.launcher_1.6.200.v20210416-2027.jar
--launcher.library
../glnxa64
-install ../..
-vm
../../java/glnxa64/jre/lib/server/libjvm.so
-vmargs
#-Djava.net.useSystemProxies=true
-Dosgi.splashPath=platform:/base/plugins/com.comsol.util/
-Dosgi.clean=true
-Dorg.osgi.service.log.admin.loglevel=DEBUG
-Dosgi.configuration.cascaded=true
-Dosgi.checkConfiguration=true
-Dosgi.sharedConfiguration.area=file:configuration/comsol
-Dosgi.sharedConfiguration.area.readOnly=true
-Dosgi.configuration.area=@user.home/.comsol/v61/configuration/comsol
-Dosgi.instance.area=@user.home/.comsol/v61/workspace/comsol
#-Dorg.eclipse.swt.browser.DefaultType=mozilla
-Declipse.security=
-Djava.security.policy=${osgi.install.area}/bin/glnxa64/comsol.policy
-Dcs.logoutput=file
-Dcs.logfileprefix=comsol
-Dcs.client=true
-Xss4m
-Xms40m
-Xmx20g
-XX:MaxMetaspaceSize=1g
-Dlog4j2.formatMsgNoLookups=true
```


- 几何导入闪退的，建议用脚本 vnc 提交
```bash
#!/bin/bash
#SBATCH -J comsol    ##作业名
#SBATCH -p xhhcnormal01   ##队列名
#SBATCH -N 1  ##节点数
#SBATCH --ntasks-per-node=1   ##每节点进程数
#SBATCH --cpus-per-task=8    ##每进程占用核数

export DISPLAY=vvnc08:2 #注意！需要手动修改为已打开的图形VNC编号

export GtkComboBox::appears-as-list=0

WDIR=`pwd`       ##获取当前脚本所在路径
cd $WDIR           ##进入文件夹
    
APP="/work/home/acdxza17zc/apprepo/comsol/6.2-none/app/comsol62/multiphysics/bin/comsol"   #软件安装路径
LOGFILE=$WDIR/job$SLURM_JOB_ID.log     ##求解日志
##################################################################
    
##################获取节点列表
NP=$SLURM_NPROCS
NNODE=`srun hostname |sort |uniq | wc -l`
LOG_FILE=$WDIR/job_${NP}c_${NNODE}n_$SLURM_JOB_ID.log
HOSTFILE=$WDIR/hosts_$SLURM_JOB_ID
srun hostname |sort |uniq -c |awk '{printf "%s:%s\n",$2,$1}' > $HOSTFILE

mkdir tmp$SLURM_JOB_ID

nn=$SLURM_NPROCS
nnhost=$SLURM_NTASKS_PER_NODE
np=$SLURM_CPUS_PER_TASK

#################程序正式调用命令
$APP  -nn $nn  -nnhost $nnhost   -np $np   -mpmode owner  -batchlog ${LOGFILE} -blas mkl -prefermph  -mpidebug 10  -tmpdir "$WDIR/tmp$SLURM_JOB_ID"    -prefsdir  "$WDIR/tmp$SLURM_JOB_ID"
```