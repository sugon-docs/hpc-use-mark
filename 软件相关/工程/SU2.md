
# 安装

- 手册：https://su2code.github.io/docs_v7/Build-SU2-Linux-MacOS/
- 环境

```bash
module purge 
#module load  compiler/intel/2021.3.0  mpi/intelmpi/mpi/2021.3.0 
module load compiler/devtoolset/7.3.1 mpi/hpcx/2.11.0/gcc-7.3.1
#module load  compiler/gcc/9.3.0 mpi/openmpi/openmpi-4.1.5-gcc9.3.0
source ~/miniconda3/bin/activate base
pip install swig mpi4py -i https://pypi.tuna.tsinghua.edu.cn/simple 
```

```bash
./meson.py setup build -Denable-autodiff=true --prefix=/public/home/jsyadmin/tanjj/paks/SU2/build/SU2-master/install
./ninja -C build install
```

>export CPATH=/path/to/your/mpi/include:$CPATH
>报找不到某头文件.h 用这个加路径就行

- 安好的
```bash
/public/home/jsyadmin/tanjj/paks/SU2/build/SU2_0.8.3_build.zip # 昆山
```