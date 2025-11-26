## matlab
## 安装

> 软件链接：https://blog.csdn.net/Alex497259/article/details/129865457
> 许可管理下载：https://ww2.mathworks.cn/support/install/license_manager_files.html
> 许可安装：https://ww2.mathworks.cn/matlabcentral/answers/389888-matlab

```bash
# 软件本身
## 图形安装 
./install  后选择 license.lic 

# 许可启动
license.lic 里最上面两行改下 
第一行加 端口 hostid 编辑license.lic以包括SERVER和行
第二行加DAEMON  指定路径到许可管理器的 mim可执行文件路径
下好的许可管理器有etc sys 放到matlab 安装好的路径下就行 
etc下 ./lmgrd -c license.lic -l log.xxx

# 激活
cd /public/software/matlab/inuse/2021b/matlab2021/bin
./matlab -desktop
或
./matlab -nodesktop -nosplash  -nodisplay 
选择到lic所在
```

- 启动
```bash
# 图形
cd /public/software/matlab/inuse/2021b/matlab2021/bin
./matlab -desktop

# 命令行
./matlab -nodesktop -nosplash  -nodisplay 
```
### 并行

设置这个就够了
![[Pasted image 20251030172108.png]]
```bash
-p Hygon_7495 --ntasks-per-node=20  # 
```

-  用80核心，在4个节点上，每节点20核心跑的设置
![[51101ca062b0ffc8e35dafbfd611b8d0.png]]
![[725d341aa31119b225e4f47c182e9cfe.png]]

![[MATLAB-parfor并行设置(1).pdf]]
```bash
matlab并行池是m文件里面调用的

https://hpc.scut.edu.cn/docs/software/list/apps/MATLAB.html
```

请教下 tyhctest 队列申请 4 个节点 256 个核，应该怎么在 matlab 里设置这个

![[Pasted image 20250624174411.png]]

```
https://ww2.mathworks.cn/help/matlab-parallel-server/customize-behavior-of-sample-plugin-scripts.html

Name:Partition
Value:tyhctest
```

#### comsol 与 matlab

![[comsol与matlab联用.docx]]
### gpu 调用

- slurm 脚本
```bash
#!/bin/bash
#SBATCH -p xhhgnormal01
#SBATCH -N 1
#SBATCH -n 8
#SBATCH -J gpu_test
#SBATCH --gres=gpu:1
#SBATCH -x c06r04
jobid=$SLURM_JOB_ID
echo "current jobid is $jobid"
#source /work/home/erpang/apprepo/matlab/2024a-none/scripts/env.sh
source /work/home/erpang/apprepo/matlab/2023b-none/scripts/env.sh
module load nvidia/cuda/12.1
$PROGLIST -nodesktop -nosplash  -nodisplay -r jsytest
echo "DW_batch_1.slurm shut down, $(date)"
scancel $jobid
```
- m 文件
```bash
clc

num_iter = 100000;

gpuDeviceCount("available")

gpuDeviceTable

canUseGPU

numGPUs = gpuDeviceCount("available");
% parpool(numGPUs);
parpool("Processes",numGPUs);

N = 200000;
numSimulations = 100;
numIterations = 1000;
X = zeros(numSimulations,N,"gpuArray");
r = gpuArray.linspace(0,4,N);

parfor i = 1:numSimulations
    D = gpuDevice;
    fprintf(1,"%i-th Simulations, Device %i has ComputeCapability %s \n", ...
        numSimulations,D.Index,D.ComputeCapability)

    X(i,:) = rand(1,N,"gpuArray");
    for n=1:numIterations
        X(i,:) = r.*X(i,:).*(1-X(i,:));
    end
end

% figure
% plot(r,X,'.',MarkerSize=1)
% xlabel("Growth Rate")


delete(gcp("nocreate"));
```

###   matlab 的 Mosek 调用

- 参考 `https://zhuanlan.zhihu.com/p/565512257`

```bash
set-path 里添加 Mosek的路径到  /mosek/10.0/toolbox/r2017a


Mosekdiag # 验证是否成功
```

### DeepLearnToolbox 调用

- 参考：https://blog.csdn.net/XXT2018/article/details/113823523
```bash

rayfile-c -a ksefile.hpccube.com -P 65245 -u jsyadmin -w 9380ec0f4b250b13da-360f-47d9-aae0-f720a016398b -no-meta -symbolic-links follow -retry 10 -retrytimeout 30 -o download -s '/tanjj/other-kehu/DeepLearnToolbox-master.zip' -d .

addpath(genpath('/work/home/ac03rfw8pb/share/DeepLearnToolbox-master'))

```
![[Pasted image 20250815155251.png]]

### CST-MATLAB 联合仿真

> 链接: https://zhuanlan.zhihu.com/p/697501573


```
```

## 使用

- 图片输出
```bash
# 图片输出
https://zhuanlan.zhihu.com/p/621519482
```

- 海光机器，blas 库缺失
![[0.png]]
```bash
cd /public/home/user/matlab-xxx/bin/glnxa64
修改文件：vim lapack.spec 和 vim blas.spec
改成：HygonGenuine Family * Model * mkl.so mklcompat.so 
```

- 修改成中文
```bash
locale -a # 列出所有支持的语言
vim ~/.bashrc
export LANG="zh_CN.UTF-8"
export LANG="en_US.UTF-8"
# windows
MWLOCALE_TRANSLATED=ON # 中文
MWLOCALE_TRANSLATED=OFF # 英文
```

- m文件中文乱码
```bash
# 查看本地matlab  m文件的编码格式
slCharacterEncoding() 
# 设置成UTF-8
slCharacterEncoding('UTF-8')
# 都设置成UTF-8的，集群上也一样设置下UTF-8
```