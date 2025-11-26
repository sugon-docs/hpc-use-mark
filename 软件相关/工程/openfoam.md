
## 1 安装相关

## 1 .1 自身安装

```bash
# ofv13 安装好了的
/public/home/jsyadmin/tanjj/paks/of/13/build  # 昆山
```

```bash
/../OpenFOAM-6/etc/bashrc 里

# 修改安装位置变量
export FOAM_INST_DIR=$MY_INSTALL_PATH

#update the shell environment
wmSET $FOAM_SETTINGS

cd $WM_PROJECT_DIR # 即回到OpenFoam-2.3.1
```

```bash
# v2.3.1

# 链接
https://openfoamwiki.net/index.php/Installation/Linux/OpenFOAM-2.3.1/CentOS_SL_RHEL#CentOS_7.0

##  软连接一下mpi
ln -s /usr/bin/mpicc.openmpi OpenFOAM-2.3.1/bin/mpicc
ln -s /usr/bin/mpirun.openmpi OpenFOAM-2.3.1/bin/mpirun

## 加载自己编译的openmpi要注意下面这些变量
module purge
export PATH=/usr/bin:$PATH
export LD_LIBRARY_PATH=/usr/lib/gcc/x86_64-redhat-linux/4.8.5:/usr/lib/gcc/x86_64-redhat-linux/4.8.5/32:$LD_LIBRARY_PATH

export MPI_ROOT=/gpfs/software/apps/openmpi/4.0.3/install
export PATH=$MPI_ROOT/bin:$PATH
export LD_LIBRARY_PATH=$MPI_ROOT/lib:$LD_LIBRARY_PATH
export LIBRARY_PATH=$MPI_ROOT/lib:$LIBRARY_PATH
export INCLUDE=$MPI_ROOT/include:$MPI_ROOT/include/openmpi:$INCLUDE
export CPATH=$MPI_ROOT/include:$MPI_ROOT/include/openmpi:$CPATH
export CPLUS_INCLUDE_PATH=$MPI_ROOT/include:$MPI_ROOT/include/openmpi:$CPLUS_INCLUDE_PATH

source /work/home/actm7jwqlm/soft/OpenFOAM/v2.3.1/OpenFOAM-2.3.1/etc/bashrc

## 并行编译
WM_NCOMPPROCS=10 WM_MPLIB=SYSTEMOPENMPI
./Allwmake -j 10 
nohup ./Allwmake  > "log.compile" 2>&1 &
```

```bash
# ccmToFoam
## 包在 /public/home/jsyadmin/tanjj/paks/of/libccmio-2.6.1.tar.gz # 昆山
## v20.。版本的方式 

# 要重新编译下of
cd $WM_THIRD_PARTY_DIR
./makeCCMIO libso
./makeCCMIO lib
foam
./Allwmake
app
cd utilities/mesh/conversion/ccm
./Allwmake

# 手册 https://www.gofarlic.com/techArticleDetail?noticeId=147791

## v9..版本方式

# 不用重新编译
cd $WM_THIRD_PARTY_DIR
./AllwmakeLibccmio
cd /yourPath/OpenFOAM/OpenFOAM-8/applications/utilities/mesh/conversion/Optional
./Allwmake

# 手册 https://zhuanlan.zhihu.com/p/270397232
```


```bash
# foam-extend版本如果转移的话要改mpi gcc 安装路径的环境  
# etc/bashrc 里修改安装路径     其他版本可以看 etc/config.sh
foamInstall=/work/home/ac0kp5kcqe/software/foam # 安装路径 不到foam-extend-4.1这边

# etc/prefs.sh 里 修改 环境路径
export WM_MPLIB=SYSTEMOPENMPI
export OPENMPI_DIR=/public/software/mpi/hpcx/v2.7.4/gcc-7.3.1
#export OPENMPI_DIR=/opt/hpc/software/mpi/hpcx/v2.7.4/gcc-7.3.1
export OPENMPI_BIN_DIR=$OPENMPI_DIR/bin
#
export OPENMPI_LIB_DIR="`$OPENMPI_BIN_DIR/mpicc --showme:libdirs`"
export OPENMPI_INCLUDE_DIR="`$OPENMPI_BIN_DIR/mpicc --showme:incdirs`"
export OPENMPI_COMPILE_FLAGS="`$OPENMPI_BIN_DIR/mpicc --showme:compile`"
export OPENMPI_LINK_FLAGS="`$OPENMPI_BIN_DIR/mpicc --showme:link`"

export QT_BIN_DIR=/usr/bin/
export CMAKE_DIR=/public/software/compiler/cmake-3.15.6
#export CMAKE_DIR=/public/software/compiler/cmake-3.17.2
export CMAKE_SYSTEM=1
export CMAKE_BIN_DIR=$CMAKE_DIR/bin
```


