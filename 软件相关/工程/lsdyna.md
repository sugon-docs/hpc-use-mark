
## lsdyna

- ansysdyna 可以换成 lsdyna 的模板
```bash
# 2024 2023 可执行文件
/work/home/ac8ezf79f3/app/ansyslsdyna-2024r1/v241/ansys/bin/linx64/lsdyna_sp_mpp.e

# 小齿轮启动环境
module purge
module load mpi/intelmpi/2021.3.0
export LSTC_LICENSE=ansys
export ANSYSLMD_LICENSE_FILE=27890@tylogin01

# memory=1G memory2=300M 不要设置太多，会报错
```

### mpi

```bash
export FI_PROVIDER=verbs

# mpi切换直接加载就行
module purge
module load compiler/intel/2017.5.239  mpi/intelmpi/2017.4.239
## 注意目前高glibc队列无法适配 2018 2017 即一下的intelmpi环境

module load compiler/intel/2015.2.164 mpi/intelmpi/5.0.2.044 
可以
```
### r5

```bash
# 包
/public/home/jsyadmin/tanjj/soft/ls-dyna_mpp_s_R9_3_1_x64_centos65_ifort131_avx2_intelmpi-2018.zip

# 启动 
/work/home/acaumhgwu4/software/ls-dyna_mpp_s_R9_3_1_x64_centos65_ifort131_avx2_intelmpi-2018/ls-dyna_mpp_s_R9_3_1_x64_centos65_ifort131_avx2_intelmpi-2018

# license
module purge
module load compiler/intel/2017.5.239  mpi/intelmpi/2017.4.239
export LSTC_LICENSE=ansys
export ANSYSLMD_LICENSE_FILE=/work/home/acaumhgwu4/software/ls-dyna_mpp_s_R9_3_1_x64_centos65_ifort131_avx2_intelmpi-2018/shared_files/licensing/license.dat

## 或者

export LSTC_LICENSE=ansys
export ANSYSLMD_LICENSE_FILE=27890@license01
```

### 2023 r1

```bash
/work/home/acdnwvt7vw/apprepo/ansyslsdyna/2023r1-none/app/v231/ansys/bin/lsdyna231
```

## 破解
```bash
ls-dyna_mpp_s_r10_0_118302_x64_redhat54_ifort160_sse2_intelmpi-413
cp /xx/ansys2020R2/shared_files/ . -r
```
### prepost

```bash
# 包
/public/home/jsyadmin/tanjj/soft/lsprepost-4.6.23-common-11Dec2019.tgz # 昆山

# 启动


# 可执行文件
/work/home/acaumhgwu4/software/lsprepost4.6_common/lsprepost
## 这个版本缺库的话有
export LD_LIBRARY_PATH=/public/home/jsyadmin/tanjj/soft/lsprepost4.6_common/lib:$LD_LIBRARY_PATH

/work/home/aghuangzhiwei/apprepo/ansyslsdyna/2021r1-none/app/v211/ansys/bin/lsprepost
#  如果这个版本缺openjpeg库的话在
/public/home/jsyadmin/tanjj/paks/openjepg/openjpeg-origin-install.zip # 昆山
export LD_LIBRARY_PATH=/public/home/jsyadmin/tanjj/paks/openjepg/openjpeg-master/install/lib64:$LD_LIBRARY_PATH

# 用workbench的模板

```


# 使用
```bash
r=d3dump # 重启动
```

```bash
mpirun -np ${SLURM_NPROCS} $APP memory=${MEMORY}M memory2=${MEMORY2}M i=$WORK_DIR/$Filename o=$OUTPUT_FILE $CMD_OPTION_WEB  2>&1

# 不加 o= 会默认输出 d3hsp 文件 日志
```

```bash

在 LS-DYNA 仿真软件中，d3plot 文件是非常重要的输出文件之一，它通常以二进制格式存储模拟过程中的结果数据。d3plot 文件主要用于记录仿真中节点位移、单元应力、应变等信息，常用于后处理时生成云图和动画来展示仿真结果。

以下是关于 d3plot 文件的几个要点：

1. **用途**

d3plot 文件通常用于记录 LS-DYNA 仿真中的瞬态数据，包括结构变形、应力应变分布、速度等信息。它可以按照预设的时间间隔生成一系列 d3plot 文件（如 d3plot、d3plot01、d3plot02 等），每个文件包含多个仿真状态 。

2. **文件格式**

d3plot 文件是二进制文件，不是直接可读的文本格式。由于其存储的是大量仿真数据，因此可以通过 LS-PrePost 等后处理工具来打开和查看这些文件。用户还可以通过自定义程序或开源库（如 ls-reader）来读取文件中的数据 。

3. **文件处理**

为了更方便地处理 d3plot 文件，有些工具可以将 d3plot 文件转换为其他格式，比如将其转换为 HDF5 格式，以便于数据的进一步分析和处理 。LS-PrePost 还支持直接加载 d3plot 文件，进行数据的后处理与可视化 。

4. **文件限制**

LS-DYNA 中有对生成 d3plot 文件数量的限制，默认最多可生成 1000 个 d3plot 文件。如果仿真生成的文件超过了这个数量，可以通过修改配置文件来突破该限制 。

通过正确处理和分析 d3plot 文件，工程师可以深入了解仿真中结构的动态行为，评估设计的性能和安全性。
```

- dp 3 plot
![[Pasted image 20251117120225.png]]

```bash
结果文件，会输出
```

- 网格量查看
```bash
grep number d3hsp -ir 

  number of solid elements....................... 1040000

```
## 报错
-  oom 内存溢出什么的

