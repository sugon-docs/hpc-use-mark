# 安装

- license
> https://blog.csdn.net/m0_48170265/article/details/136654433
```bash
cd /public/software/abaqus/license/DSLS.AllOS/1/RedHat_Suse
./startInstLicServ -noUI  -p /public/software/abaqus/license/install   # -p 指定安装路径 -noUI 无图形
cd /public/software/abaqus/license/install/linux_a64/code/bin
./DSLicTarget -t # 输出 Computer Id:  AJN-53C51394EFAFCEBC
```

- license启动lmgrd报没有这个文件
```bash
# 报错
[root@admin2-2 abaqus2022]# ./SSQ__lmgrd__linux64.bin -c ABAQUSTEST.lic -l log.admin2-2
-bash: ./SSQ__lmgrd__linux64.bin: 没有那个文件或目录

# 排查
[root@admin2-2 abaqus2022]# ldd  SSQ__lmgrd__linux64.bin 
	linux-vdso.so.1 (0x00007ffdb2d98000)
	libpthread.so.0 => /usr/lib64/libpthread.so.0 (0x00007f5c89495000)
	libm.so.6 => /usr/lib64/libm.so.6 (0x00007f5c89314000)
	libgcc_s.so.1 => /usr/lib64/libgcc_s.so.1 (0x00007f5c892fb000)
	libc.so.6 => /usr/lib64/libc.so.6 (0x00007f5c8914e000)
	libdl.so.2 => /usr/lib64/libdl.so.2 (0x00007f5c89149000)
	/lib64/ld-lsb-x86-64.so.3 => /lib64/ld-linux-x86-64.so.2 (0x00007f5c894dc000)  ## 这个有问题 so.3 找的是 so.2
	
# 解决
ln -s /lib64/ld-linux-x86-64.so.2 /lib64/ld-lsb-x86-64.so.3  # 就可以了

```

- 本体安装
```bash
# 打包得
## 直接把装好的压缩包复制解压过去
## license压缩包也有 解压下来就行

# 手动安装
module load apps/p7zip/9.20.1
 
## 赋予权限

chmod 755 -R *  # 先
export DSYAuthOS_`lsb_release -si`=1
export DSY_Force_OS=linux_a64
export NOLICENSECHECK=true

# 装完3D和CAA就可以命令行运行
# CAE模块是图形显示，用不到可以不装
./StartTUI.sh # ksh 可以直接改成bash
# AbaqusSolver和SIMULIA/CAE  要安装在两个不同目录下，安装SIMULIA/CAE时会要输入AbaqusSolver的路径

# 会输入全选和路径

```

五个文件夹
![[Pasted image 20250820120909.png]]
```bash
1. 先安装license
cd 3
```

###  破解方式 

```bash
# 6144 在
vim /work/home/cqu30174361/apprepo/abaqus/6144-none/app/6.14-4/SMA/site/abaqus_v6.env

# 2020
vim /work/home/aclb01ckwc/soft/ABAQUS2020/SIMULIA/EstProducts/2020/linux_a64/SMA/site/custom_v6.env

# 写入以下
# Installation of Established Products 2020

# Installation of Abaqus CAE 2020
# Wed Oct 27 11:51:10 2021
plugin_central_dir="/work/home/jsyadmin/dwj/abaqus2020/app/ABAQUS2020/DassaultSystemes/SIMULIA/CAE/plugins/2020"
license_server_type=FLEXNET
abaquslm_license_file="27800@login01"  # 修改成服务节点
```

###  启动可执行文件及方式
```bash
#2022之后图形启动
/work/home/jsyadmin/L4/dwj/2.28/abaqus2024/linux_a64/code/bin/SMALauncher
#之前图形
/work/home/jsyadmin/L4/dwj/2.28/abaqus2024/linux_a64/code/bin/ABQLauncher

# 2016 
/work/home/zhangzhang08/software/abaqus/2016/linux_a64/code/bin/SMALauncher # 命令行
```

```bash
# 西安启动
module purge
module load  mpi/intelmpi/2017.4.239 compiler/gcc/9.3.0   
```
# 使用

- 参数使用
https://abaqus-docs.mit.edu/2017/English/SIMACAEILGRefMap/simailg-c-envsyntax.htm

##  abaqus 和 starccm 联合

dlutzy 用户搞了半天

