
# 编译

```bash
# 具体参考文件 前提得3rdparty里的环境得好了

export CC=/public/software/mpi/intelmpi/2021.3.0/bin/mpicc
export CXX=/public/software/mpi/intelmpi/2021.3.0/bin/mpicxx
export FC=/public/software/mpi/intelmpi/2021.3.0/bin/mpiifort

cd /work/home/ach8atla47/apprepo/PHengLEI/v2506-mpich/app/PHengLEI
mkdir build
cd build
cmake ..
make -j4
```

# 使用

```bash
# 启动环境 建议用intelmpi 或者高版本的mpich 低版本有点问题  mpich向下兼容的
module purge
#module load compiler/devtoolset/7.3.1 mpi/mpich/3.3.2-gcc-7.3.1  compiler/cmake/3.20.4
module load compiler/gcc/9.3.0  mpi/mpich/4.1.2-gcc-9.3.0  compiler/cmake/3.20.4
```
