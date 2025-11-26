# 安装
```bash
# 安装包 
/public/home/jsyadmin/tanjj/paks/quantumatk/source/2024.09-QATK.zip # 昆山
# build好了的
/public/home/jsyadmin/tanjj/paks/quantumatk/build # 昆山

/work/home/ac2cfhellx/software/QuantumATK2023/quantumatk/V-2023.12-SP1/atkpython/bin/quantumatk # 这个python的上面的路径要改
/work/home/ac2cfhellx/software/QuantumATK2023/quantumatk/V-2023.12-SP1/atkpython/bin/atkpython # 这个python的上面的路径要改

# 教程
https://yunhaha.net/504.html
```

```bash
export DSIPLAY=i03r4n16:9
export QT_API=pyqt5 
export QT_PLUGIN_PATH=/work/home/jiachao_ustc/software/quantumatk/W-2024.09/atkpython/lib/python3.11/site-packages/PyQt5/Qt5/plugins
export LD_LIBRARY_PATH=/work/home/jiachao_ustc/software/quantumatk/quantumatk/W-2024.09/atkpython/lib/python3.11/site-packages/PyQt5/Qt5/lib:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=/work/home/jiachao_ustc/software/quantumatk/quantumatk/W-2024.09/lib:$LD_LIBRARY_PATH

find . -iname "*libQt5Core.so.5*"

pip install pyqt5 -i https://pypi.tuna.tsinghua.edu.cn/simple  

export http_proxy='http://jsyadmin:iU2ij97C@10.15.20.1:3120'
export https_proxy='http://jsyadmin:iU2ij97C@10.15.20.1:3120'

```


# 脚本

```bash
#!/bin/bash
#SBATCH -J quantumatk
#SBATCH -p wzhcnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=32

module purge
#module load  compiler/intel/2018.5.274  mpi/intelmpi/2018.4.274
module load  compiler/intel/2021.3.0  mpi/intelmpi/2021.3.0

export MKL_DEBUG_CPU_TYPE=5
export MKL_CBWR=AVX2
export I_MPI_PIN_DOMAIN=numa
export I_MPI_FABRICS=shm
export I_MPI_OFI_PROVIDER=Verbs
#export I_MPI_PMI_LIBRARY=/opt/gridview/slurm/lib/libpmi.so
#export UCX_IB_ADDR_TYPE=ib_global

#export DISPLAY=i03r4n14:8 #linux桌面名称
export SNPSLMD_LICENSE_FILE=26123@login05
export SNPSLMD_LICENSE_FILE=/work/home/jiachao_ustc/software/quantumatk-202312/QuantumATK2023/license/Synopsys.dat

export LD_LIBRARY_PATH=/work/home/jiachao_ustc/software/quantumatk-202312/QuantumATK2023/quantumatk/V-2023.12-SP1/lib:$LD_LIBRARY_PATH
export PATH=/work/home/jiachao_ustc/software/quantumatk-202312/QuantumATK2023/quantumatk/V-2023.12-SP1/bin:$PATH

mpirun atkpython  Cropt.py >runlog </dev/null
#srun --mpi=pmi2 atkpython  Cropt.py >runlog </dev/null 
#quantumatk  < Cropt.py >runlog </dev/null
```