![[Pasted image 20250617190812.png]]
>/public/home/accslasm 5 v/apprepo/abaqus/2019-none/app/abaqus 2019/linux_a 64/code/bin/ABQLauncher
>/public/home/jsyadmin/apprepo/abaqus/2023-none/app/abaqus 2023/command/abq2023
>/public/home/jsyadmin/apprepo/abaqus/2022-null/app/abaqus/Command/abq
> 申请的核心数和实际 inp 运行的核心数要一致
> 手册：file:///public/home/jsyadmin/apprepo/starccmplus/18.06.006-null/app/18.06.006-R8/STAR-CCM+18.06.006-R8/doc/en/online/STARCCMP/GUID-837EFED7-B4DB-4343-BB6C-03A9541E53F1.html#star.cosimulation.link.common.CoSimulationValueManager.AbaqusExecution

- 添加 abaqus 软件的 libABQSMACeeModeles. so
![[Pasted image 20250617191510.png]]
>/public/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/code/bin/libABQSMACseModules.so
>/public/home/jsyadmin/apprepo/abaqus/2023-none/app/abaqus 2023/linux_a64/code/bin/libABQSMACseModules.so
>/public/home/jsyadmin/apprepo/abaqus/2022-null/app/abaqus/linux_a64/code/bin/libABQSMACseModules.so

>手册：`file:///public/home/jsyadmin/apprepo/starccmplus/18.06.006-null/app/18.06.006-R8/STAR-CCM+18.06.006-R8/doc/en/online/STARCCMP/GUID-837EFED7-B4DB-4343-BB6C-03A9541E53F1.html#star.cosimulation.link.common.CoSimulationValueManager.AbaqusExecution`

>官方手册：`https://www.topcfd.cn/Ebook/STARCCMP/GUID-837EFED7-B4DB-4343-BB6C-03A9541E53F1.html?hl=abaqus%2C%E5%91%BD%E4%BB%A4%2C%E4%BB%A4%E8%A1%8C`

>常见耦合问题：`https://zhuanlan.zhihu.com/p/681761110`


- abaqus
```bash
# abaqus2019的license会在

/public/home/accslasm5v/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/SMA/site/custom_v6.env


doc_root="file:////usr/DassaultSystemes/SIMULIA2019doc/English"
license_server_type=FLEXNET
abaquslm_license_file="27800@11.2.131.1"

# 修改这个license就可以了，不用export 

# 2023的话这样 里面
# Installation of Established Products 2023
# Wed Oct 11 13:30:54 2023
plugin_central_dir="/usr/local/abaqus-2023/DassaultSystemes/SIMULIA/CAE/plugins/2023"
# retrieve licensing configuration from EstablishedProductsConfig.ini
importEnv('licensing.env')
abaquslm_license_file = "27398@XXX.XXX.XXX.XXX"

# 如果报错什么license check failed 注意看下co**的输出，切换了下mpi就可以写到 ~/abaqus_v6.env 里
mp_mpi_implementation=IMPI
mp_file_system=(SHARED, LOCAL)

```

- starccm
	- java 文件
```java
// Simcenter STAR-CCM+ macro: run.java
// Written by Simcenter STAR-CCM+ 20.02.007
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cosimulation.link.abaqus.*;
import star.cosimulation.link.common.*;

public class run extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 =
      getActiveSimulation();

    CoSimulation coSimulation_0 =
      ((CoSimulation) simulation_0.get(CoSimulationManager.class).getObject("Link 1"));

    AbaqusExecution abaqusExecution_0 =
      coSimulation_0.getCoSimulationValues().get(AbaqusExecution.class);

    abaqusExecution_0.setJobFileName("boatcrashfan.inp");

    abaqusExecution_0.setExecutableName("/gpfs/software/apps/abaqus/2024/abaqus/Commands/abq2024");

    abaqusExecution_0.setNumCpus(190);

    AbaqusLibraryFile abaqusLibraryFile_0 =
      coSimulation_0.getCoSimulationValues().get(AbaqusLibraryFile.class);

    abaqusLibraryFile_0.setFilePath("/gpfs/software/apps/abaqus/2024/abaqus/linux_a64/code/bin/libABQSMACseModules.so");
  }
}

```
![[run.java]]
###  网格核心配比
```bash
.sta 文件里看 
单核心3-5万网格
```

