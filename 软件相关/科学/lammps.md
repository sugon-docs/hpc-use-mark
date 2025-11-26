
# gpu 版本编译

```bash
module purge
module load compiler/intel/2017.5.239
module load mpi/intelmpi/2017.4.239
module load fftw-3.3.8-intelmpi2017_double
module load compiler/cmake/3.23.3
module load nvidia/cuda/11.8

cd /../lammps
mkdir build install
cd build 


cmake -DBUILD_SHARED_LIBS=ON -DCMAKE_CXX_COMPILER=icpc -DCMAKE_C_COMPILER=icc -DMPI_C_COMPILER=mpiicc -DMPI_CXX_COMPILER=mpiicpc -DCMAKE_INSTALL_PREFIX=/public/home/boxu_jiang/lammps/lammps-29Aug2024/install -DCMAKE_CXX_FLAGS=-qopenmp -DCMAKE_C_FLAGS=-qopenmp -DCMAKE_Fortran_FLAGS=-qopenmp -DBUILD_MPI=yes -DBUILD_OMP=yes -DBUILD_EXE=yes -DFFT=FFTW3 -DFFTW3_INCLUDE_DIR=/public/home/boxu_jiang/apprepo/fftw/3.3.8-intelmpi2017_double/app/include -DFFTW3_LIBRARY=/public/home/boxu_jiang/apprepo/fftw/3.3.8-intelmpi2017_double/app/lib/libfftw3.so -D FFTW3_OMP_LIBRARY=/public/home/boxu_jiang/apprepo/fftw/3.3.8-intelmpi2017_double/app/lib/libfftw3_omp.so -DFFT_FFTW_THREADS=on  -DCMAKE_Fortran_COMPILER=ifort -DMPI_Fortran_COMPILER=mpiifort -DPKG_GPU=yes -DGPU_API=cuda -DGPU_PREC=mixed -DGPU_ARCH=sm_86 -DCUDPP_OPT=yes -C ../cmake/presets/most.cmake ../cmake/presets/nolib.cmake -DCMAKE_TUNE_FLAGS=-march=core-avx2 -DLAMMPS_MACHINE=mpi ../cmake/

make -j 8

# 昆山
cmake -DBUILD_SHARED_LIBS=ON -DCMAKE_INSTALL_PREFIX=/public/home/boxu_jiang/lammps/lammps-29Aug2024/install -DFFTW3_INCLUDE_DIR=/public/home/boxu_jiang/apprepo/fftw/3.3.8-intelmpi2017_double/app/include -DFFTW3_LIBRARY=/public/home/boxu_jiang/apprepo/fftw/3.3.8-intelmpi2017_double/app/lib/libfftw3.so -DBUILD_MPI=yes -DBUILD_TOOLS=yes -DFFT=FFTW3 -C  ../cmake/presets/most.cmake -C ../cmake/presets/kokkos-cuda.cmake ../cmake -DCMAKE_TUNE_FLAGS=-march=core-avx2 -DLAMMPS_MACHINE=mpi -DPKG_GPU=yes -D GPU_API=cuda -D GPU_PREC=double -D GPU_ARCH=sm_86 
```