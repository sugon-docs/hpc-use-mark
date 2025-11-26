# 安装

```bash
module purge
module load  compiler/devtoolset/7.3.1   mpi/hpcx/2.11.0/gcc-7.3.1  
```
- hypre
```bash
# 下载地址
https://github.com/hypre-space/hypre/releases

cd /work/home/actvcl7age/LHY/hypre-2.28.0/src
export CFLAGS="-fPIC"
export CXXFLAGS="-fPIC"
./configure --prefix=/work/home/aciqdjdj1s/OpenFOAM/aciqdjdj1s-9/ThirdParty/hypre-2.28.0/install F77=/public/software/compiler/gnu/gcc-9.3.0/bin/gfortran
make -j4 all
make install
```
- DIVEMesh
```bash
cd /work/home/actvcl7age/LHY/DIVEMesh
make -j4
```
- REEF3D
```bash
cd /work/home/actvcl7age/LHY/REEF3D-master
vim Makefile # HYPRE_DIR := /work/home/actvcl7age/LHY/hypre-2.28.0/install
make -j4
```
