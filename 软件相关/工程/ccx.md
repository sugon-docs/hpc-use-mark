
### ccx 

#### 编译
```bash

# 环境
module purge
module load compiler/gcc/9.3.0 mpi/openmpi/4.1.5/gcc-9.3.0 compiler/cmake/3.23.3
#source /public/software/compiler/intel/oneapi/oneapi-2023.2.0/setvars.sh --force # 链接intelmkl不用全部加载防止环境影响

export MKL_ROOT=/public/software/compiler/intel/oneapi/oneapi-2023.2.0/mkl/latest # 链接intelmkl
export MKL_INCLUDE=$MKL_ROOT/include # 链接intelmkl
export MKL_LIBS==$MKL_ROOT/lib # 链接intelmkl

export Eigen3_ROOT=/work/home/yyxiao/preCICE-CalculiX/req/eigen-3.4.0

export BOOST_ROOT=/work/home/yyxiao/preCICE-CalculiX/req/boost_1_71_0/install
export LIBRARY_PATH=$BOOST_ROOT/lib:$LIBRARY_PATH
export LD_LIBRARY_PATH=$BOOST_ROOT/lib:$LD_LIBRARY_PATH
export CPLUS_INCLUDE_PATH=$BOOST_ROOT/include:$CPLUS_INCLUDE_PATH

export LIB_XML2_ROOT=/work/home/yyxiao/miniconda3
export LIBRARY_PATH=$LIB_XML2_ROOT/lib:$LIBRARY_PATH
export LD_LIBRARY_PATH=$LIB_XML2_ROOT/lib:$LD_LIBRARY_PATH
export CPLUS_INCLUDE_PATH=$LIB_XML2_ROOT/include:$CPLUS_INCLUDE_PATH
export PKG_CONFIG_PATH=$LIB_XML2_ROOT/lib/pkgconfig:$PKG_CONFIG_PATH

#export PKG_CONFIG_PATH=/work/home/yyxiao/preCICE-CalculiX/precice-3.0.0/install/lib64:$PKG_CONFIG_PATH
export PETSC_DIR=/work/home/yyxiao/preCICE-CalculiX/req/petsc-3.22.4
export PETSC_ARCH=arch-linux-c-opt
export CPLUS_INCLUDE_PATH=$PETSC_DIR/$PETSC_ARCH/include:$CPLUS_INCLUDE_PATH
export LD_LIBRARY_PATH=$PETSC_DIR/$PETSC_ARCH/lib:$LD_LIBRARY_PATH
export PKG_CONFIG_PATH=/work/home/yyxiao/preCICE-CalculiX/req/petsc-3.22.4/arch-linux-c-opt/lib/pkgconfig:$PKG_CONFIG_PATH

export PKG_CONFIG_PATH=/work/home/yyxiao/soft/precice-3.2.0/build/lib/pkgconfig:$PKG_CONFIG_PATH
#export PKG_CONFIG_PATH=/work/home/yyxiao/preCICE-CalculiX/precice-3.0.0/install/lib64/pkgconfig:$PKG_CONFIG_PATH
#export LD_LIBRARY_PATH=/work/home/yyxiao/preCICE-CalculiX/precice-3.0.0/install/lib64:$LD_LIBRARY_PATH

export LD_LIBRARY_PATH=/work/home/yyxiao/soft/precice-3.2.0/build:$LD_LIBRARY_PATH
#export LD_LIBRARY_PATH=/work/home/yyxiao/preCICE-CalculiX/precice-3.0.0/build:$LD_LIBRARY_PATH

export LD_LIBRARY_PATH=/work/home/yyxiao/preCICE-CalculiX/req/yaml-cpp-master/build:$LD_LIBRARY_PATH
export PATH=/work/home/yyxiao/soft/calculix/CalculiX-oneapi-mt/CalculiX/ccx_2.20/src:$PATH

export PATH=/work/home/yyxiao/soft/calculix/calculix-adapter-oneapi/bin:$PATH

#source /work/home/yyxiao/OpenFOAM-7/OpenFOAM-7/etc/bashrc
source ~/soft/OpenFOAM-v2412/etc/bashrc

# 编译
cd /work/home/yyxiao/soft/calculix/CalculiX-oneapi-mt/CalculiX/ccx_2.20/src
make -f Makefile_MT
```

这是 makefile
![[Makefile_MT.txt]]
## precice

### 编译
```bash
# 和上面ccx一套可以
cd /work/home/yyxiao/soft/precice-3.2.0/build

cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=/work/home/yyxiao/soft/precice3.2/precice-3.2.0/install -DPRECICE_FEATURE_PETSC_MAPPING=OFF -DPRECICE_FEATURE_PYTHON_ACTIONS=OFF .. -DCMAKE_PREFIX_PATH=/work/home/yyxiao/soft/boost;/work/home/yyxiao/soft/eigen-3.4.0
```

## calculix-adapter

### 链接 ccx precice

```bash
# 环境
module purge
module load compiler/gcc/9.3.0 mpi/openmpi/4.1.5/gcc-9.3.0 compiler/cmake/3.23.3
#source /public/software/compiler/intel/oneapi/oneapi-2023.2.0/setvars.sh --force # 链接intelmkl不用全部加载防止环境影响

export MKL_ROOT=/public/software/compiler/intel/oneapi/oneapi-2023.2.0/mkl/latest # 链接intelmkl
export MKL_INCLUDE=$MKL_ROOT/include # 链接intelmkl
export MKL_LIBS==$MKL_ROOT/lib # 链接intelmkl
# 编译
cd /work/home/yyxiao/soft/calculix/calculix-adapter-oneapi
make -j8
```
makefile 如下
![[Makefile (2).txt]]

https://calculix.discourse.group/t/undefined-reference-to-pardiso-with-intel-mkl-oneapi-toolkit/373
### 测试使用脚本

```bash

```




/opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/compiler/lib/intel 64_lin:
/opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/mkl/lib/intel 64_lin:
/opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/tbb/lib/intel 64_lin 