
# 1 安装

```bash
[hj2024@login09 procast2019]$ ls
disk  _SolidSQUAD_

[hj2024@login09 disk]$ ls
auxsh.tar  getppgdir.sh  optional   Paminst-gui.sh  paminst.tcl  required  VERSION
CDinst.sh  info          Pamgui.sh  Paminst.sh      reqs.dat     util.tar
[hj2024@login09 disk]$ ./Paminst.sh  # 非图形安装

# 一一覆盖率，可看readme_linux.txt中
[hj2024@login09 procast2019]$ cd _SolidSQUAD_/
[hj2024@login09 _SolidSQUAD_]$ ls
esiplayer    pamopt   ProCAST2019.1.Suite.Linux64-SSQ.rar  Visual-Environment
pam_lmd.lic  procast  readme_linux.txt
[hj2024@login09 _SolidSQUAD_]$

```

```bash
# 破解 配置 写到env中
export PAM_LMD_LICENSE_FILE=/work/home/hj2024/softwares/procast2019/licenses/pam_lmd.lic
export PAMHOME=/work/home/hj2024/softwares/procast2019
source ~/softwares/procast2019/env-Linux/esiplayer.Baenv
source ~/softwares/procast2019/env-Linux/pamopt.Baenv
source ~/softwares/procast2019/env-Linux/procast.Baenv
source ~/softwares/procast2019/env-Linux/quikcast.Baenv
source ~/softwares/procast2019/env-Linux/pamopteditor.Baenv
source ~/softwares/procast2019/env-Linux/psi.Baenv
source ~/softwares/procast2019/env-Linux/visualenvironment.Baenv

export PATH=/work/home/hj2024/softwares/procast2019/procast/2019.0/Linux_x86_64_2.12/visual:$PATH
```

# 2 脚本
## 图形脚本

```bash
# 图形提交脚本

#!/bin/bash
#SBATCH -J procast2019
#SBATCH -p xahcnormal
#SBATCH -N 1
#SBATCH --ntasks-per-node=32

export DISPLAY=vadmin26:9 # 需要自定义修改

source /work/home/hj2024/softwares/procast2019/env.sh

VisualCast.sh
```