# 安装

```bash
# 2025R2 安装好的
/public/home/jsyadmin/tanjj/paks/ansys/2025R2/build # 昆山

/work/home/jsyadmin/L4/chuxu/softpack  # 西安 ANSYS2025R1安装包
/work/home/jsyadmin/L4/dwj/2025ans.tgz # 西安 安装好的ansys2025
```
## fluent-不止 fluent

### gpu 加速

```bash
#!/bin/bash
#SBATCH -J fluent    ##作业名
#SBATCH -p dzagnormal    ##队列
#SBATCH -N 1    ##申请计算节点数
##SBATCH -n 64
#SBATCH --ntasks-per-node=8   ##每节点进程数
#SBATCH --gres=gpu:1

source /work/home/jsyadmin/apprepo/fluent/2024r1-none/scripts/env.sh
export DISPLAY=mlu4:1
WORK_DIR=`pwd`    ##获取当前路径
FLUENT_VERSION=3ddp   ## [ 3d | 3ddp | 2d | 2ddp ]

### optional and advance parameters
IBSTAT=`ibstat|grep ^CA|awk '{print $2}'`
if [[ $IBSTAT =~ "mlx" ]]; then
   OPTIONAL="-pinfiniband -mpi=intel "
elif [[ $IBSTAT =~ "hfi" ]];then
   OPTIONAL="-pmpi-auto-selected -mpi=intel -ssh -affinity=core"
else
   OPTIONAL="-mpi=intel "
fi
#########################################################

cd $WORK_DIR

####################################形成节点文件
PROCS=$SLURM_NPROCS  ##总进程数
srun hostname|sort|uniq -c|awk '{print $2":"$1}' > ./hostfile_$PROCS
HOST_FILE=./hostfile_$PROCS
####################################

$PROGLIST  $FLUENT_VERSION -t$PROCS -gpgpu=1 -cnf=$HOST_FILE  $OPTIONAL 2>&1 # -gpgpu=1 1是开启 0是关
#$PROGLIST -g $FLUENT_VERSION -t$PROCS -cnf=$HOST_FILE -i $INPUT_FILE $OPTIONAL 2>&1    ##正式运行软件命令
```

手册参考：https://zhuanlan.zhihu.com/p/662171124
### dcv 图形启动设置
- dcv  ` -pib 3ddp -mpi=intel2018`
![[Pasted image 20250518095454.png]]

![[Pasted image 20250518102842.png]]

### 一些配置
```bash
#dcv
-pib 3ddp -mpi=intel2018 #23及以后
parallel setting那边选shared memory

#默认
-pinfiniband 3ddp -mpi=intel2018

export FLUENT_INTEL_MPI_VERSION=2018

export KMP_AFFINITY=disabled


export UCX_NET_DEVICES=mlx5_0:1
export UCX_IB_PCI_BW=mlx5_0:100Gbs
export UCX_TLS=dc_mlx5,rc_mlx5

# ibface..
export UCX_IB_ADDR_TYPE=ib_global

# intelmpi 老版本mpi2017、2018版本用
export I_MPI_FABRICS=shm:dapl
export I_MPI_DAPL_UD=enable
export I_MPI_FALLBACK_DEVICE=disable
export I_MPI_DAPL_UD_PROVIDER=ofa-v2-mlx5_0-1u


#  amd的可能不要 比较新的机器 合肥 opa 的不用加-pinfiniband

#指定intel版本 # 2023及往后用 
export FLUENT_INTEL_MPI_VERSION=2018

# fluent 21的老版本，没有dapl的可以用
-pib.ofed 3ddp -mpi=intel
```

### 2025 版本

```bash
西安下安装了
可能会缺的库
/work/home/jsyadmin/L4/dwj/2025ans/v251/fluent/lib/lnamd64/external

/work/home/jsyadmin/L4/dwj/2025ans.tgz
```


### udf 使用