### mpi 切换

```bash
# 自带mpi路径
/work/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/code/bin/SMAExternal/impi 
/work/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/code/bin/SMAExternal/pmpi

# 配置文件
/work/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/SMA/site/mpi_config.env # 2019
/work/home/zhangzhang08/software/abaqus/2016/linux_a64/SMA/site/lnx86_64.env # 2016 老的最好默认 pmpi

# 修改mpi
## 修改自带的mpi类型
### 脚本里
echo "mp_mpi_implementation=IMPI" >> abaqus_v6.env # 用intelmpi
echo "mp_mpi_implementation=PMPI" >> abaqus_v6.env # 用platformmpi
### 手动修改 /work/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/SMA/site/mpi_config.env

## 使用集群系统的mpi
### 1.要修改此配置文件
vim
/work/home/jsyadmin/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/SMA/site/mpi_config.env  

### 2.替换成集群的路径 intelmpi为例，ABA_PATH 改成 I_MPI_ROOT 后面的路径直到mpirun
mp_mpirun_path = {PMPI: driverUtils.locateFile(os.environ.get('ABA_PATH', ''), 'pmpi/bin', 'mpirun'),
                  IMPI: driverUtils.locateFile(os.environ.get('I_MPI_ROOT', ''), 'intel64/bin', 'mpirun'),
			      CMPI: driverUtils.locateFile(os.environ.get('ABA_PATH', ''), 'impi/bin', 'mpirun')}

### 3. 提交时需要在slurm脚本里 
module load mpi/intelmpi/2018.4.274


# 西安切换pmpi 
要在mpi_config.env 里加 mp_mpirun_options = '-srq' 注意python语法空格 # 短通信参数
# 西安切换intelmpi
export I_MPI_PIN_DOMAIN=numa # 需要加这个变量
```

## 调用
```bash
# python 脚本调用
app/ABAQUS2020/SIMULIA/EstProducts/2020/linux_a64/code/bin/ABQLauncher cae noGUI=xxx.py  
```

# 脚本

 - 启动脚本
```bash
#!/bin/bash 
#SBATCH -J abaqus_test   #作业名
#SBATCH -p wzhctest 
#SBATCH -N 1   #节点数
#SBATCH --ntasks-per-node=30 #每节点进程数
#SBATCH -o out.%j    
#SBATCH -e err.%j

unset SLURM_GTIDS

module purge
source ../scripts/env.sh
export EXEC=$PROGLIST
WORK_DIR=`pwd`     #当前目录
INPUT_FILE=tracload2d.inp  #输入文件
job_name=`echo ${INPUT_FILE} | awk -F. '{print $1}'`
#USER_FILE=exa_fml_ortho_damage_umat.for    #fortran子程序
LOG_FILE=abaqus-test.log
#########################################################

cd $WORK_DIR
PROCS=$SLURM_NPROCS
 
srun hostname|sort|uniq -c|awk '{print $2}' > ./hostfile_$PROCS
HOST_FILE=./hostfile_$PROCS
 
NODELIST=""
for host in `cat $HOST_FILE |uniq`
do
  if [ -z ${NODELIST} ]; then
    NODELIST="['$host',$SLURM_NTASKS_PER_NODE]"
  else
    NODELIST="$NODELIST,['$host',$SLURM_NTASKS_PER_NODE]"
  fi
done
 
cat > abaqus_v6.env << !
mp_host_list=[$NODELIST]
max_history_requests=0
mp_mpi_implementation=PMPI 
mp_file_system=(SHARED, LOCAL)
!

############程序主命令

module load compiler/intel/2017.5.239

$EXEC job=${job_name} input=${INPUT_FILE} cpus=${PROCS} mp_mode=MPI   int  scratch=./ # 缓存

#$EXEC job=${job_name} input=${INPUT_FILE}  cpus=${PROCS} user=$USER_FILE mp_mode=MPI int    #带子程序
 
#$EXEC job=${job_name} input=${INPUT_FILE}  oldjob=job-2  cpus=${PROCS} mp_mode=MPI int    #重启动分析

echo The end time is: `date +"%Y-%m-%d %H:%M:%S"` | tee -a $LOG_FILE
```

# 报错

## 6144 图形启动缺库

![[Pasted image 20250605235844.png]]

