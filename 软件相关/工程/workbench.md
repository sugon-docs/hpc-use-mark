## workbench

### 启动命令行无需添加什么参数

### 与 maxwell（ansysem） 集成

在 maxwell 中
```bash
cd /public/home/jsyadmin/apprepo/maxwell/2021r1-none/app/AnsysEM21.1/Linux64/scripts

./IntegrateWithANSYS21.1.pl

[jsyadmin@vncserver01 scripts]$ ./IntegrateWithANSYS21.1.pl 
ANSYS Electromagnetics 21.1: Modify Integration with ANSYS 21.1
===============================================================
Would you like to add or remove the integration [add|remove|quit]? add 
*** Invalid action YES.  Please re-enter: add

Please specify the path to the ANSYS 21.1 installation directory (q to quit)
> /public/home/jsyadmin/apprepo/workbench/2021r1-none/app/v211

Created: /public/home/jsyadmin/apprepo/workbench/2021r1-none/app/v211/commonfiles/registry/linx64/AnsoftPreferences.xml
Created: /public/home/jsyadmin/apprepo/workbench/2021r1-none/app/v211/Addins/Configuration/Ansoft.xml

Integration added.


# workbench的安装路径到v211那一层  /public/home/jsyadmin/apprepo/workbench/2021r1-none/app/v211
```

测试：在 workbench 中，启动可见左侧功能树中有 `HFSS、maxwell2d、、、` 即成功

### 并行配置

![[workbench操作.pdf]]

>mechanical 里的总运行核数也要设置一下再

### 日常查看

```bash
# 可以切换到节点上
top -u $USER 查看进程
##如下
top - 14:46:28 up 116 days,  4:53,  2 users,  load average: 78.47, 79.18, 80.01
Tasks: 1340 total,  82 running, 1258 sleeping,   0 stopped,   0 zombie
%Cpu(s): 90.4 us,  9.4 sy,  0.0 ni,  0.0 id,  0.0 wa,  0.0 hi,  0.3 si,  0.0 st
KiB Mem : 26168512+total, 67930776 free, 11343240+used, 80321952 buff/cache
KiB Swap:        0 total,        0 free,        0 used. 14290713+avail Mem 

  PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND                                                                                                               
11000 shenjun+  20   0 6096436   2.1g 561736 R  94.8  0.9 180:37.88 ansys.e                                                                                                                                                                                                                           
3721 shenjun+  20   0 2034176 799056  80164 R  26.5  0.3  63:01.69 fluent_mpi.22.1                                          


# ansys.e 为结构运行进程 fluent_mpi.22.1  为流体进程
pwdx 11000 # 进程号
## 如下
11000: /work/home/shenjunyan/sjy/fsi-8.7/_ProjectScratch/Scr1A9B
## 结构日志
/work/home/shenjunyan/sjy/fsi-8.7/_ProjectScratch/Scr1A9B/solve.out
## 流体日志
/work/home/shenjunyan/sjy/fsi-8.7/fsi_files/dp0/FFF-1/Fluent/olution.trn
```

# tip

```bash
workbench双向流固耦合的话，每个模块尽量别超过3/4的核，因为计算模式是流体计算（固体休眠）-固体计算（流体休眠）-流体计算（固体休眠）... 需要留一部分给休眠程序

比如64就是固体48，流体48试试
```

# 报错

- 打开错误

![[Pasted image 20250828203819.png]]


```bash
# 遇到类似的报错 重装一下软件  优先删除  
rm -fr ~/.mw/'Application Data'/Ansys/v211 # 应该是这个有效了 
# 其他的  ~/.ansys   ~/.config/Ansys/  也可以尝试 反正就是home目录下的一些配置文件
```

- 单机错误
```bash
# 单机裸金属运行 注意 
https://zhuanlan.zhihu.com/p/445792522

# 如图，取消图中Distributed的勾选
File->[Solve Process Settings ->Advanced...，取消勾选Distribute Solution(if possible)

# mpi并行的问题

-mpi msmpi # 微软mpi

-mpi intel2018 #linux 
```
![[Pasted image 20250915130558.png]]

- system coupling 闪退
```bash
# system coupling 闪退
/ansys_inc/v201/Framework/bin/Linux64/runwb2 -oglmesa 
```

- 252 QT 库报错

```bash
# 报错
Unhandled exception caught :

System.EntryPointNotFoundException: ans_qt_Application_setPluginDirectory assembly:<unknown assembly> type:<unknown type> member:(null)
  at (wrapper managed-to-native) Ansys.UI.Toolkit.QTC.Application.ans_qt_Application_setPluginDirectory(intptr,string)
  at Ansys.UI.Toolkit.QT.Application.SetPluginDirectory () [0x000cb] in <49401e93f6e2458298921567776382b9>:0 
  at Ansys.UI.Toolkit.QT.Application..ctor () [0x00006] in <49401e93f6e2458298921567776382b9>:0 
  at Ansys.UI.Toolkit.QT.UIFactory.newApplication () [0x00000] in <49401e93f6e2458298921567776382b9>:0 
  at Ansys.UI.Toolkit.UIFactory.newApplication () [0x0000c] in <ab1cbf05066b4d5395ed2a5be5d88bcc>:0 
  at Ansys.UI.Toolkit.Application.InstantiateIApplication () [0x00000] in <ab1cbf05066b4d5395ed2a5be5d88bcc>:0 
  at Ansys.UI.Toolkit.Application..cctor () [0x0000a] in <ab1cbf05066b4d5395ed2a5be5d88bcc>:0 
--- System.TypeInitializationException: The type initializer for 'Ansys.UI.Toolkit.Application' threw an exception.
  at Ansys.UI.UIManager..ctor () [0x00055] in <87eea9da8b5d4479bad739440ab2bdde>:0 
  at Ansys.UI.UIManager.get_Instance () [0x00023] in <87eea9da8b5d4479bad739440ab2bdde>:0 
  at Ans.Program.Runtime.InitializeSplashScreen () [0x00057] in <b65a1b8249984f848ddc454acf0e29ab>:0 
  at Ans.Program.Runtime.Initialize (Ans.Program.RuntimeSettings settings) [0x0021e] in <b65a1b8249984f848ddc454acf0e29ab>:0 
  at Ans.Program.ExecutableBase.Run (Ans.Program.CommandLineParse parser) [0x0000d] in <469c1bc1266c4a148d3547bd3be89b39>:0 
  at Ans.Program.ExecutableBase.DoMain (System.String[] args) [0x000c8] in <469c1bc1266c4a148d3547bd3be89b39>:0 

# 替换这个下面的lib 用fluent 的 /work/share/hushuming_001/softs/ansys/252/ansys_inc/v252/fluent/lib/lnamd64/Qt/lib
/work/share/hushuming_001/softs/ansys/252/ansys_inc/v252/tp/qt/5.15.18/linx64
```

- mechanical 界面里 solve 会闪退，这边 update
![[Pasted image 20251106154634.png]]
- mechanical  里设置 mpi
```bash
-mpi intel2018 # 23 24版本 linux
```
![[Pasted image 20251106154843.png]]

- ![[dbf4e9b1b99c29bb0b0fc48671d3770f.png]]
```bash
export KMP_AFFINITY=disabled # 取消 OpenMP 线程与 CPU 核心的固定绑定
```