```bash
# libAcoustics-2412  链接

### 要有fftw 商城的一般没有，要下载fftw源码编译，链接到库
## fftw编译
/public/home/jsyadmin/tanjj/paks/fftw-3.3.10.tar.gz  # 哈尔滨
tar -zxvf fftw-3.3.10.tar.gz 
cd fftw-3.3.10
./configure --prefix=/work/home/ac2b2b65rz/software/share/fftw3/install   --enable-static --enable-shared CFLAGS="-fPIC" CXXFLAGS="-fPIC" 
make -j10
make install

### 一般还会差个-lrandomProcesses 库 of的 依赖fftw，所有没有装好 
cd /work/home/ac2b2b65rz/apprepo/OpenFoam_com/v2412-gcc_9.3.0_openmpi_4.1.5/app/OpenFOAM-v2412/src/randomProcesses
vim Make/options
## options 长这样，补充上面编译的fftw的位置
EXE_INC = \
    -I$(LIB_SRC)/finiteVolume/lnInclude \
    -I$(LIB_SRC)/fileFormats/lnInclude \
    -I$(LIB_SRC)/surfMesh/lnInclude \
    -I$(LIB_SRC)/meshTools/lnInclude \
    -I$(LIB_SRC)/sampling/lnInclude \
    -I/work/home/ac2b2b65rz/software/share/fftw3/install/include

LIB_LIBS = \
    -lfiniteVolume \
    -lfileFormats \
    -lmeshTools \
    -lsurfMesh \
    -lsampling \
    -L/work/home/ac2b2b65rz/software/share/fftw3/install/lib \
    -lfftw3

wmake # 不要./Allwmake 
foam  
ll platforms/linux64GccDPInt32Opt/lib/librandomProcesses.so # 就能看到

#### 最后编译 libAcoustics-2412
unzip libAcoustics-2412
cd libAcoustics-2412
vim Sources/lib/Make/options

### options长这样
fftw_root=$(FFTW_ARCH_PATH) # 我没用 直接改下面的绝对路径 # 整个变量对应是在of etc/config.sh/FFTW 里有定义 可以改成现在的export FFTW_ARCH_PATH=/work/home/ac2b2b65rz/software/share/fftw3/install

EXE_INC = \
    -I$(LIB_SRC)/fileFormats/lnInclude \
    -I$(LIB_SRC)/sampling/lnInclude \
    -I$(LIB_SRC)/transportModels \
    -I$(LIB_SRC)/transportModels/compressible/lnInclude \
    -I$(LIB_SRC)/TurbulenceModels/turbulenceModels/lnInclude \
    -I$(LIB_SRC)/TurbulenceModels/incompressible/lnInclude \
    -I$(LIB_SRC)/TurbulenceModels/compressible/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/basic/lnInclude \
    -I$(LIB_SRC)/finiteVolume/lnInclude \
    -I$(LIB_SRC)/meshTools/lnInclude \
    -I$(LIB_SRC)/functionObjects/forces/lnInclude \
    -I$(LIB_SRC)/surfMesh/lnInclude \
    -I/work/home/ac2b2b65rz/software/share/fftw3/install/include

LIB_LIBS = \
    -lcompressibleTransportModels \
    -lsampling \
    -lturbulenceModels \
    -lincompressibleTurbulenceModels \
    -lcompressibleTurbulenceModels \
    -lincompressibleTransportModels \
    -lfluidThermophysicalModels \
    -lspecie \
    -lfileFormats \
    -lfiniteVolume \
    -lmeshTools \
    -lforces \
    -lsurfMesh \
    -L/work/home/ac2b2b65rz/software/share/fftw3/install/lib \
    -lfftw3
    
```


