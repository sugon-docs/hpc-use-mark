# 安装

```bash
module purge
module load compiler/gcc/9.3.0 compiler/intel/2021.3.0 mpi/openmpi/4.1.5-gcc9.3.0

./configure --prefix=/public/home/calypso_lx/soft/qe7.0-gcc -enable-openmp FCFLAGS=-march=core-avx2 CFLAGS=-march=core-avx2 FFLAGS=-march=core-avx2
make all install

export PATH=/work/home/calypso_lx/software/q-e-qe-7.0/bin:$PATH
export LD_LIBRARY_PATH=/public/software/compiler/intel-compiler/2021.3.0/mkl/lib/intel64:$LD_LIBRARY_PATH
```