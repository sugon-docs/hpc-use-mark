# 1 安装

## 1.1 env

```bash
export STDB=$HOME/STDB
#---the Path where you install TCAD
export TcadPATH=/work/home/acivd5aptb/soft/install2018/sentaurus/O_2018.06-SP2
#---the Path where you install SCL
export SclPATH=/work/home/acivd5aptb/soft/scl_lic/scl/2018.06-SP1
#---the  Path of your license file
#export LicPATH=/public/home/acsxed7yjc/software/Sentaurus/scl/2018.06-SP1/admin/license/license.dat
export LicPATH=27111@tylogin02
export PATH=$SclPATH/linux64/bin:$PATH
export PATH=$TcadPATH/bin:$PATH
export LM_LICENSE_FILE=$LicPATH
export SNPSLMD_LICENSE_FILE=$LicPATH
export STDB=$HOME/STDB

export PATH=/work/home/acivd5aptb/soft/install2018/sentaurus/O_2018.06-SP2/bin:$PATH
```

## 1.2 启动 license

```bash
cd /work/home/acivd5aptb/soft/scl_lic/scl/2018.06-SP1/linux64/bin && ./lmgrd -c license.dat -l tylogin02
```

# 2 使用
# 3 FAQ

# 4 脚本

## 4.1 图形

```bash
#!/bin/bash
#SBATCH -J swb
#SBATCH -p tyhcnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=4

export DISPLAY=tyadmin06:2  #linux桌面名称
source /work/home/acivd5aptb/soft/env.sh
module purge
module load compiler/intel/2017.5.239
module load mpi/intelmpi/2017.4.239


#/work/home/acivd5aptb/soft/install2018/sentaurus/O_2018.06-SP2/bin/swb
/work/share/acivd5aptb/soft/install2018/sentaurus/O_2018.06-SP2/bin/swb
```