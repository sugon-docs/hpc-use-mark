# 安装
![[VPS_2023-0_InstallationGuide.pdf]]

# 使用

## 参数

```bash
/work/home/ssct0070t/software/2021.06/vpsolver/2021.06/Linux_x86_64/bin

[ssct0070t@login02 bin]$ ./pamcsm --help 
usage: pamcsm [-h] [-man] [--version] [--verbose] [-cfg CFG] [-n N] [-nt NT] [-sp] [-dp] [--solverdir DIR] [--workdir DIR] [-s STAGES] [--time] [-d] [-c] [-x] [-pyvers PYVERS] [-userpython USERPYTHON] [-np NP] [-cf HOSTFILE] [--hostfile_n HOSTFILE_N] [-mpi MPI]
              [-mpidir DIR] [-mpiext MPIEXT] [-bind] [-nobind] [-g {gdb,valgrind,valgrind_full,ddt,map}] [-gdir DIR] [-gdbbpf GDBBPF] [-gdbid GDBID] [-gvsf GVSF] [-autocfg] [--stagesid STAGESID] [--arch ARCH] [--token] [--rsrvtok RSRVTOK] [--datacheck DATACHECK]
              [-lic {CRASHSAF,MEDYSA,SOLVER_SEAT,STAMP,IVSTAMP,FORM,V5ESIIV,ASSEMBLY,RTM,DIST,CAST,VA,MACHINING,3DPRINT,VPS_TNT,MF_TNT,COMP_TNT}] [-aes AES] [-aesopt AESOPT] [-aeslayout AESLAYOUT] [-pamarg ...] [--visualcheck VISUALCHECK] [-fp FP]
              [inputfile]

ESI solver launcher

positional arguments:
  inputfile             Solver input file.

optional arguments:
  -h, --help            show this help message and exit
  -man                  Show this help message and exit
  --version             Prints launcher version information.
  --verbose, -v         Verbose output from solver.
  -cfg CFG              Configuration file for multi-stage cases. Examples of CFG files and an explanation of their structure are located at the bottom of this help.
  -n N                  Number of cores; alternative to -np/-nt where the best configuration is chosen automatically.
  -nt NT                Specifies the number of threads to run within shared memory environment (OpenMP).
  -sp                   Use single floating-point precision.
  -dp                   Use double floating-point precision.
  --solverdir DIR       Specifies the directory containing the solver executable. The default is the directory containing this launcher.
  --workdir DIR, -wd DIR
                        Specifies the working directory to run the application in. The default is the current directory.
  -s STAGES, --stages STAGES
                        Launch only the selected stages, where STAGES is a comma or colon-separated list of numbers and/or range intervals; For example, 1,3-5,8:10 will launch stages 1,3, 4, 5, 8, 9 and 10.
  --time                Summarize time spent by stage.
  -d, --desc            Only describe the simulation without running it.
  -c, --cmd             Only list the launch commands without running the simulation.
  -x, --xml             Only generate XML description of simulation without running it.
  -pyvers PYVERS        Select Python version (38 available by default).
  -userpython USERPYTHON
                        Specify a custom Python directory instead of using included 'one. Requires -pyvers.

MPI options:
  -np NP                Specifies the number of processes to run for applications running on a single host within distributed memory environment (MPI). Its usage must be exclusive with cluster configuration file. (see -cf option).
  -cf HOSTFILE, --hostfile HOSTFILE
                        Specifies the name of a cluster file configuration for applications running on multiple hosts within distributed memory environment (MPI). A cluster file configuration simply lists a series of hostnames, one per line, followed by the number
                        of processes to be run onto. (the default is 1). Lines beginning with # are treated as comment.
  --hostfile_n HOSTFILE_N
                        MPI host file with number of cores; assigned automatically to MPI processes and OpenMP threads; each line formatted: <host> <procs>.
  -mpi MPI              Specifies the MPI runtime to be used. The following MPI implementations are supported: "impi" (Linux and Windows) and "mpich" (Linux only). When using Intel MPI, a version must be specified in addition to the MPI implementation type (e.g.
                        "impi-2019.9").
  -mpidir DIR           Specifies where to look for MPI runtime binary directory; i.e., where to find tools such as mpirun and mpiexec. By default, embedded MPI runtimes are used. This must be used together with -mpi to specify which runtime corresponds to this
                        directory. Using this option on Windows requires either passing the -localonly option to MPI by adding the option: -mpiext "-localonly", or for the impi_hydra service to be running on the machine.
  -mpiext MPIEXT        Extra arguments passed to MPI. e.g.: Intel MPI https://software.intel.com/content/www/us/en/develop/documentation/ mpi-developer-reference-linux/top/environment-variable-reference/process-pinning.html
  -bind                 Deprecated option; has no effect.
  -nobind               Disable process binding; equivalent to I_MPI_PIN=0

Debugger options:
  -g {gdb,valgrind,valgrind_full,ddt,map}
                        Debug your executable. Supported debuggers are: gdb (at least version 6.3), valgrind (at least version 3.3.0), valgrind_full (at least version 3.3.0, in full mode), osplit (for split output). map / ddt (Allinea map or ddt tool). MPI supported
                        on Linux for debuggers: openmpi-1.*, platform-9.*.*
  -gdir DIR             Path to the debugger executable if locally installed.
  -gdbbpf GDBBPF        Path to the gdb break point file.
  -gdbid GDBID          Rank of the process where to apply the breakpoint file.
  -gvsf GVSF            Path to the valgrind suppression file.

CSM options:
  -autocfg              Automatic switch for solver precision per stage. All Explicit stages are running in single precision (-sp) and all Implicit stages are running in double precision (-dp), (available only with -lic STAMP).
  --stagesid STAGESID   Same as --stages except stage ID Must be used as defined in the input file.
  --arch ARCH, -arch ARCH
                        Processor architecture to use. e.g. em64t.
  --token, -token       Print number of flexible tokens.Needed for this run without launching it.
  --rsrvtok RSRVTOK, -rsrvtok RSRVTOK
                        Number of tokens reserved before reading input.
  --datacheck DATACHECK, -datacheck DATACHECK
                        Enable data-check mode, enables solver DATACHECK QUIT option.
  -lic {CRASHSAF,MEDYSA,SOLVER_SEAT,STAMP,IVSTAMP,FORM,V5ESIIV,ASSEMBLY,RTM,DIST,CAST,VA,MACHINING,3DPRINT,VPS_TNT,MF_TNT,COMP_TNT}
                        Specifies the licence package to use.
  -aes AES              Number of models for Alternate-Execution Scheme (AES).
  -aesopt AESOPT        Parameter for AES.
  -aeslayout AESLAYOUT  Parameter for AES.
  -pamarg ...           Arguments for MMC coupling run; For multiple options, specify as -pamarg="..."
  --visualcheck VISUALCHECK, -vc VISUALCHECK
                        Perform a VISUAL-ENVIRONMENT data-check. VISUALCHECK can be one of: {mass, timestep, penetration, projection, vacoupling, datacheck, penetrationwiththickness}.
  -fp FP                Specifies the floating-point precision level to use. 1 stands for Single Precision, 2 for Double Precision. The default is 1.

CSM configuration file format:
  The configuration file specified by option -cfg contains a set of rules.
  Each rule defines a condition and solver options to apply to any stage that
  matches this condition. In case several rules are matching, the last rule
  is the one actually applied.

  A rule is defined by a single line in format
   <condition type> <condition value>: <stage options>

  Conditions can be any of:
    stage <index>
       where <index> is the 1-based position of the stage in the list of stages
    stageid <id>
       where <id> is the stage identifier
    stagename <name>
       where <name> is the stage name
    stageanalysistype <type>
       where <type> is the stage analysis type
       ('implicit', 'explicit' or 'old_implicit')

  <condition value> accepts wildcards '*', '?', '[seq]' and '[!seq]' and is
  case insensitive. <stage options> defines arguments that override the
  global arguments passed to the launcher.

  Environment variables and global launcher arguments can be used in
  <stage options> by using syntax %var%, ${var} or $var and %args.<argument>%,
  ${args.<argument>} or $args.<argument> respectively.

  Examples:
    # options for third stage
    stage 3: -np ${proc_count}
    # options for any stage with ID between 100 and 199
    stageid 1??: -sp
    # options for any stage whose name starts with 'step'
    stagename step*: -smp
    # options for any stage running implicit analysis
    stageanalysistype implicit: -dp

Setting the environment variable ESI_LAUNCHER_DEBUG=1 will result
in the launcher printing out the environment variables and values
it has set for this stage.
```