```bash
在命令行参数添加了memory=500M memory2=300M 

# memory1通常使用内存的 30%到 70%。
# memory2通常使用 memory1分配后剩余内存的 20%到 40%
# https://blog.sina.com.cn/s/blog_88337d960101e86e.html
```


- 高版本 glibc 队列不适配，段错误
```bash

export LD_PRELOAD=/work/home/jshadmin/tanjj/TEST/lsdyna/0805/test-1/my.so  # 伟伟哥的适配库 西安 

# wzacnormal 高glibc队列 用 intel2021 往上的mpi lsdyna R12 可以
```

## 脚本
### 跨节点

smp 多线程求解只能单节点多线程最大 16 进程 
mmp 才可跨节点多进程

```bash

#!/bin/bash
#SBATCH -J lsdyna
#SBATCH -p kshcnormal
#SBATCH -N 2
#SBATCH --ntasks-per-node=32

source /public/home/jsyadmin/apprepo/ansyslsdyna/2023r1-none/scripts/env.sh
WORK_DIR=`pwd`    ##获取当前路径

job_name=${SLURM_JOB_NAME}

INPUT_FILE=input.k       ##输入文件

machines=""
for i in $(scontrol show hostnames=$SLURM_JOB_NODELIST); do
        machines=$machines:$i:$SLURM_NTASKS_PER_NODE
done
machines=${machines#:*}  ##获取节点列表

echo $machines
export LSTC_MEMORY=AUTO ##内存自适应
export LSTC_RSH=ssh


$PROGLIST -dis -lsdynampp  ncpu=${SLURM_NPROCS}   -machines "$machines" memory=1G memory2=300M  i=$INPUT_FILE  >& ${job_name}.log   #mpp多进程求解

#$PROGLIST  ncpu=${SLURM_NPROCS}  i=$INPUT_FILE   memory=1G  >& ${job_name}.log  #smp多线程求解
```


## 批量

```bash
01 02 03 lsdyna.slurm  work.sh

./work.sh
```

- work.sh
```bash
#!/bin/bash 
#SBATCH -J lsdyna    # 此行将被命令行覆盖，可删除或保留
#SBATCH -p wzacnormal   ##队列
#SBATCH -N 1   ##申请计算节点数
#SBATCH --ntasks-per-node=8   ##每节点进程数
#SBATCH --cpus-per-task=1    ##每进程占用核心数

module purge
source /work/home/acq8z3i3r5/apprepo/lsdyna/r10.single-null/scripts/env.sh

# 从脚本的第一个参数获取输入文件名[1,4](@ref)
INPUT_FILE="${1}"        ##输入文件
# 从输入文件名推导出作业名（去掉.key后缀）
JOB_NAME="${INPUT_FILE%.key}"

WORK_DIR=`pwd`    ##获取当前路径
# 使用推导出的作业名作为输出文件的一部分
OUTPUT_FILE="${WORK_DIR}/${JOB_NAME}.out"

machines=""
for i in $(scontrol show hostnames=$SLURM_JOB_NODELIST); do
        machines=$machines:$i:$SLURM_NTASKS_PER_NODE
done
machines=${machines#:*}  ##获取节点列表
echo $machines
export OMP_NUM_THREADS=1
export LSTC_MEMORY=AUTO ##内存自适应
export I_MPI_PIN_DOMAIN=numa
export LSTC_RSH=ssh
###############intel mpi相关参数
mpirun $PROGLIST i=$INPUT_FILE memory=200m

if [ $? -eq 0 ]; then
    echo "运行成功 准备移动到 ~/SampleFiles_goal/${SLURM_JOB_NAME}-${SLURM_JOB_ID} 目录下 "
    mkdir -p ~/SampleFiles_goal/${SLURM_JOB_NAME}-${SLURM_JOB_ID}
    mv $WORK_DIR/*  ~/SampleFiles_goal/${SLURM_JOB_NAME}-${SLURM_JOB_ID}/
    rm -fr $WORK_DIR
    echo "移动完成" 
else
    echo "运行失败"
fi
```

- lsdyna.slurm 运行成功会移动到另一个目录
```bash
#!/bin/bash
# 通用批量提交LS-DYNA作业脚本
# 在当前目录运行，自动处理所有子目录

# 设置slurm脚本名称（根据您的实际文件名修改）
SLURM_SCRIPT="lsdyna.slurm"

# 检查slurm脚本是否存在
if [ ! -f "$SLURM_SCRIPT" ]; then
    echo "错误：找不到slurm脚本 $SLURM_SCRIPT"
    exit 1
fi

# 遍历所有子目录
for dir in */; do
    # 获取目录名
    dir_name="${dir%/}"
    echo "处理目录: $dir_name"

    # 复制slurm脚本到子目录
    cp "$SLURM_SCRIPT" "$dir"

    # 进入目录
    cd "$dir" || continue

    # 查找所有.key文件并提交作业
    for keyfile in *.key; do
        if [ -f "$keyfile" ]; then
            # 使用.key文件的名称（不含后缀）作为作业名
            job_name="${keyfile%.key}"
            echo "为输入文件 $keyfile 提交作业，作业名为: $job_name"

            # 直接通过sbatch命令传递作业名和输入文件参数，不再需要临时脚本
            sbatch --job-name="$job_name" "$SLURM_SCRIPT" "$keyfile"
        fi
    done

    # 返回上级目录
    cd ..
    echo "----------------------------------------"
done

echo "所有作业提交完成！"
```