```bash
export LD_PRELOAD=/work/home/jsyadmin/L4/liucheng/tmp/libstdc++.so.5  # 在西安下复制过去

export LM_LICENSE_FILE=27800@license01
export LD_PRELOAD=/work/home/handsomer5/lib/libstdc++.so.5
```

## 2024 启动报错

```bash
# 报错 
/work/home/martin_tan/apprepo/abaqus/2024-none/app/abaqus2024/Commands/abaqus: /lib64/libstdc++.so.6: version `CXXABI_1.3.9` not found (required by /work/home/martin_tan/apprepo/abaqus/2024-none/app/abaqus2024/Commands/abaqus)

# 解决方法
strings /lib64/libstdc++.so.6 | grep CXXABI # 查看版本

env里加上这个

module purge
module load compiler/gcc/9.3.0 mpi/openmpi/4.1.5/gcc-9.3.0
export LD_PRELOAD=/public/software/compiler/gnu/gcc-9.3.0/lib64/libstdc++.so.6
export LD_LIBRARY_PATH=/public/software/compiler/gnu/gcc-9.3.0/lib64:$LD_LIBRARY_PATH

再source 
```
## mpi 通信  报错等
### dapl 报错

![[23d25bdbde3ac4099a564dbfd4415ef2 1.png]]

```bash
export MPI_IC_ORDER="uDAPL:TCP" # 昆山加
```

## for 子程序编译报错

```bash
#!/bin/bash 
#SBATCH -J abaqus_test   #作业名
#SBATCH -p wzhcnormal 
#SBATCH -N 1   #节点数
#SBATCH --ntasks-per-node=16 #每节点进程数
#SBATCH -o out.%j    
#SBATCH -e err.%j
#SBATCH --constraint="32core"
#SBATCH --exclusive

export I_MPI_PIN_DOMAIN=numa


#module load compiler/devtoolset/7.3.1 mpi/hpcx/2.11.0/gcc-7.3.1
#module load compiler/intel/2021.3.0 mpi/intelmpi/2021.3.0
#module load compiler/intel/2020.1.217  mpi/intelmpi/2020.1.217
module purge
module load compiler/intel/2017.5.239  mpi/intelmpi/2017.4.239
export LD_LIBRARY_PATH=/opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/compiler/lib/intel64:$LD_LIBRARY_PATH

#export LD_LIBRARY_PATH=/public/software/compiler/intel/intel-2020/compilers_and_libraries_2020.1.217/compiler/lib/intel64/:$LD_LIBRARY_PATH

source /work/home/ac74q8ni2f/apprepo/abaqus/2021-none/scripts/env.sh 
export EXEC=$PROGLIST
WORK_DIR=`pwd`     #当前目录
INPUT_FILE=single3DUMAT.inp  #输入文件
job_name=`echo ${INPUT_FILE} | awk -F. '{print $1}'`
USER_FILE=umat.f    #fortran子程序
LOG_FILE=abaqus-test.log   
#########################################################

cd $WORK_DIR  
PROCS=$SLURM_NPROCS
env_file=abaqus_v6.env
node_list=$(scontrol show hostname ${SLURM_NODELIST} | sort -u)

mp_host_list="["
for host in ${node_list}; do
    mp_host_list="${mp_host_list}['$host', ${SLURM_CPUS_ON_NODE}],"
done

mp_host_list=$(echo ${mp_host_list} | sed -e "s/,$/]/")

echo "mp_host_list=${mp_host_list}"  >${env_file}
echo "mp_mpi_implementation=IMPI" >>${env_file} 
echo "mp_file_system=(SHARED, LOCAL)" >>${env_file}


echo The start time is: `date +"%Y-%m-%d %H:%M:%S"` | tee $LOG_FILE  
echo My job ID is: $SLURM_JOB_ID | tee -a $LOG_FILE 
echo The total cores is: $PROCS | tee -a $LOG_FILE 
echo The hosts is: | tee -a $LOG_FILE 
cat $HOST_FILE | tee -a $LOG_FILE


############程序主命令
#ssh $HOSTNAME "source /public/software/compiler/intel/intel-compiler-2017.5.239/bin/compilervars.sh intel64;cd $WORK_DIR;$EXEC job=${job_name} input=${INPUT_FILE} user=$USER_FILE cpus=${PROCS} mp_mode=MPI double int"

$EXEC job=${job_name} input=${INPUT_FILE} user=$USER_FILE  cpus=${PROCS} mp_mode=MPI   int scratch=./

#ssh $HOSTNAME "export LM_LICENSE_FILE=27800@hlogin11;cd $WORK_DIR;$EXEC job=${job_name} input=${INPUT_FILE} cpus=${PROCS} mp_mode=THREAD double int"

echo The end time is: `date +"%Y-%m-%d %H:%M:%S"` | tee -a $LOG_FILE

```

