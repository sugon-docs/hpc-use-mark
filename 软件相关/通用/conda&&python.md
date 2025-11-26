# 1 安装

```bash
# miniconda
wget https://repo.anaconda.com/miniconda/Miniconda3-py38_23.5.1-0-Linux-x86_64.sh -i https://pypi.tuna.tsinghua.edu.cn/simple/ --no-check-certificate 
chmod +x Miniconda3-py38_23.5.1-0-Linux-x86_64.sh 
mkdir -p ./miniconda3/ 
bash Miniconda3-py38_23.5.1-0-Linux-x86_64.sh -b -f -p "./miniconda3/" 
rm -rf Miniconda3-py38_23.5.1-0-Linux-x86_64.sh
./miniconda3/bin/conda init 
source ~/.bashrc

# anaconda
wget https://repo.anaconda.com/archive/Anaconda3-2024.06-1-Linux-x86_64.sh   -i https://pypi.tuna.tsinghua.edu.cn/simple/ --no-check-certificate
chmod +x Anaconda3-2024.06-1-Linux-x86_64.sh
mkdir ./anaconda3
bash Anaconda3-2024.06-1-Linux-x86_64.sh -b -f -p "./anaconda3/"

# miniforge
wget "https://github.com/conda-forge/miniforge/releases/latest/download/Miniforge3-$(uname)-$(uname -m).sh"
chmod +x Miniforge3-$(uname)-$(uname -m).sh
mkdir -p ./miniforge 
bash Miniforge3-$(uname)-$(uname -m).sh -b -f -p "./miniforge/"
./miniforge/bin/conda init 
source ~/.bashrc

```


```bash
module load apps/singularity/3.7.3

singularity shell /public/software/apps/DeepLearning/singularity/centos7.6/ubuntu20.04-mpi4.0-gcc9.4-glibc2.35-cmake3.19-mkl-py3.10-v1.0.sif 
```
# 2 使用
### 2.1 配置清华源

```bash
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/conda-forge/
```

### 2.2 使用清华源下载更新


```bash
# conda
conda install blastn  -c https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/ 

# pip
pip install   -i https://pypi.tuna.tsinghua.edu.cn/simple 

## pip升级
pip install --upgrade pip  -i https://pypi.tuna.tsinghua.edu.cn/simple

## root使用pip忽略警告
pip install --root-user-action=ignore xxx

## pip安装指定源其他源头
pip install torch==1.13.1+cu117 torchvision==0.14.1+cu117 torchaudio==0.13.1 --extra-index-url https://download.pytorch.org/whl/cu117

## 用文件安装
pip install -r requirements.txt

## 
```

#### pip 安装 whl 包

```bash
环境要和whl module load 的一致先

pip install  pip==23.*  # 先执行这个
pip install xxxx.whl
```

### 2.3 打包

```bash
1）A账号下，conda install conda-pack -c conda-forge #先安装打包命令
2）A账号下，conda pack -n envname -o ~/envname.tar.gz #开始打包指定输出名
3）将压缩包传到B账号下
4）B账号下，mkdir envname，tar xvf envname.tar.gz -C envname/
5）B账号下，source envname/bin/activate 
6）B账号下，conda-unpack，直接使用

conda install r-ggplot2=3.0.0 --force-reinstall  # 强制重装，解决打包过去的冲突
conda remove r-*  --force -y # 卸载R 
```

### 2.4 取消自动激活

```bash
conda config --set auto_activate_base false
```

### 2.5 常用

```bash
conda create --name myclone --clone myenv  # 克隆环境
conda env remove --name myenv # 删除环境
conda remove package_name # 卸载包
conda update package_name # 更新包
conda search package_name # 搜索包
conda clean --all # 清理不需要的包
conda env create -f environment.yaml
conda create --prefix=/home/tanjj/software/Anaconda3/stall/envs/myCAE/ python=3.10.14 
```
# 3. FAQ

## 3.1 启动慢
```
在使用 conda activate 激活同一个环境时，两次耗时不一致是常见现象
1. 环境缓存状态
首次激活：需要扫描环境中的依赖项、生成缓存文件，并更新系统路径，耗时较长。
后续激活：若环境未发生变化，conda 可能直接读取缓存，耗时较短。但如果环境文件或依赖项有更新，缓存需重新生成，导致耗时增加。
2. 系统资源占用
后台进程：首次激活时，若系统正运行其他高负载任务，会导致激活速度变慢。
环境规模：若环境包含大量包，激活时需要加载更多动态链接库，耗时可能不稳定。
```
### 3.2  python 缺少 gdal 库

```bash
conda install -c conda-forge libgdal

conda install -c conda-forge gdal

conda install tiledb=2.2

conda install poppler  
  
ogrinfo --version
```