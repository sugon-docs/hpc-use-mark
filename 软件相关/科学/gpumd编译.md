
# dcu


- 环境
```bash
module purge
module load compiler/devtoolset/7.3.1
module load compiler/dtk/24.04
module load mpi/hpcx/2.11.0/gcc-7.3.1
```
- makefile 里
```bash
###########################################################
# some flags
###########################################################
CC = hipcc
CFLAGS = -std=c++14 -O3 --offload-arch=gfx906 -DUSE_HIP --gpu-max-threads-per-block=1024
INC = -I./
LDFLAGS =
LIBS = -lhipblas -lhipsolver
```

直接 make -j 4 就行