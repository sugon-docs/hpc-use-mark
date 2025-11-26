## vasp

-  `chgsum.pl` 加载
```bash
module load apps/vtstscripts/1033
```

- 赝势文件配置

```bash
vim ~/.vaspkit

# 修改一下路径至真实
VASP5                         =     .TRUE.                         # .TRUE. or .FALSE.; Set .FALSE. if you are using vasp.4.x
LDA_PATH                      =     ~/POTCAR/LDA                   #  Path of LDA potential
PBE_PATH                      =     /work/home/ac9omgachc/0630/PAW_GGA_PBE/potpaw_PBE                   #  Path of PBE potential
GGA_PATH                      =     ~/POTCAR/GGA                   #  Path of PW91 potential
```

- oom

```bash
1.一般可以独占队列，然后降低进程数，保证每进程的分配内存高点
2.如果没有设置 NPAR 和 NCORE ，能够设置的算例需要设置 （INCAR文件中）
```