```bash
https://mp.weixin.qq.com/s/u9gJvN7Bz9QWRfGrtQ091Q # 图形使用参考 
# 可以同时载入多个
# 会生成libudf文件 里面有makefile 
# .c会复制到libudf里的src里
# 可以在libudf 下直接 make  makeclean 调试 成功后直接usr-defined---Functions--compiled--load 或者在compile 一遍load也行
# 编译好了测试udf  usr-defined---execute on Demand---Execute 看输出 无报错 Done.就可以

## .c 文件里老有些语法错误  
### 少 {}  多{}  少 ； 遇到编译error可以问ai，定位到.c文件第几行调整看看
```

### 报错

```bash
Error in `fluent': double free or corruption (!prev): 0x00000000e9ebc190 *** 
# 这个报错之前遇到的经验是独占然后缩小核数可能可以解决
```

```bash
Error: eval: unbound variable
symbol %setluate-aerodamping-monitor
Error encountered in critical code section
 The fluent process could not be started.
 
# 解决
vi看下输入文件里的信息呢，cas里的，如果是h5格式的，用h5dump先解压 # module load  mathlib/hdf5/1.12.2-gnu-7.3.1

1.版本不一致
2.网格分组错误，概率性问题，重新提交就行，建议用.cas和.dat格式
```

```bash
自适应网格报错
关闭什么自适应的开关
```


```bash

Writing to j03r2n01:"/work/home/acjvnnnyg1/02clean_chemistry/case/cleanmap/clean_map0721_1rn-1-00150.cas.h5" in NOO
DE0 mode and compression level 1 ...
  Writing mesh ...
j11r4n04:UCM:9e37:c30aa700: 1890905250 us(1890905250 us!!!): dapl async_event QP (0x91752e0) Event 3
mlx5: j03r2n01: got completion with error:
00000000 00000000 00000000 00000000
00000000 00000000 00000000 00000000
000000b9 00000000 00000000 00000000
00000000 00008813 100076ab 008ae4d3
j03r2n01:UCM:d600:37cc880: 1890740163 us(1890740163 us!!!): DTO completion ERR: status 10, op Invalid DTO OP?, venn
dor_err 0x88 - 0.0.0.0
[35:j03r2n01][../../src/mpid/ch3/channels/nemesis/netmod/dapl/dapl_poll_rc.c:1374] Intel MPI fatal error: ofa-v2-mm
lx5_0-1u DTO operation posted for [305:j11r4n04] completed with error. status=0x6. cookie=0x40131
[35:j03r2n01] unexpected DAPL connection event 0x4006 from 305
Fatal error in PMPI_Waitall: Internal MPI error!, error stack:
PMPI_Waitall(405)........: MPI_Waitall(count=32, req_array=0x7ffd26c21c40, status_array=0x7ffd26c21740) failed
MPIR_Waitall_impl(221)...: fail failed
PMPIDI_CH3I_Progress(850): fail failed
(unknown)(): Internal MPI error!
    63975710 cells,     8 zones ...
   306049680 faces,    50 zones ...
 The fluent process could not be started

# 解决
# 存储ib 超时，退费
```

```bash

# 报错 cas.h5导入正常，dat.h5报错如下

Reading from node198:"/public/home/ac1lvbdbfj/FTL/3D_2D/3D/620w_4.2_6000_510_0.6/caseA.dat.h5" in NODE0 mode ...
HDF5-DIAG: Error detected in HDF5 (1.12.2) MPI-process 0:
  #000: /home2/cfx5test/TFSAgents/milce7cfxbld03.ansys.com/_work/_tool/.conan/data/hdf5/1.12.2/thirdparty/stable/build/2de328b9b7ee746673e43fb810561a50286bc510/hdf5-1.12.2/src/H5F.c line 620 in H5Fopen(): unable to open file
    major: File accessibility
    minor: Unable to open file

# 解决 
(pyfluent) [ac1lvbdbfj@login02 620w_4.2_6000_510_0.6]$ h5dump  -H caseA.dat.h5
h5dump error: unable to open file "caseA.dat.h5"
# hd5dump 也报错（pyfluent里带）
# 就是.dat.h5文件损坏了

```

```bash
Error: File has wrong dimensions (2). Error Object: #f  Error: error: wta[1](string) Error Object: ()  Error: Error reading "/work/home/ace0bzuwpi/qiongzhou/-3/2da/0.2500/jt=-3-8-2025-8-13-28.61.cas". Error Object: #f  Error: api-get-var: the object is not active Error Object: setup/general/solver/time


