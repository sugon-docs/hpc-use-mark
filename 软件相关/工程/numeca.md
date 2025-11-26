

## 设置

###  9.3.0 mpi 并行

```bash
# 老版本 openmpi1.6.3重新编译

# 不一定有用
export OMPI_MCA_btl=self,tcp
export OMPI_MCA_btl_tcp_if_include=ib0    # 等效于  mpirun  --mca btl self,tcp --mca btl_tcp_if_include ib0 xxx
或者
# 不一定有用
export OMPI_MCA_btl=^tcp
或者 
# 这个有用
export OMPI_MCA_pml=ob1
export OMPI_MCA_btl="self,tcp,sm"    
```
### 并行

```bash
# 并行
cd /work/share/acur7jkpih/numeca/COMMON/
./configure 

# 脚本

#!/bin/bash
#SBATCH -J test
#SBATCH -N 2 
#SBATCH -n 32
#SBATCH -p xahcnormal
#SBATCH -o %x_%j.log

rm /tmp/pvm* -fr # 删除计算节点上的残留进程文件 要加，对跨节点要求

export DISPLAY=vadmin33:15
unset SLURM_GTIDS
#export NI_LM_LICENSE_FILE=26001@login08
export NUMECA_LICENSE_FILE=/work/share/acur7jkpih/numeca/numeca_SSQ.dat
export CENAERO_LICENSE_FILE=/work/share/acur7jkpih/numeca/numeca_SSQ.dat
export OPENENG_LICENSE_FILE=/work/share/acur7jkpih/numeca/numeca_SSQ.dat
#ulimit -s unlimited

# 这个必要加
export NI_RSHCMD=ssh
export NI_DRIVER=X11  
export NI_DOUBLEBUFFERING=ON

export PATH=/work/share/acur7jkpih/numeca/bin:/work/home/yuanshilong/Test:$PATH
#export PATH=/work/home/yuanshilong/soft/numeca/16.1/bin:$PATH #安装路径
#########################软件图形窗口启动命令
#/work/share/acur7jkpih/numeca/bin/fine161  -print
fine161 -print
#########################占用计算节点时长
sleep 100h ####单位是小时
```

https://max.book118.com/html/2020/1218/8103117073003027.shtm
![[Pasted image 20251117131319.png]]

![[Pasted image 20250903003752.png]]


## 使用

```bash
# autogrid
/work/share/acur7jkpih/numeca/bin/igg161  -batch -autogrid5 -script "/public/home/acxgm05emo/EZK/TURBINE/python-test/automesh/automesh.py" 
```
## 报错
### mpi 报错
```bash
进程数不能太多独占64核数 40核是极限
会报段错误，最后导致mpi init错误
```

### 分块报错
![[Pasted image 20250903142608.png]]
```bash
最好选用这种软件自动分块的方式，手动设置不太对的话容易报错
```
