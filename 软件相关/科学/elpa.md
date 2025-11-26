```bash

source /public/home/tyust_sofc/jrwang/env.sh

export CC=mpiicc FC=mpiifort CXX=mpiicpc
export MKL_HOME=$MKLROOT

../configure --prefix=/public/home/tyust_sofc/jrwang/elpa-2016.05.004-new/elpa-2016.05.004/install  SCALAPACK_LDFLAGS="-L$MKL_HOME/lib/intel64 -lmkl_scalapack_ilp64 -lmkl_gf_ilp64 -lmkl_sequential -lmkl_core -lmkl_blacs_intelmpi_ilp64 -lpthread -lm -Wl,-rpath,$MKL_HOME/lib/intel64" SCALAPACK_FCFLAGS="-L$MKL_HOME/lib/intel64 -lmkl_scalapack_ilp64 -lmkl_gf_ilp64 -lmkl_sequential -lmkl_core -lmkl_blacs_intelmpi_ilp64 -lpthread -lm -I$MKL_HOME/include/intel64/lp64" FCFLAGS="-O2 -march=native" CFLAGS="-O2 -march=native"

../configure --prefix=/public/home/tyust_sofc/jrwang/elpa-2016.05.004-new/elpa-2016.05.004/install  SCALAPACK_LDFLAGS="-L$MKL_HOME/lib/intel64 -lmkl_scalapack_ilp64 -lmkl_gf_ilp64 -lmkl_sequential -lmkl_core -lmkl_blacs_intelmpi_ilp64 -lpthread -lm -Wl,-rpath,$MKL_HOME/lib/intel64" SCALAPACK_FCFLAGS="-L$MKL_HOME/lib/intel64 -lmkl_scalapack_ilp64 -lmkl_gf_ilp64 -lmkl_sequential -lmkl_core -lmkl_blacs_intelmpi_ilp64 -lpthread -lm -I$MKL_HOME/include/intel64/lp64" FCFLAGS="-O2 -march=native" CFLAGS="-O2 -march=native"

```