## 解决
精度选错 换成2ddp或者2d
```


```bash
# 导入时报错
Error: Set_Thread_Variables: wta(real)
Error Object: ((constant . 0) (profile "" "")) # 这一般是在高版本24 25 里的

# 解决
低版本打开高版本
```

```bash
# 报错

[14:c06r3n02][../../src/mpid/ch3/channels/nemesis/netmod/dapl/dapl_conn_ud.c:138] error(0x30000): ofa-v2-mlx5_1-1u: could not connect DAPL UD endpoint: DAT_INSUFFICIENT_RESOURCES()
Assertion failed in file ../../src/mpid/ch3/channels/nemesis/netmod/dapl/dapl_init_ud.c at line 2282: mpi_errno == MPI_SUCCESS
internal ABORT - process 0

# 解决
export I_MPI_FABRICS=shm:dapl
export I_MPI_DAPL_UD=enable
export I_MPI_FALLBACK_DEVICE=disable
export I_MPI_DAPL_UD_PROVIDER=ofa-v2-mlx5_0-1u

```

```bash
# 导入.dat.h5文件报错相关的 缺失hdf5相关环境的，建议都重新打包上传
```
### 使用 tip

```
1.合肥、华北A区opa网络不能设置为IB
2.23、24以上要选择intel2018
```

```bash
1. 192 193 201等几个fluent版本没事不加命令行参数跳出软件调参界面，fluent提交模板限制
2. 可用 workbench 或者 cfx 的提交模板代替
3. hostfile 也要手动去选下
```

```bash
# 卡死
目录下创建 touch check-fluent 文件 # 前提是不能卡在ib通信上

# 退出
目录下创建  touch exit-fluent 文件  # 前提是不能卡在ib通信上 

# 中文 
-setenv="lang=zh" 

# 算例路径 
-case file_path

# meshing
-meshing

# gpu
-gpgpu=1 # 1是开启 0是关
```



### pyfluent

#### 注意手册

```bash
# 1.安装 
pip install ansys-fluent-core  # pyfluent包老了依赖python2.7
pip install ansys-fluent-parametric  # 参数化模块
pip install ansys-fluent-visualization # 后处理模块

# 2.关联
export AWP_ROOT252=/usr/ansys_inc/v252 # 环境变量里添加

# 3.支持
# All versions of PyFluent support Fluent 2022 R2 and later.

# 4.集群无图形

# 5.基础指令
ansys.fluent.core.launcher.launcher.launch_fluent(version=None, precision=None, processor_count=None, journal_filename=None, meshing_mode=None, start_timeout=100, additional_arguments='', env=None, start_instance=None, ip=None, port=None, cleanup_on_exit=True, start_transcript=True, show_gui=None)

#  `version`：可选参数为`'2d'`或`'3d'`，不设置则默认为`'3d'`
    
#  `precision`：可选参数为`'single'或'double'`，不设置则默认为`'double'`
    
#  `processor_count`：指定处理器数量，默认值为`1`
    
#  `journal_filename`：指定journal文件的路径
    
#  `meshing_mode`：可选参数为`True或False`，指定为`True`表示启动Fluent Meshing
    
#  `start_timeout`：指定连接Fluent的最大时间，默认为100 s
    
#  `additional_arguments`：指定启动Fluent时可以添加的额外参数，参数类型为字符串形式
    
#  `env`：在Fluent中修改环境变量的映射，参数为字典形式
    
#  `start_instance`：此参数如果为False，则连接到ip和端口上的现有Fluent实例。否则启动Fluent的本地实例。此参数默认为True，也可以由环境变量设置
    
#  `ip`：连接到现有Fluent实例的IP地址。仅当start_instance为False时使用。默认值为`'127.0.0.1'`，也可以由环境变量设置
    
#  `port`：连接到现有Fluent实例的端口。仅当start_instance为False时使用。默认值可以由环境变量`PYFLUENT_FLUENT_PORT=`设置。
    
