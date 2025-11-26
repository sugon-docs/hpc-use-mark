
# 安装


- 手册：https://ibamr.github.io/building

- 环境

```bash
module purge
module load  compiler/gcc/9.3.0   mpi/openmpi/4.1.5/gcc-9.3.0  compiler/cmake/3.23.3
```

- petsc-3.23.3 安装

```bash
#!/bin/bash
cd /work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3
unset PETSC_DIR
unset PETSC_ARCH

./configure --force --COPTFLAGS='-O2 -g'--CXXOPTFLAGS='-O2 -g' --FOPTFLAGS='-O2 -g' --with-shared-libraries=1 --download-hdf5=1 --download-hypre=1 --download-openmpi=0 --download-openblas=1  --download-metis=1 --download-parmetis=1 --download-silo=1 --with-x=0 --with-64-bit-indices=0 --with-fortran-bindings=0 --with-mpi=1 --with-debugging=0 --prefix=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/install

make -j4
make -j4 install

cp -fr install/* arch-linux-c-opt/
```

- libmesh-1.7.8安装
```bash
cd /work/home/acpyofyhad/software/libmesh-1.7.8/libmesh-1.7.8
export PETSC_DIR=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3
export PETSC_ARCH=arch-linux-c-opt 
mkdir  build-debug 
cd  build-debug  
./configure --prefix=/work/home/acpyofyhad/software/libmesh-1.7.8/libmesh-1.7.8/build-debug --with-methods=devel --enable-exodus -enable-triangle --enable-petsc-required --disable-boost --disable-eigen --disable-hdf5  --disable-metaphysicl  --disable-openmp  --disable-perflog --disable-pthreads -disable-tbb  -disable-timestamps --disable-reference-counting  --disable-strict-lgpl --disable-glibcxx-debugging --disable-vtk  --with-thread-model=none
```

- SAMRAI 安装
```bash
cd /work/home/acpyofyhad/software/SAMRAI/IBSAMRAI2-2025.09.12

mkdir -p build-debug
cd build-debug

./configure  --with-CC=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpicc --with-CXX=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpicxx --with-F77=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpif90  --with-hdf5=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/arch-linux-c-opt CFLAGS="-fPIC -g -O2" CXXFLAGS="-fPIC -g -O2" FFLAGS="-fPIC -g -O2" --prefix=/work/home/acpyofyhad/software/SAMRAI/IBSAMRAI2-2025.09.12/build-debug --without-petsc --without-hypre --without-blaslapack --without-cubes --without-eleven --without-petsc --without-sundials --without-x --with-doxygen --enable-debug --disable-opt --enable-implicit-template-instantiation --disable-deprecated

make -j4
make -j4 install

```

- IBAMR-0.17.0 安装
```bash
cd /work/home/acpyofyhad/software/IBAMR-0.17.0
mkdir -p build-debug
cd build-debug

cmake -DCMAKE_C_COMPILER=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpicc -DCMAKE_CXX_COMPILER=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpicxx -DCMAKE_Fortran_COMPILER=/public/software/mpi/openmpi/4.1.5/gcc-9.3.0/bin/mpif90 -DPETSC_ROOT=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/arch-linux-c-opt -DHDF5_ROOT=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/arch-linux-c-opt -DHYPRE_ROOT=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/arch-linux-c-opt -DSILO_ROOT=/work/home/acpyofyhad/software/petsc-3.23.3/petsc-3.23.3/arch-linux-c-opt -DCMAKE_BUILD_TYPE=Debug  -DSAMRAI_ROOT=/work/home/acpyofyhad/software/SAMRAI/IBSAMRAI2-2025.09.12/build-debug -DLIBMESH_ROOT=/work/home/acpyofyhad/software/libmesh-1.7.8/libmesh-1.7.8/build-debug -DLIBMESH_METHOD=DEVEL -DCMAKE_INSTALL_PREFIX=/work/home/acpyofyhad/software/IBAMR-0.17.0/build-debug -DIBAMR_FORCE_BUNDLED_BOOST=ON -DIBAMR_FORCE_BUNDLED_EIGEN3=ON -DIBAMR_FORCE_BUNDLED_MUPARSER=ON ..

make -j4 install
```