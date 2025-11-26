# 1 安装

- env. sh 环境
```bash
module purge
module load compiler/intel/2017.5.239
module load mathlib/netcdf/4.4.1-intel-2017
module load mathlib/zlib/1.2.11-intel-2017
module load mathlib/pnetcdf/1.12.0-impi-2017
module load mathlib/hdf5/1.8.20-intel-2017
module load mpi/intelmpi/2017.4.239
module load mathlib/szip/2.1.1-intel-2017
export CDFF_PATH=/public/software/mathlib/netcdf/4.4.1/intel
export SZIP_PATH=/public/software/apps/szip/2.1.1/intel
export CDF_PATH=/public/software/mathlib/netcdf/4.4.1/intel
export HDF5_PATH=/public/software/mathlib/hdf5/1.8.20/intel
export HDF_PATH=/public/software/mathlib/hdf5/1.8.20/intel
export ZLIB_PATH=/public/software/mathlib/zlib/1.2.11/intel
export VER_CDF=CDF2020PARALL
export FC=/opt/hpc/software/compiler/intel/intel-compiler-2017.5.239/bin/intel64/ifort
```

- 指令
```bash
export OMP_NUM_THREADS=1
./configure
. ../conf/profile_surfex-LXgfortran-SFX-V8-1-1-MPIAUTO-O2-X0
make
make installmaster
```

- 手册
https://www.umr-cnrm.fr/surfex/spip.php?article191