```bash
# of-extend 
## 参考指导连接
https://forum.cfdac.com/t/topic/1236
https://blog.csdn.net/snuhxm/article/details/109338081

# 安装好的有了 在 /public/home/jsyadmin/tanjj/paks/of/foam-extend-4.1/build/foam-extend-4.1.zip  昆山 可以直接解压用 gcc7.3.0 openmpi1.8.8
## 正式源码编译
### 首先，将需要的一些源码包手动下载好，放到这边 /work/home/acyzhmmsjm/soft/openfoam/foam-extend-4.1/ThirdParty/rpmBuild/SOURCES
cd of-extend-4.1

vim etc/bashrc 
# 修改以下
foamInstall=/work/home/acyzhmmsjm/soft/openfoam # 安装路径
: ${WM_COMPILER:=Gcc}; export WM_COMPILER # 编译器
: ${WM_MPLIB:=SYSTEMOPENMPI}; export WM_MPLIB # mpi 
# mpi要用集群编译下，软件默认编译方式有问题 ，或者将集群编译好的替换掉 /work/home/acyzhmmsjm/soft/openfoam/foam-extend-4.1/ThirdParty/packages/openmpi-1.8.8/platforms/linux64GccDPInt32Opt/ 这里的 
# 注意原来的 /work/home/acyzhmmsjm/soft/openfoam/foam-extend-4.1/ThirdParty/packages/openmpi-1.8.8/platforms/linux64GccDPInt32Opt/etc/openmpi-1.8.8.sh 也要复制过来，
# 其他安装好的也类似方法替换

cd etc
cp prefs.sh-EXAMPLE prefs.sh
vim prefs.sh
#export WM_THIRD_PARTY_USE_PYFOAM_069=1
## 该注释注释，不需要的就注释

cd ../
sourcr etc/bashrc
./Allwmake.firstInstall -j10


## 并行计算算例 /work/home/acyzhmmsjm/soft/openfoam/foam-extend-4.1/tutorials/incompressible/simpleFoam/pitzDaily
## 计算脚本
#!/bin/bash
#SBATCH -J test  # 作业名称
#SBATCH -p xhacnormalc #指定分区
#SBATCH -N 2 #请求1个节点
#SBATCH --ntasks-per-node=2 #每个节点1个任务
##SBATCH --exclusive 

# foam环境激活
source /work/home/acyzhmmsjm/soft/openfoam/foam-extend-4.1/env.sh

# 计算指令
#bash ./Allrun
blockMesh
#snappyHexMesh
decomposePar -force
mpirun --mca btl self,tcp --mca btl_tcp_if_include ib0 simpleFoam  -parallel


### 插件包安装
## gklib
make config shared=1 openmp=ON cc=/opt/rh/devtoolset-7/root/usr/bin/gcc prefix=/work/home/acyzhmmsjm/soft/apps/install/gklib
make install

## metis
make config shared=1 openmp=ON cc=/opt/rh/devtoolset-7/root/usr/bin/gcc prefix=/work/home/acyzhmmsjm/soft/apps/install/metis
make install

## parmetis
make config shared=1 openmp=ON cc=/opt/rh/devtoolset-7/root/usr/bin/gcc prefix=//work/home/acyzhmmsjm/soft/apps/install/parmetis gklib_path=/work/home/acyzhmmsjm/soft/apps/install/gklib metis_path=/work/home/acyzhmmsjm/soft/apps/install/metis 
make install 
```

> openmpi-1.6.5 编译好的在昆山 `/public/home/jsyadmin/tanjj/soft/openmpi-v1.6.5-gcc4.8.5.zip`


- CFDEM

手册在 `https://www.cfdem.com/media/CFDEM/docu/CFDEMcoupling_Manual.html#install-7`

1. 加载 openfoam 环境

> 要先编译好 openfoam

2. LIGGGHTS 编译

> 再编译 LIGGGHTS

手册在  `https://zhuanlan.zhihu.com/p/664114645`
`https://www.cfdem.com/media/DEM/docu/Section_start.html#making-liggghts-r-public`

```bash
cd LIGGGHTS
git clone git://github.com/CFDEMproject/LIGGGHTS-PUBLIC.git
git clone git://github.com/CFDEMproject/LPP.git lpp

cd /.../LIGGGHTS/LIGGGHTS-PUBLIC/src/MAKE
vim Makefile.user # 取消注释 改成 AUTOINSTALLATION_VTK="ON"
cd /.../LIGGGHTS/LIGGGHTS-PUBLIC/lib/vtk && 下载vtk
cd /.../LIGGGHTS/LIGGGHTS-PUBLIC/src/

export WM_NCOMPPROCS=10 # 10核并行编译
make auto
```