#  `cleanup_on_exit`：如果为True，则当PyFluent退出或在会话实例上调用`exit()`函数时，连接的Fluent会话将关闭，默认情况下为True。
    
#  `start_transcript`：当参数指定为True时，客户端中会启动Fluent transcript。它可以通过对会话对象的方法调用来启动和停止。
    
#  `show_gui`：当此参数为True时，且START_INSTANCE也为True时，会显示Fluent的图形用户界面，这也可以通过环境变量`PYFLUENT_SHOW_SERVER_GUI=0或1`来设置。show-gui参数的作用是覆盖`PYFLUENT_SHOW_SERVER_GUI`变量。例如当PYFLUENT_SHOW_SERVER_GUI设置为1，若show_gui设置为False，则隐藏图形用户界面。默认设置为None，以便可以检测到显式False设置。

# 6.pyfluent 脚本录制
## tui界面输入
api-start-python-journal "pyfluenttest3.py"
# 官方视频：https://www.bilibili.com/video/BV1jz4y1v7gL/?spm_id_from=333.337.search-card.all.click&vd_source=359307c2cb3efd70a975ce2a03e18519
```

>官方手册：https://fluent.docs.pyansys.com/version/stable/getting_started/installation.html    https://github.com/ansys/pyfluent 
>胡沙手册：https://www.topcfd.cn/19458/
>其他：https://blog.csdn.net/pud_ding/article/details/130038945   https://blog.csdn.net/xiaoqiang_2/article/details/134629991
>pyansys (不只是 fluent)：https://docs.pyansys.com/version/dev/#

#### 脚本案例

```python
# 启动fluent，载入网格，检查有无负体积网格

import_filename="naca0012.cas"
#fluent启动界面，2d双精度，4核，mode="solver"求解模式，show_gui=True同步显示fluent
solver = pyfluent.launch_fluent(version="2d",precision="double",
                                processor_count=4,show_gui=True, mode="solver")
#读入网格
solver.file.read(file_type="case", file_name=import_filename)
#检查网格
solver.tui.mesh.check()

# 定义模型：开启能量方程，sst k-omega

solver.setup.models.energy.enabled = True
solver.tui.define.models.viscous.kw_sst('yes')
solver.setup.models.viscous.k_omega_options.kw_low_re_correction=False
solver.setup.models.viscous.options.viscous_heating=True

# 定义材料：理想气体，定义操作压力

solver.setup.materials.fluid['air'].density.option='ideal-gas'
solver.setup.materials.fluid['air'].viscosity.option='sutherland'
solver.tui.define.operating_conditions.operating_pressure('101325')

# 定义求解算法，压力速度耦合，开启全局伪时间，定义残差检测

solver.tui.solve.set.p_v_coupling(24) # Coupled
solver.tui.solve.set.gradient_scheme('yes')    # Green-Gauss Node Based
solver.solution.methods.pseudo_time_method.formulation.coupled_solver='global-time-step'
solver.tui.solve.monitors.residual.check_convergence('yes','no','no','no','no','no')
solver.tui.solve.monitors.residual.convergence_criteria('1e-7')
solver.tui.solve.monitors.residual.monitor('yes','yes','yes','yes','yes','yes')
solver.tui.solve.monitors.residual.plot("yes")
solver.tui.solve.monitors.residual.print("yes")

```

> 官方案例：https://parametric.fluent.docs.pyansys.com/version/stable/examples/00-parametric/parametric_static_mixer_1.html#sphx-glr-examples-00-parametric-parametric-static-mixer-1-py

- 客户的
```bash
#!/bin/bash
#SBATCH -J python
#SBATCH -p wzhcnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=32
#SBATCH --exclusive

module purge
source /work/home/houzhao/miniconda3/bin/activate tf-env

export AWP_ROOT241=/work/home/houzhao/apprepo/fluent/2024r1-none/app/v241
export PATH="$AWP_ROOT241/fluent/bin:$PATH"

python3 test2.py
```

```python
### test2.py 内容

#!/usr/bin/env python3
import random