## 脚本

```bash
#!/bin/bash
#SBATCH -J pamtest                          # 作业名称
#SBATCH -N 1                                    # 总任务数
#SBATCH --ntasks-per-node=64                 # 每个节点的任务数（ptile）
#SBATCH -p xhacexclu53                          # 队列名称
#SBATCH -o %x.out                            # 标准输出文件
#SBATCH -e %x.err                            # 标准错误文件

CURDIR=$PWD
cd $CURDIR
module purge
module load compiler/intel/2021.3.0 mpi/intelmpi/2021.3.0
#module load compiler/intel/2020.1.217 mpi/intelmpi/2020.1.217
#module load compiler/intel/2017.5.239
#module load mpi/intelmpi/2017.4.239
input=`ls  |grep "\.pc"`
#export UCX_IB_ADDR_TYPE=ib_global
#export I_MPI_FABRICS=shm:dapl 
#export I_MPI_DAPL_UD=enable
#export I_MPI_FALLBACK_DEVICE=disable
#export I_MPI_DAPL_UD_PROVIDER=ofa-v2-mlx5_0-1u
export PAMHOME=/work/home/ssct0070t/software/VPS
export PATH=/work/home/ssct0070t/software/2021.06/vpsolver/2021.06/Linux_x86_64/bin:$PATH
export PAM_LMD_LICENSE_FILE=27027@login04
export PAMSHARE=/work/home/ssct0070t/software/pam_libs/vw_libuser/2021/V0/compiled_on_22-10-12/Linux
#export  PAMSHARE=/work/home/ssct0070t/software/VPS/pam_libs/vw_libuser   #智能指定材料库对应的版本，需要进一步验证
unset PYTHONPATH
unset PYTHONHOME
#export PYTHON=/work/home/ssct0070t/software/VPS/python3.8/3.8.9+4/Linux_x86_64/bin/python
# 生成主机文件：每个任务写一行主机名（短格式）
srun hostname -s > $CURDIR/hostfile_$SLURM_JOBID

# 运行pamcrash
#pamcrash -sp -np $SLURM_NTASKS -mpi -cf $CURDIR/hostfile_$SLURM_JOBID $input
#pamcrash -sp -np $SLURM_NTASKS -mpi impi-2018.3 -mpiext "-s all"  -cf $CURDIR/hostfile_$SLURM_JOBID $input # 自带mpi在这个路径下 /work/home/ssct0070t/software/2021.06/intelmpi
pamcrash -sp -np $SLURM_NTASKS -mpi impi-2021.3 -mpidir /public/software/mpi/intelmpi/2021.3.0/bin -mpiext "-s all"  -cf $CURDIR/hostfile_$SLURM_JOBID $input # 用集群的mpi
#pamcrash -sp -np $SLURM_NTASKS -mpi impi-2021.3 -mpidir /public/software/compiler/intel/intel-2020/compilers_and_libraries_2020.1.217/mpi/intel64/bin/ -mpiext "-s all"  -cf $CURDIR/hostfile_$SLURM_JOBID $input 
# 可选：运行后删除主机文件
#rm -f $CURDIR/hostfile_$SLURM_JOBID
```