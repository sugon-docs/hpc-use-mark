
# 图形启动

```bash
/work/home/jsyadmin/L4/dwj/v221/v221/ansys/bin/launcher2022r1   # 可执行路径
export MESA_GL_VERSION_OVERRIDE=3.3  # 环境
```
# 脚本
```bash
#!/bin/bash
#SBATCH -p hfacnormal04
#SBATCH -N 2
#SBATCH --ntasks-per-node=128
#SBATCH --exclusive    ##独占节点
#SBATCH -o %j.out   ##标准输出
#SBATCH -e %j.err   ##错误日志


WORK_DIR=`pwd`

EXEC=/public/home/llfang/apprepo/mechanical/2022r1-none/app/v221/ansys/bin/mapdl
INPUT_FILE=${WORK_DIR}/ASIPP_Test_Case2.inp
HOSTFILE=$(srun hostname |sort |uniq -c |\awk '{print $2 ":" $1}'|\paste -s -d ":" -)

$EXEC -b -dis -mpi intelmpi -machines $HOSTFILE -i $INPUT_FILE -o output.log -dir ${WORK_DIR}
```

# 模板提交

```bash
# 输入文件一般有以下 
 512 -rwxr--r-- 1 jsyadmin jsyadmin  405 Sep  3 15:32 pb.inp
1.5G -rwxr-xr-x 1 jsyadmin jsyadmin 1.5G Sep  3 15:32 pb.db
 512 -rwxr--r-- 1 jsyadmin jsyadmin  449 Sep  3 15:32 00_hpc_fat1.txt  # 这是提交文件
 512 -rwxr--r-- 1 jsyadmin jsyadmin  183 Sep  3 15:32 unit_load_bolt.mac
3.5K -rwxr--r-- 1 jsyadmin jsyadmin 3.2K Sep  3 15:32 solv_yeya_bolt_fat.mac
1.5K -rwxr--r-- 1 jsyadmin jsyadmin 1.1K Sep  3 15:33 pbAnalysisParams.mac

# 00_hpc_fat1.txt 内部
vim 00_hpc_fat1.txt

!用于新hpc节点中linux提交计算任务，用于套圈螺栓孔疲劳计算提交
fini
/clear
!dir_cal='yeya_two_jianxi'   !需要修改
!/cwd,strcat('../',dir_cal)
!/cwd,'db'
!/input,'assembly','mac','../apdl',,0
!fini
!/clear
!dir_cal='yeya_two_jianxi'   !需要修改
!job_num='job1'  !需要修改
!/cwd,strcat('../../',dir_cal)
!/CWD,strcat('sim_bolt_if/',job_num)  !需要修改
/INPUT,'pb','inp','',, 0
!/INPUT,'post','inp','',, 0

# 日志查看
file0* PRETENSION0.log 等

# 输出
rst
```