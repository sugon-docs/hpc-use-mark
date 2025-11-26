## 1 安装

```bash
# 下载wanniertools和arpack的安装包
wget https://github.com/opencollab/arpack-ng/archive/refs/heads/master.zip
module purge
module load compiler/intel/2017.5.239 mpi/intelmpi/2017.4.239
module load mathlib/lapack/3.8.0-intel-2017 mathlib/blas/3.5.0-intel-2017

# 安装arpack
sh bootstrap
mkdir install build
cd build
export CC=mpicc
export CXX=mpicxx
export FC=mpiifort
export LDFLAGS="-L/public/software/mpi/intelmpi/2017.4.239/intel64/lib $LDFLAGS"
../configure --prefix=/work/home/acsqkb8d1l/temp/arpack-ng-master/install --enable-mpi
make
make check
make install

cd wannier_tools-2.7.0/src

# 修改Makefile.intel-mpi中
F90  = mpiifort -fpp -DMPI -DINTELMKL -fpe3 -DARPACK -DINTELMKL
CC = mpiicc -cpp -O3 -DINTELMKL
# ARPACK LIBRARY
ARPACK= -L/work/home/acsqkb8d1l/temp/arpack-ng-master/install/lib -larpack -lparpack

cp Makefile.intel-mpi Makefile
make
```

# 2 脚本

```bash
 #!/bin/bash
#SBATCH -J wannier-tools
#SBATCH -N 1
#SBATCH --ntasks-per-node=64
#SBATCH -p tyhcnormal
module purge
module load compiler/intel/2017.5.239 mpi/intelmpi/2017.4.239
module load mathlib/lapack/3.8.0-intel-2017 mathlib/blas/3.5.0-intel-2017

export LD_LIBRARY_PATH=/work/home/acsqkb8d1l/temp/arpack-ng-master/install/lib:$LD_LIBRARY_PATH
mpirun -np 64 /work/home/acsqkb8d1l/temp/wannier_tools-2.7.0/bin/wt.x
```
