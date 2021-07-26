package simulation;

import base.BaseAPI;
import factory.CpfDataFactory;
import factory.SimulationDataFactory;

public abstract class SimulationBase extends BaseAPI {

    public static final String BASE_PATH_SIMULATION = "simulacoes";
    protected final SimulationDataFactory simulationDataFactory;
    protected final CpfDataFactory cpfDataFactory;

    public SimulationBase() {
        simulationDataFactory = new SimulationDataFactory();
        cpfDataFactory = new CpfDataFactory();
    }

}