```bash
# 报错
/work/home/ac74q8ni2f/apprepo/abaqus/2021-none/app/linux_a64/code/bin/standard: symbol lookup error: /tmp/ac74q8ni2f_single3DUMAT_5763/libstandardU.so: undefined symbol: for_realloc_lhs

# 分析 库版本老旧
# 参考 https://community.intel.com/t5/Intel-Fortran-Compiler/Turn-off-automatic-reallocation-of-arrays-on-compiler-19-0/m-p/1157977
# 官方文档：https://www.intel.com/content/www/us/en/docs/fortran-compiler/developer-guide-reference/2025-0/standard-realloc-lhs.html
# 还有些文档：https://www.cnblogs.com/structurer/p/10652854.html

# 解决
/work/home/ac74q8ni2f/apprepo/abaqus/2021-none/app/linux_a64/SMA/site/lnx86_64.env # abaqus_v6.env 里加也行
# 里添加编译选项  '-nostandard-realloc-lhs',
compile_fortran = [fortCmd,
                   '-V',
                   '-c', '-fpp','-fPIC','-extend_source',
                   '-DABQ_LNX86_64', '-DABQ_FORTRAN',
                   '-auto',    # <-- important for thread-safety of parallel user subroutines
                   '-pc64',                  # set FPU precision to 53 bit significand
                   '-align', 'array64byte',
                   '-nostandard-realloc-lhs',
                   '-prec-div', '-prec-sqrt',# improve precision of FP divides and sqrt
                   '-fp-model', 'precise',   # floating point model: precise
                   '-fimf-arch-consistency=true', # math library consistent results
                   '-mP2OPT_hpo_vec_divbyzero=F',
                   '-no-fma',                # disable floating point fused multiply-add
                   '-fp-speculation=safe',   # floating point speculations only when safe
                   '-fprotect-parens',       # honor parenthesis during expression evaluation
                   '-fstack-protector-strong', # enable stack overflow protection checks
                   '-reentrancy', 'threaded',  # important for thread-safety
                   #'-init=zero','-init=arrays',  # automatically initialize all arrays to zero
                   #'-init=snan', '-init=arrays', # automatically initialize all arrays to SNAN
                   '-msse3',                      # generate SSE3, SSE2, and SSE instructions
                   '-axcore-avx2,avx',
                   '-WB', '-I%I', '-I'+abaHomeInc, '%P']

```

> 不要出现中文注释
> 其实也支持gfortran 
> 这个视频有 `https://www.bilibili.com/video/BV1qU4y1K7A3/?vd_source=12f13b225da822698e030dccdbc9bd97`

##  abaqus_v 6. env 文件影响
![[Pasted image 20250704175103.png]]

```bash
检查主目录下有没有abaqus_v6.env节点文件影响，直接删掉
```

- 报错 max_history_requests 限制

```bash
Error in job Job-1-1: The number of history output requests (100001) in this analysis step has exceeded 10x the maximum value of 10000 specified by the Abaqus environment variable 'max_history_requests.' Reduce the number of requests, increase the value of this variable, or deactivate this test by setting 'max_history_requests=0' in the abaqus_v6.env file. Significant performance problems may occur if these requests are maintained.
Job Job-1-1: Analysis Input File Processor aborted due to errors.
```

```bash

# 主目录下写个abaqus_v6.env 可控制
max_history_requests=0 # 无限制 

mp_mpi_implementation=PMPI

mp_file_system=(SHARED, LOCAL)
```

## 不分常规
- 算例过小

```bash
算例过小，网格分块异常，计算错误，其他工业软件也通用
```
- 中文路径特殊字符
![[c4b5ebea34032b2612fc4ae6b27653c1.jpg]]
```bash
# 报错
Abaqus Error: License for explicit with cpus=8 is not available.

# 检查中文路径
```