> vtk 这边有 `/work/home/jsyadmin/tanjj/soft/vtk.zip`  西安

3. CFDEM 编译

env 里
```bash
export CFDHOME=/work/home/xuwanxin/softwares/CFDEM # 安装位置
export CFDEM_SRC_DIR=/work/home/xuwanxin/softwares/CFDEM/CFDEMcoupling-PUBLIC-5.x/src  # 源码位置
source /work/home/xuwanxin/apprepo/OpenFOAM_org/v5.x-gcc_9.3.0_openmpi_4.1.5_gcc9.3.0/scripts/env.sh # of激活
export LD_LIBRARY_PATH=/work/home/xuwanxin/softwares/CFDEM/LIGGGHTS/LIGGGHTS-PUBLIC/lib/vtk/build/lib:$LD_LIBRARY_PATH # vtk在LIGGGHTS前先编译好
export CFDEM_VERSION=PUBLIC
export CFDEM_PROJECT_DIR=$CFDHOME/CFDEMcoupling-$CFDEM_VERSION-$WM_PROJECT_VERSION
export CFDEM_PROJECT_USER_DIR=$CFDHOME/$LOGNAME-$CFDEM_VERSION-$WM_PROJECT_VERSION
export CFDEM_bashrc=$CFDEM_PROJECT_DIR/src/lagrangian/cfdemParticle/etc/bashrc
export CFDEM_LIGGGHTS_SRC_DIR=$CFDHOME/LIGGGHTS/LIGGGHTS-PUBLIC/src
export CFDEM_LIGGGHTS_MAKEFILE_NAME=auto
export CFDEM_LPP_DIR=$CFDHOME/LIGGGHTS/lpp/src
source $CFDEM_bashrc

export WM_NCOMPPROCS=10  # 10核编译

cfdemCompCFDEMall # 全部编译
```


- dropletInterCanteraFoam

