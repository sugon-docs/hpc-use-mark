
```bash
module purge
module load compiler/intel/2017.5.239 mpi/intelmpi/2017.4.239
module load mathlib/lapack/3.8.0-intel-2017 mathlib/blas/3.5.0-intel-2017  #mathlib/fftw/3.3.8-intel-2017-double

make install INSTALL_DIR="/work/home/dengchao817/software/castep-18.1" ARCH=linux_x86_64_ifort17 COMMS_ARCH=mpi SUBARCH=mpi MATHLIBS=mkl10 FFT=mkl BUILD=fast -j 8 OPT="-O3"


Please enter directory where mkl10 LAPACK and BLAS libraries are kept:
      Hit the "return" key if these are in the default linker path.
      The value you enter will be stored and used in future compiles.
      Delete file obj/linux_x86_64_ifort17--mpi/mathlibdir.mkl10 to remove saved value.

/public/software/mathlib/blas/3.5.0/intel  /public/software/mathlib/lapack/intel/3.8.0/lib  /opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/mkl  /opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/mkl/include/ 

------------------------

module purge
module load compiler/intel/2017.5.239 mpi/intelmpi/2017.4.239
#module load mathlib/lapack/3.8.0-intel-2017  

# apprepo 下 fftw-3.3.10-intelmpi2018_double  openblas-0.3.26-intel2018

module purge
module load compiler/devtoolset/7.3.1    mpi/hpcx/gcc-7.3.1
module load lapack-3.11-gcc731   fftw-3.3.10-hpcx_double openblas-0.3.24-gcc731


export CC=mpicc
export CXX=mpicxx
export FC=mpifort

make INSTALL_DIR="/work/home/dengchao817/software/castep-18.1/install" ARCH=linux_x86_64_gfortran7.0 COMMS_ARCH=mpi SUBARCH=mpi MATHLIBS=openblas MATHLIBDIR=/work/home/dengchao817/apprepo/openblas/0.3.24-gcc731/app/lib CFFT=fftw3 FFTLIBDIR=/work/home/dengchao817/apprepo/fftw/3.3.10-hpcx_double/app/lib  LDFLAGS="-L/work/home/dengchao817/apprepo/lapack/3.11-gcc731/app/share/lib64 $LDFLAGS"  -j 8

make install INSTALL_DIR="/work/home/dengchao817/software/castep-18.1/install" ARCH=linux_x86_64_gfortran7.0 COMMS_ARCH=mpi SUBARCH=mpi MATHLIBS=openblas MATHLIBDIR=/work/home/dengchao817/apprepo/openblas/0.3.24-gcc731/app/lib CFFT=fftw3 FFTLIBDIR=/work/home/dengchao817/apprepo/fftw/3.3.10-hpcx_double/app/lib  LDFLAGS="-L/work/home/dengchao817/apprepo/lapack/3.11-gcc731/app/share/lib64 $LDFLAGS"  -j 8


make INSTALL_DIR="/work/home/dengchao817/software/castep-18.1/install" ARCH=linux_x86_64_gfortran7.0 COMMS_ARCH=mpi SUBARCH=mpi FFT=fftw3 FFTLIBDIR=/work/home/dengchao817/apprepo/fftw/3.3.10-hpcx_double/app/lib MATHLIBS=openblas MATHLIBDIR=/work/home/dengchao817/apprepo/openblas/0.3.24-gcc731/app/lib  -j 12 
# 并行编译
# 如果install时候遇到python版本问题，就用/usr/bin下的。取消conda环境

# FFTLIBDIR=/public/software/mathlib/fftw/3.3.8/double/gnu/lib
# MATHLIBDIR=/work/home/dengchao817/apprepo/openblas/0.3.24-gcc731/app/lib

mpirun -np 1 ./castep.mpi YH
```

>指南 1：https://blog.csdn.net/sowhatgavin/article/details/71271487
>指南 2：https://zhuanlan.zhihu.com/p/547223221