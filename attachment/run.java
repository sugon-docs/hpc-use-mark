// Simcenter STAR-CCM+ macro: run.java
// Written by Simcenter STAR-CCM+ 20.02.007
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cosimulation.link.abaqus.*;
import star.cosimulation.link.common.*;

public class run extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CoSimulation coSimulation_0 = 
      ((CoSimulation) simulation_0.get(CoSimulationManager.class).getObject("Link 1"));

    AbaqusExecution abaqusExecution_0 = 
      coSimulation_0.getCoSimulationValues().get(AbaqusExecution.class);

    abaqusExecution_0.setJobFileName("SOLID_JOB.inp");

    abaqusExecution_0.setExecutableName("/public/home/accslasm5v/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/code/bin/ABQLauncher");

    abaqusExecution_0.setNumCpus(24);

    AbaqusLibraryFile abaqusLibraryFile_0 = 
      coSimulation_0.getCoSimulationValues().get(AbaqusLibraryFile.class);

    abaqusLibraryFile_0.setFilePath("/public/home/accslasm5v/apprepo/abaqus/2019-none/app/abaqus2019/linux_a64/code/bin/libABQSMACseModules.so");
  }
}