```bash
<IBM Platform MPI>: : warning, dlopen of libhwloc.so failed (null)/lib/linux_amd64/libhwloc.so: cannot open shared object file: No such file or directory
Traceback (most recent call last):
Exception: can not parse host/port from umbrella

# 添加这个 脚本
unset SLURM_GTIDS
export MKL_CBWR=AVX2
```


```bash
# 双精度
double=both
```
#### 算例问题

```bash
***ERROR: ExceSsive distortion of element number 12671 of instance PART-1-1

算例单元过度变形
```

```bash
The analysis may need a large number of increments (more than 20,000,000)...

算例变形发散，修改时间步网格
```

```bash
Error "Too many attempts made for this increment" 

算例问题，网格不行，或者没进程分配的网格数太少了，看 sta文件
```

```bash
aborted with system error "Illegal instruction"(signal 4)

export MKL_CBWR=AVX2
```

- odb 文件属性错了，要压缩传输
![[Pasted image 20250930192444.png]]

- .for 找不到可以cp下修改后缀为 .f 

-  6144运行报错
```bash
# 报错
Abaqus Error: The executable standard
aborted with system error "Illegal instruction" (signal 4).
Please check the .dat, .msg, and .sta files for error messages if the files
exist.  If there are no error messages and you cannot resolve the problem,
please run the command "abaqus job=support information=support" to report and
save your system information.  Use the same command to run Abaqus that you
used when the problem occurred.  Please contact your local Abaqus support
office and send them the input file, the file support.log which you just
created, the executable name, and the error code.
Abaqus/Analysis exited with error

# env 加这个
export MKL_CBWR=AVX2
换高版本abaqus
```

- 文件
```
## 1. Abaqus 常见计算日志文件

当你提交一个作业（Job）时，Abaqus 会在工作目录下生成多个文件：

|文件扩展名|作用|
|---|---|
|**`.log`**|记录作业启动、版本信息、硬件环境、求解器调用过程等。|
|**`.msg`**|求解过程的详细信息，包括迭代次数、收敛状态、警告等。|
|**`.dat`**|模型检查信息、单元/节点数、载荷步摘要等。|
|**`.sta`**|分析步的进度（百分比）、当前增量等。|
|**`.odb`**|结果数据库文件（可视化用）。|
|**`.lck`**|锁文件，防止多个进程同时写入 `.odb`。|

## 2. `.log` 文件内容结构

一个典型的 `.log` 文件包含以下部分：

1. **作业启动信息**
    
    复制代码
    
    `Begin Analysis Input File Processor Run pre.exe`
    
    - 显示 Abaqus 版本、启动时间、调用的可执行程序。
2. **硬件与环境信息**
    
    复制代码
    
    `Host Name: myPC Number of CPUs: 8`
    
    - 记录运行机器的 CPU 核数、内存等。
3. **分析步骤**
    
    复制代码
    
    `Begin Abaqus/Standard Analysis Step 1, Increment 1`
    
    - 每个分析步的开始时间、增量信息。
4. **结束状态**
    
    复制代码
    
    `Analysis completed successfully`
    
    - 如果失败，会显示 `ERROR` 或 `Abaqus/Standard exited with an error`.

## 3. 查看计算日志的方法

- **Abaqus/CAE**  
    在 **Job Manager** 中选中作业 → 点击 **Monitor** → 切换到 **Message** 或 **Log** 标签页。
- **命令行**  
    直接用文本编辑器打开 `jobname.log` 或 `jobname.msg`。

## 4. 常见日志问题与排查

|日志提示|可能原因|解决建议|
|---|---|---|
|`Too many attempts made for this increment`|收敛失败|检查载荷步设置、网格质量、材料参数。|
|`Abaqus/Standard exited with an error`|输入文件错误或计算中断|查看 `.dat` 和 `.msg` 文件定位错误行。|
|`.log` 文件未生成|作业未启动或启动即失败|检查 `.lck` 文件、路径权限、命令行输出。|
```

- 子程序 for 问题
```bash
dos2unix xxx.for
# 是一个用于将文本文件从 **DOS/Windows 格式** 转换为 **Unix/Linux 格式** 的工具，主要解决不同操作系统间换行符差异导致的问题。它可以有效避免因换行符不一致引发的脚本执行错误或文本处理问题。
```