```bash
# 先安装Cantera-2.5.0  参考链接：https://zhuanlan.zhihu.com/p/21002053665
# 直接用conda 安装，源码安装太多依赖了，费力搞不好
conda create -n cantera_dev python=3.8.2 scons numpy cython ruamel_yaml libboost h5py ipython pytest
conda activate cantera_dev
conda install -c cantera libcantera-devel=2.5.1

# 然后编译dropletInterCanteraFoam
cd /public/home/yuanzerui/dropletInterCanteraFoam/dropletInterCanteraFoam
## 环境用gcc的
cat env.sh
source /public/home/yuanzerui/apprepo/openfoam/v8-gcc9.3.0_openmpi4.1.5/scripts/env.sh 
CanteraDir="/public/home/yuanzerui/miniconda3/envs/cantera2.5.0" 
export PATH="${PATH}:${CanteraDir}/bin" 
export PYTHONPATH="${PYTHONPATH}:${CanteraDir}/lib/python3.9/site-packages" 
export LD_LIBRARY_PATH="${CanteraDir}/lib:${LD_LIBRARY_PATH}"

## 修改Make/options # 改成conda安装后的实际路径
## 其中os.h os_config.h 去源码下一下包https://github.com/adamheinrich/os.h/blob/master/src/os.h
vim ./Make/files
dropletInterCanteraFoam.C

EXE = $(FOAM_USER_APPBIN)/dropletInterCanteraFoam

vim ./Make/options
EXE_INC = \
    -w \
    -IliquidThermoTransport \
    -IupdateProperties \
    -I$(LIB_SRC)/finiteVolume/lnInclude \
    -I$(LIB_SRC)/meshTools/lnInclude \
    -I$(LIB_SRC)/sampling/lnInclude \
    -I$(LIB_SRC)/MomentumTransportModels/momentumTransportModels/lnInclude \
    -I$(LIB_SRC)/MomentumTransportModels/compressible/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/specie/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/reactionThermo/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/basic/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/chemistryModel/lnInclude \
    -I$(LIB_SRC)/ODE/lnInclude \
    -I$(LIB_SRC)/combustionModels/lnInclude \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include/cantera/base \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include/cantera/thermo \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include/cantera/transport \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include/cantera/ext \
    -I/public/home/yuanzerui/miniconda3/envs/cantera2.5.0/include/cantera/ext/fmt \
    -I/public/home/yuanzerui/dropletInterCanteraFoam/dropletInterCanteraFoam/share/os.h-master/src \
    -I/public/home/yuanzerui/dropletInterCanteraFoam/dropletInterCanteraFoam/share/os.h-master/examples/stm32f030x8

EXE_LIBS = \
    -L$(FOAM_USER_LIBBIN) \
    -L$(FOAM_LIBBIN) \
    -lfiniteVolume \
    -lfvOptions \
    -lmeshTools \
    -lsampling \
    -lspecie \
    -lreactionThermophysicalModels \
    -lfluidThermophysicalModels \
    -lchemistryModel \
    -lODE \
    -lcombustionModels \
    /public/home/yuanzerui/miniconda3/envs/cantera2.5.0/lib/libcantera_shared.so.2
    
    
## 修改  Allwmake.sh
#!/bin/bash
set -x

local_or_NSCC="${1-local}"  # local | NSCC

if [[ $local_or_NSCC == "NSCC" ]]; then
    CanteraDir_local="/public/home/yuanzerui/miniconda3/envs/cantera2.5.0"
    CanteraDir_NSCC="/public/home/yuanzerui/miniconda3/envs/cantera2.5.0"

    if [[ -d $CanteraDir_NSCC ]]; then
        sed -i "s:${CanteraDir_local}:${CanteraDir_NSCC}:g" Make/options
    else
        echo "No $CanteraDir_NSCC exits."
        exit 1
    fi
fi

cd ${0%/*} || exit 1    # Run from this directory

wmake

# 编译
./Allwmake.sh

# 运行脚本
#!/bin/bash

#SBATCH -J test # 作业名
#SBATCH -p kshcnormal # 队列名
#SBATCH -N 2 # 节点个数
#SBATCH --ntasks-per-node=2  # 每节点进程数

# of环境激活 不需要修改
source /public/home/yuanzerui/apprepo/openfoam/v8-gcc9.3.0_openmpi4.1.5/scripts/env-drop.sh 
export UCX_IB_ADDR_TYPE=ib_global  # 通信变量置于后面最好

# of运行指令 根据算例自行修改不同指令
cp "CH3OH.yaml" "tmp.yaml"
./generateMesh.sh
cp -r 0.org 0
setFields -dict "system/setFieldsDict.setDroplet"
setFields -dict "system/setFieldsDict.ignite"
decomposePar -cellDist -force  # 分区要与实际进程数一致 总进程数=节点个数*每节点进程数 与 system/decomposeParDict 里的一致
rm constant/cellDecomposition
mpirun  dropletInterCanteraFoam -parallel
```
> 手册如下：
![[Readme.pdf]]


- 高版本编译报错
```bash
# 13 icc不行要用 icx 也就是intelonempi
[jsyadmin@tylogin02 OpenFOAM-13]$ ./Allwmake -j 2
Compiling enabled on 2 cores
Allwmake /work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13
/work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13/wmake/rules/General/general:27: /work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13/wmake/rules/linux64Icc/general: No such file or directory
/work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13/wmake/rules/General/general:28: /work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13/wmake/rules/linux64Icc/c++: No such file or directory
make: *** No rule to make target `/work/home/jsyadmin/tanjj/soft/openfoam/icc21-intelmpi21/OpenFOAM-13/wmake/rules/linux64Icc/c++'.  Stop.
```

- rehotool 编译
```bash
# 手册
https://github.com/fppimenta/rheoTool/tree/master/doc

## petsc 装不上直接拉平台的
export PETSC_DIR=/work/home/aciqdjdj1s/apprepo/petsc/3.19.5-hpcx/app 
export LD_LIBRARY_PATH=/work/home/aciqdjdj1s/apprepo/petsc/3.19.5-hpcx/app/lib:$LD_LIBRARY_PATH 
## EIGEN_RHEO好装
export EIGEN_RHEO=/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/Eigen3.2.9
## hypre 也好装
 # 要链接上lib和头文件 Make/options 里 一个个改的我
-I/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/hypre-2.28.0/install/include \
-L/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/hypre-2.28.0/install/lib -lHYPRE \
export PFLAGS="-I/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/hypre-2.28.0/install/include" # 好像没用
export PLIBS="-L/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/hypre-2.28.0/install/lib -lHYPRE" # 好像没用


