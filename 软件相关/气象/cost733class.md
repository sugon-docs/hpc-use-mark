
# 安装

```bash
module purge
module load compiler/intel/2017.5.239 mpi/intelmpi/2017.4.239
module load mathlib/jasper/1.900.1/intel
module load mathlib/netcdf/4.6.2/intel
module load mathlib/pnetcdf/1.12.1/intel
module load mathlib/libpng/1.2.50/intel
module load mathlib/openjpeg/2.3.1/intel

mkdir build install
cd build
../configure --disable-option-checking '--prefix=/public/share/acbxpexjrd/software/cost733class-1.4-main/install'  'FC=ifort' 'CC=icc' 'FCFLAGS=-openmp -O3 -mp -heap-arrays 1024' --with-jasper=/public/software/mathlib/jasper/1.900.1/intel/lib  --with-openjpeg=/public/software/mathlib/openjpeg/2.3.1/intel/lib 
```