import os
import numpy as np
import random
#import tensorrt as trt
#import tensorflow.compat.v1 as tf
import ansys.fluent.core as pyfluent


 #from ANSYS.fluent.core Import launch_fluentimport ansys.fluent.core as pyfluent

 #solver = pyfluent.launch_fluent(Dimension.TWO,precision="double", processor_count=2,show_gui=True, mode="solver")
solver = pyfluent.launch_fluent(version="3d",precision="double", processor_count=16,show_gui=False, mode="solver")
c_file = "/work/home/houzhao/rev/withoutcontrol/fine600.cas"
solver.settings.file.read_case(file_name = c_file)

 #tui=solver.tui
#solver.tui.file.read_case('\work\home\houzhao\tesst\python\case.cas.h5')
#inlet = solver.settings.setup.boundary_conditions.velocity_inlet["inlet"]
 #solver.tui.define.boundary_conditions.set.velocity_inlet("velocity-inlet-l1","quit")
#inlet.momentum.velocity.value = 0.1
#solver.tui.define.models.unsteady_2nd_order("yes")
#solver.tui.solve.initialize.initialize_flow()
#solver.tui.solve.dual_time_iterate(2, 3)
resid_eqns = solver.solution.monitor.residual.equations

resid_eqns["continuity"].absolute_criteria = 1e-5
resid_eqns["x-velocity"].absolute_criteria = 1e-5
resid_eqns["y-velocity"].absolute_criteria = 1e-5
resid_eqns["k"].absolute_criteria = 1e-5
resid_eqns["omega"].absolute_criteria = 1e-5
#resid_eqns["energy"].absolute_criteria = 1e-6

solver.settings.solution.initialization.initialization_type = "standard"
solver.settings.solution.initialization.standard_initialize()
solver.settings.solution.run_calculation.transient_controls.time_step_size = 0.005
solver.settings.solution.run_calculation.dual_time_iterate(iter_countt = 10000, max_iter_per_step = 20)

solver.file.write_case_data(file_name = "result100.cas.h5")
 #with open('exportvel.txt', 'a') as file2:
           #print('%g' % velocity,file=file2)
solver.exit()
```

> 接口文档：https://fluent.docs.pyansys.com/version/stable/api/launcher/launcher.html
> 官方案例：https://fluent.docs.pyansys.com/version/stable/examples/00-fluent/external_compressible_flow.html#sphx-glr-examples-00-fluent-external-compressible-flow-py

## 脚本

# 模板
```bash
#!/bin/bash 
#SBATCH -J fluent_test #作业名称
#SBATCH -p hfacnormal02    ##队列
#SBATCH -n 60 #总进程数，等于总节点数乘以每个节点进程数
#SBATCH -N 1 #节点数
##SBATCH --ntasks-per-node=60 #每个节点进程数
#SBATCH -t 60
#SBATCH --exclusive
#SBATCH -o out.%j
#SBATCH -e err.%j


#########################软件图形窗口启动

EXEC=/public/home/acrjr7psve/soft/ansys2020R2/v202/fluent/bin/fluent
WORK_DIR=`pwd`
FLUENT_VERSION=3ddp  ## [ 3d | 3ddp | 2d | 2ddp ] 
INPUT_FILE=${WORK_DIR}/PR_fluent.jou      
LOG_FILE=${WORK_DIR}/PR_fluent.log   
### optional and advance parameters
#OPTIONAL=" "
OPTIONAL="-pmpi-auto-selected -mpi=intel -ssh"   
#########################################################

cd $WORK_DIR

PROCS=$SLURM_NPROCS
export PBS_NODEFILE=`generate_pbs_nodefile`  
sort $PBS_NODEFILE | uniq -c | awk '{print $2":"$1}' > ./hostfile_$PROCS 
HOST_FILE=./hostfile_$PROCS

echo The start time is: `date +"%Y-%m-%d %H:%M:%S"` | tee $LOG_FILE  
echo My job ID is: $SLURM_JOB_ID | tee -a $LOG_FILE 
echo The total cores is: $PROCS | tee -a $LOG_FILE 
echo The hosts is: | tee -a $LOG_FILE 
cat $HOST_FILE | tee -a $LOG_FILE

