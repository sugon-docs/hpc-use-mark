# 编译安装
```bash
# sac-102.0编译
# env.sh
module purge
module load compiler/devtoolset/7.3.1   mpi/hpcx/gcc-7.3.1
export SACHOME=/work/home/acvkhdhgob/software/sac-102.0/install
export SACAUX=${SACHOME}/aux
export PATH=${SACHOME}/bin:${PATH}
export SAC_DISPLAY_COPYRIGHT=1
export SAC_PPK_LARGE_CROSSHAIRS=1
export SAC_USE_DATABASE=0

# 编译
tar -zxvf sac-102.0.tar.gz 
cd sac-102.0
mkdir install
./configure --prefix=/work/home/acvkhdhgob/software/sac-installed --enable-editline
make -j8
make install
```

> 指南如下
![[SAC_Docs.pdf]]