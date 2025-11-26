# 1 安装

## 1.1 gpu 版本

```bash
# 解压
tar xvf Amber24.tar.bz2
tar xvf AmberTools24.tar.bz2
# conda 安装
micromamba config append channels conda-forge

micromamba create -n amber24 python=3.10

micromamba activate amber24

micromamba install numpy scipy cython ipython notebook matplotlib setuptools -y

micromamba install openmpi mpi4py

micromamba install cudatoolkit-dev=11.7 -c conda-forge

micromamba install cuda-nvcc=11.7 -c nvidia

micromamba activate amber24

# 进入Amber_src
cd amber24_src/build
vim run_cmake

##
cmake $AMBER_PREFIX/amber24_src \
    -DCMAKE_INSTALL_PREFIX=/work/home/hnnky01/software/Amber/amber24-parallel-gpu/install \ # 安装位置
    -DCOMPILER=GNU  \
    -DMPI=FALSE \
    -DCUDA=TRUE \
    -DINSTALL_TESTS=TRUE \
    -DDOWNLOAD_MINICONDA=FALSE -DUSE_CONDA_LIBS=TRUE \
    -DCUDA_TOOLKIT_ROOT_DIR=/public/software/compiler/nvidia/cuda/12.1.0  \ # cuda位置
    -DPYTHON_INCLUDE_DIR=$(python3 -c "from distutils.sysconfig import get_python_inc; print(get_python_inc())")  \
    -DPYTHON_LIBRARY=$(python3 -c "import distutils.sysconfig as sysconfig; print(sysconfig.get_config_var('LIBDIR'))") \
    2>&1 | tee  cmake.log

fi
## 

#安装
./run_cmake
make install -j 16

## 最后在激活 
source /work/home/hnnky01/software/Amber/amber24-parallel-gpu/amber24/amber.sh
```