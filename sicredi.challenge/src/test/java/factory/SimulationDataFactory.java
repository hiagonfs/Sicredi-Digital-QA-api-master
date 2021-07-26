package factory;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import simulation.SimulationPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.when;

public class SimulationDataFactory {

    private final Faker faker;
    private CpfDataFactory cpfDataFactory;
    private final Gson gson;
    private List<SimulationPOJO> simulations;

    public SimulationDataFactory() {
        faker = new Faker();
        cpfDataFactory = new CpfDataFactory();
        gson = new Gson();
        simulations = new ArrayList<>();
        setSimulationDTO();
    }

    public List<SimulationPOJO> getSimulations() {
        return simulations;
    }

    public SimulationPOJO selectRandomSimulationInDatabase() {
        int index = (int)(Math.random() * simulations.size());
        return simulations.get(index);
    }

    public String getNewCpf() {
        return cpfDataFactory.getRandomCpf();
    }

    public String getNewName() {
        return faker.name().firstName();
    }

    public double getNewValue() {
        return faker.number().randomDouble(2, 1000, 40000);
    }

    public String getNewEmail() {
        return faker.internet().emailAddress();
    }

    public int getNewPortion() {
        return faker.number().numberBetween(2, 48);
    }

    public boolean getNewInsurance() {
        return faker.bool().bool();
    }

    public SimulationPOJO jsonToObject(String json) {
        return gson.fromJson(json, SimulationPOJO.class);
    }

    private void setSimulationDTO() {
        simulations = Arrays.asList(getSimulationsInDataBase());
    }

    public String generateNonExistentCpf() {
        String cpf = cpfDataFactory.getRandomCpf();
        List<String> cpfs = simulations.stream().map(p -> p.getCpf()).collect(Collectors.toList());
        while(cpfs.contains(cpf)) {
            cpf = cpfDataFactory.getRandomCpf();
        }
        return cpf;
    }

    public int getNonExistentID() {
        Random random = new Random();
        int randomID = random.nextInt();
        List<Integer> ids = simulations.stream().map(p -> p.getId()).collect(Collectors.toList());
        while(ids.contains(randomID)) {
            randomID = random.nextInt();
        }
        return randomID;
    }

    public SimulationPOJO[] getSimulationsInDataBase() {
        return when().
                    get("/simulacoes").
               then().
                    statusCode(HttpStatus.SC_OK).
                    extract().
                    as(SimulationPOJO[].class);

    }

    public SimulationPOJO generateNewSimulation() {
        String name = getNewName();
        String cpf = getNewCpf();
        String email = getNewEmail();
        double value = getNewValue();
        int portion = getNewPortion();
        boolean insurance = getNewInsurance();
        return new SimulationPOJO(cpf, name, email, value, portion, insurance);
    }


}
