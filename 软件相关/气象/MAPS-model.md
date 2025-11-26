# 安装

- 编译过程
```bash
# 网址：
https://github.com/MPAS-Dev/MPAS-Model
# 环境
module purge
module load compiler/intel/2017.5.239
module load mathlib/netcdf/4.4.1-intel-2017  昆山：module load mathlib/netcdf/4.4.1/intel
module load mathlib/jasper/1.900.1-intel-2017  昆山：module load  mathlib/jasper/1.900.1/intel
module load mathlib/hdf4/4.2r1-intel-2017  昆山：module load mathlib/hdf4/4.2r1/intel
module load mathlib/zlib/1.2.11-intel-2017  昆山：module load mathlib/zlib/1.2.11/intel
module load mathlib/pnetcdf/1.12.0-impi-2017  昆山：module load  mathlib/pnetcdf/1.12.1/intel
module load mathlib/hdf5/1.8.20-intel-2017  昆山：module load mathlib/hdf5/1.8.20/intel
module load mpi/intelmpi/2017.4.239  昆山：module load mpi/intelmpi/2017.4.239
module load mathlib/libpng/1.2.50-intel-2017 昆山：module load mathlib/libpng/1.2.50/intel
module list
# 编译
cd /work/home/acsua6z5a8/software/MPAS-Model
make -j 4 intel-mpi CORE=atmosphere
# 编译成功后会在当前目录下生成atmosphere_model
# 编译其他模式（建议在新的未开封的目录下编译）：
make  -j 4  intel-mpi CORE=ocean
make -j 4  intel-mpi CORE=init_atmosphere
make  -j 4 intel-mpi CORE=landice
make  -j 4 intel-mpi CORE=seaice
make  -j 4 intel-mpi CORE=sw
# 编译完成后会形成xxx_model执行程序
# 若是想要在同一个目录下编译所有模式，可用以下命令，其他模式相同
make -j 4 intel-mpi CORE=ocean AUTOCLEAN=true
```
- 安装好的
```bash
/work/home/acsua6z5a8/software/MPAS # 乌镇 8.0.1
```