./Allwmake -j4

```
### 1.2 求解器编译改造

源码改造目录下有源码和 Make 目录应该
```bash
[glory27@login10 processInterfaceTransfer]$ ls -R
Make  processInterfaceTransfer.C

./Make:
files  linux64GccDPInt32Opt  options
```

```bash
# files里
processInterfaceTransfer.C #要编译的源码

EXE = $(GCFOAM_APPBIN)/processInterfaceTransfer # 编译后指定的可执行文件
```

```bash
# option 里

EXE_INC = \  # OpenFOAM-v2212/src 里找
    -I$(LIB_SRC)/transportModels \
    -I$(LIB_SRC)/transportModels/incompressible/lnInclude \
    -I$(LIB_SRC)/thermophysicalModels/specie/lnInclude \
    -I$(LIB_SRC)/finiteVolume/lnInclude \
    -I$(LIB_SRC)/meshTools/lnInclude \
    -I$(LIB_SRC)/transportModels/tw[]()oPhaseProperties/lnInclude \
    -I$(LIB_SRC)/transportModels/twoPhaseMixture/lnInclude

EXE_LIBS = \  # OpenFOAM-v2212/platforms/linux64GccDPInt32Opt/lib/ 里找
    -lincompressibleTransportModels \
    -lspecie \
    -lfiniteVolume \
    -ltwoPhaseProperties

# 可以用grep 筛查出来
```

```bash
wmake processInterfaceTransfer.C # 开始编译
wclean \ wmake clean # 清除
```

## 2 脚本相关
### 2.1 singularity 运行 openfoam

```bash
#!/bin/bash
#SBATCH -J test  # 作业名称
#SBATCH -p xahcnormal #指定分区
#SBATCH -N 1 #请求1个节点
#SBATCH --ntasks-per-node=1 #每个节点1个任务
##SBATCH -c 32 #每个任务32个线程

#-J test：作业名称
#-p xahctest：指定分区
#-N 1：请求1个节点
#--ntasks-per-node=1：每个节点1个任务
#-c 32：每个任务32个CPU核心

#source ../scripts/env.sh
#source /work/home/jsyadmin/L4/wys/shangcheng/sci_package_supplement/202503/openfoam/openfoam7_adapter_precice2.5.0_package/scripts/env.sh

export COMMAND="
cd fluid-bottom-openfoam
./run.sh &> log1 &

cd ..
cd fluid-top-openfoam
./run.sh &> log2 &

cd ..
cd solid-calculix
./run.sh &> log3 &

wait
"

srun --mpi=pmix_v3 singularity exec -B ${HOME} ${WHERE_IS_SIF} bash -c "
singularity exec -B ${HOME} ${WHERE_IS_SIF} bash -c "
source /opt/openfoam7/etc/bashrc
$COMMAND
"
```


# 3 FAQ

## mpi
```bash
# of 用低版本openmpi 1.6.5 不支持 ucx  报错如下，
--------------------------------------------------------------------------
WARNING: No preset parameters were found for the device that Open MPI
detected:

  Local host:            a03r2n05
  Device name:           mlx5_0
  Device vendor ID:      0x02c9
  Device vendor part ID: 4123

Default device parameters will be used, which may result in lower
performance.  You can edit any of the files specified by the
btl_openib_device_param_files MCA parameter to set values for your
device.

NOTE: You can turn off this warning by setting the MCA parameter
      btl_openib_warn_no_device_params_found to 0.
--------------------------------------------------------------------------

# mpirun加上这些参数，高版本openmpi不用，走ucx参数
mpirun  --mca btl self,tcp --mca btl_tcp_if_include ib0 pimpleDyMFoam -parallel > log.pimpleDyMFoam
```

- Duplicate Entry 解决办法
https://zhuanlan.zhihu.com/p/356859133

```bash
srun --mpi=pmix_v3 --exclusive -n 8 fluidSwimmingFoam -parallel > log & # 一个作业同时算多个算例 
```

- 通信
```bash
export UCX_TLS=self,tcp,rc,rc_mlx5,ud,ud_mlx5,mm,cma 
```

- A 区通信问题
```bash
export UCX_NET_DEVICES=ib0
export FI_PROVIDER=psm2

export I_MPI_FABRICS=shm:ofi
```