$EXEC -g $FLUENT_VERSION -t$PROCS -cnf=$HOST_FILE -i $INPUT_FILE $OPTIONAL 2>&1 | tee -a  $LOG_FILE

echo The end time is: `date +"%Y-%m-%d %H:%M:%S"` | tee -a $LOG_FILE  

```

# 图形

```bash
#!/bin/bash 
#SBATCH -J fluent_test #作业名称
#SBATCH -p hfacnormal01    ##队列
#SBATCH -n 64 #总进程数，等于总节点数乘以每个节点进程数
#SBATCH -N 1 #节点数
##SBATCH --ntasks-per-node=32 #每个节点进程数
#SBATCH --exclusive
#SBATCH -o out.%j
#SBATCH -e err.%j

#########################vnc端口号
export DISPLAY=imgr8:19
#########################软件图形窗口启动

WORK_DIR=`pwd`
FLUENT_VERSION=3ddp  ## [ 3d | 3ddp | 2d | 2ddp ] 
#INPUT_FILE=${WORK_DIR}/truck_111m.jou      
#LOG_FILE=${WORK_DIR}/truck_111m.log   
### optional and advance parameters
#OPTIONAL=" "
OPTIONAL="-pmpi-auto-selected -mpi=intel -ssh"   
#########################################################

cd $WORK_DIR

PROCS=$SLURM_NPROCS
for host in $SLURM_JOB_NODELIST;do
scontrol show hostname $host>hostfile.$SLURM_JOBID
done

/public/home/wuyuxuan/bin/ansys_inc/v201/fluent/bin/fluent $FLUENT_VERSION -t$PROCS -cnf=hostfile.$SLURM_JOBID $OPTIONAL
```


```bash
作业使用脚本提交，脚本可根据需求自行更改,提交作业时将脚本和算例放在相同路径下。

使用方式是：

提交作业：sbatch 脚本名

查看作业：squeue

取消作业：scancel 作业号 （作业号：执行squeue，jobid下面的数字）

实时查看输出：tail -f 输出文件名

-N后面的数字代表使用的节点数

-n后面的数字代表使用的总核数

--ntasks-per-node代表单节点使用的核数，不可超过单节点的核数

-p后面是队列名

最后一行是执行命令 脚本您可以根据自己的需要修改
```

```bash
# 切换 系统 hpcxmpi
export USE_HPCX_FROM_SYSTEM=1
module purge
module load compiler/devtoolset/7.3.1 mpi/hpcx/gcc-7.3.1
export HPCX_HOME=/opt/hpc/software/mpi/hpcx/v2.11.0/gcc-7.3.1
然后mpi选择openmpi


# 内置 mpi路径
/work/home/zy178/ANSYS_2021R1/ANSYS2021R1_LINX64_Disk1/ansys_inc/v211/fluent/fluent21.1.0/multiport/mpi/lnamd64


# mpi源码
https://www.open-mpi.org/software/ompi/v3.1/
# 安装好的 
/public/home/jsyadmin/tanjj/paks/mpi/openmpi/build/openmpi-3.1.6.zip # 昆山 

rayfile-c -a ksefile.hpccube.com -P 65245 -u jsyadmin -w 9380ec0f4b08ef0835-d754-479d-8c3e-f66fcbdf9228 -no-meta -symbolic-links follow -retry 10 -retrytimeout 30 -o download -s '/tanjj/paks/mpi/openmpi/build/openmpi-3.1.6.zip' -d .

./configure --prefix=/work/home/jiying002/soft/openmpi/openmpi-3.1.5/install --with-ucx=/opt/hpc/software/mpi/hpcx/v2.11.0/ucx_without_rocm/ --enable-mpi1-compatibility --with-libevent --without-xpmem --with-slurm --with-platform=contrib/platform/mellanox/optimized --enable-install-libpmix --with-hwloc=/opt/hpc/software/mpi/hwloc --with-pmix=internal --enable-orterun-prefix-by-default --enable-mpi-thread-multiple --enable-mpi-cxx

make 
make install



```

```bash
strace -T -p 进程ID
```

```bash
# 使用手册 ansys旗下所有
https://ansyshelp.ansys.com/public/account/secured
```
