
## 编译

```bash
源码里直接make 会输出opencfd-1.10.1a.out 可执行文件
```

## 使用

```bash
opencfd.in 里 npx0 npy0  npz0  相乘登录等于并行分块
```

```bash
#!/bin/bash 
#SBATCH -J rewrite ##作业名
#SBATCH -p wzhcnormal ##队列
#SBATCH -N 12 ##申请计算节点数
#SBATCH --ntasks-per-node 60 
#SBATCH --exclusive

ulimit -s  unlimited
ulimit -l  unlimited
export UCX_IB_ADDR_TYPE=ib_global
#export UCX_WARN_UNUSED_ENV_VARS=n

module purge
module load compiler/intel/2021.3.0    mpi/intelmpi/2021.3.0
#module load compiler/gcc/9.3.0  mpi/openmpi/4.1.5/gcc-9.3.0

#srun --mpi=pmi2 ./opencfd-1.10.1a.out
mpirun -np 720 ./opencfd-1.10.1a.out
```