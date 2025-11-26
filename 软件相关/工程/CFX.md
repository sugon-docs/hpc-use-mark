## CFX
### 使用

- 图形启用并行

```
在define 界面
先设置各个节点及调用核数
选择 mpi 分布式的
large problem 勾选对内存有帮助

太原适合 openmpi
```

- ccl
```bash
cfx5solve -def cascade.def -fullname $basename   -ccl Poutlet101Kpa.ccl ......(接cfx脚本命令）

# -fullname $basename 指定输出文件
```

- 输出监控
```bash
/work/home/kesirong/apprepo/cfx/ansys21_linux64_ssq/Ansys.Products.2021.R1.Linux64-SSQ/install/v211/CFX/bin/cfx5mondata -varrule "CATEGORY = USER POINT ;FORCE:MOMENT" -res 1_001.res  -output ./test.txt
```


- 计算

```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5solve" -batch -def example.def -ccl example.ccl   -fullname example
```

- 加算
```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5solve" -batch -initial example.res -def example.def -ccl example.ccl   -fullname example
```

- 将结果插值到新网格并加算
```
Interpolates the solution from the initial values file, if one is supplied (using the -initial option), onto the mesh from the CFX-Solver input file, rather than using the mesh from the initial values file. This option has been deprecated and should be replaced by the -initial-file or -continue-from-file option, as appropriate.  
```

```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5solve" -batch -initial example.res -def new.def -ccl new.ccl -interp-iv  -fullname new
```

- 中途换ccl
```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5control" example.dir -inject-commands example.ccl
```

- 运行宏文件
```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5pre" -batch example.pre
```

- 取结果
```bash
"C:\Program Files\ANSYS Inc\v140\CFX\bin\cfx5mondata" -varrule "CATEGORY = USER POINT" -res example.res -out example.dat
```

- 写结果

在计算路径的`*.dir`下，新建名为`stp`的空文件，将写出`res`结果，程序停止运行。新建名为`trn`的空文件，将写出备份结果，程序继续运行。

- 指令解释官方
>https://ansyshelp.ansys.com/public/account/secured?returnurl=////////Views/Secured/corp/v242/en/cfx_solv/i1304960.html
### 报错

#### 段错误

```bash
减小每个节点上的进程数，独占，试试
换mpi
```

### CFX-Pre 图形打开错误选择文件夹闪退

```bash
LD_LIBRARY_PATH中/opt/gridview/slurm/lib 里面的环境变量影响的

echo $LD_LIBRARY_PATH

将变量写到一个新的env.sh文件里

export LD_LIBRARY_PATH=/opt/hpc/software/mpi/hpcx/v2.11.0/gcc-7.3.1/lib:/opt/hpc/software/mpi/hpcx/v2.11.0/sharp/lib:/opt/hpc/software/mpi/hpcx/v2.11.0/hcoll/lib:/opt/hpc/software/mpi/hpcx/v2.11.0/ucx_without_rocm/lib:/opt/rh/devtoolset-7/root/usr/lib64:/opt/rh/devtoolset-7/root/usr/lib:/public/software/compiler/dtk-22.04.2/hip/lib:/public/software/compiler/dtk-22.04.2/lib:/public/software/compiler/dtk-22.04.2/lib64:/public/software/compiler/dtk-22.04.2/llvm/lib:/public/software/compiler/dtk-22.04.2/opencl/lib:/opt/inotify/lib:/opt/gridview/slurm/lib64:/opt/gridview/munge/lib

启动时 soure 新的 env.sh
去掉 /opt/gridview/slurm/lib 这个库就可以

原因是CFX-pre启动时没有使用自身库，而是使用/opt/gridview/slurm/lib共享库，导致使用共享库时发生不兼容问题。
```

# 脚本

```bash
#!/bin/bash
#SBATCH -J cfx       #作业名
#SBATCH -p tyhcnormal  #填写队列名，可以在命令行执行whichpartition $USER获取
#SBATCH -N 1         #节点数
#SBATCH --ntasks-per-node=64  ##核心数

unset SLURM_GTIDS
#source /work/home/kesirong/apprepo/cfx/2023r1-none/scripts/env.sh
APP_EXEC="/work/home/kesirong/apprepo/cfx/ansys21_linux64_ssq/Ansys.Products.2021.R1.Linux64-SSQ/install/v211/CFX/bin/cfx5solve"
APP_MONDATA="/work/home/kesirong/apprepo/cfx/ansys21_linux64_ssq/Ansys.Products.2021.R1.Linux64-SSQ/install/v211/CFX/bin/cfx5mondata"
WORK_DIR=`pwd`
INPUT_FILE=${WORK_DIR}/1.def # def 文件名
#RES_FILE=${WORK_DIR}/cartest.res
CCL_FILE=${WORK_DIR}/AOA4AOS0.ccl # ccl文件名
SPECIFIED_NAME="AOA4AOS0_1"  # 自定义名称
OUTPUT_FILE="test"  # 监测输出文件名 
cd ${WORK_DIR}
NP=$SLURM_NPROCS
HOST_FILE=$(generate_pbs_nodefile)
cat ${HOST_FILE} > ${WORK_DIR}/HOST_STRING
HOST_STRING=""
for i_node in `cat ${HOST_FILE} | uniq`; do
        i_ppn=`cat ${HOST_FILE} | grep ${i_node} | wc -l`
        if [ -z ${HOST_STRING} ];then
                HOST_STRING="${i_node}*${i_ppn}"
        else
                HOST_STRING="${HOST_STRING},${i_node}*${i_ppn}"
        fi
done

echo "The hosts is ${HOST_STRING}" >> ${WORK_DIR}/HOST_STRING

export CFX5RSH=ssh

${APP_EXEC} -double -par-dist ${HOST_STRING} -start-method 'Intel MPI Distributed Parallel' -def ${INPUT_FILE} -ccl ${CCL_FILE} 2>&1
${APP_MONDATA}  -varrule "CATEGORY = USER POINT ;FORCE:MOMENT" -res ${SPECIFIED_NAME}.res  -output ${WORK_DIR}/${OUTPUT_FILE}.txt 2>&1
```