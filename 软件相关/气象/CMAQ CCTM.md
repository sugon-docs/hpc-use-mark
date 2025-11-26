
# 编译

```bash
# 环境加载
module purge
module load mpi/intelmpi/2017.4.239 ncl-6.6.2-gcc4.8.5 mathlib/netcdf/4.4.1/intel compiler/intel/2017.5.239


# 注意 intel的 要加 -march=core-avx2 makfile 里
cd /work/home/ac88tkxoi9/CMAQ/CMAQ-5.5/CCTM/scripts 
./bldit_mech.csh

## 每次编译都会生成一个新的文件夹，里面也有makefile 可以直接在里面make
### -xHost 与 -march=core-avx2 冲突，删掉-xHost

```