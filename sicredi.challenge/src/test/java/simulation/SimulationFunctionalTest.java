package simulation;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@Epic("Regression test")
@Feature("Simulation tests")
public class SimulationFunctionalTest extends SimulationBase {

    @Test
    @Description("Test Description: Creating (POST) a new simulation successfully.")
    void validateCreateNewSimulationWithSucefully() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        Response response = postRequest(simulation);
        SimulationPOJO simulationJsonObject = simulationDataFactory.jsonToObject(response.getBody().print());
        Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());
        Assertions.assertEquals(true, simulation.equals(simulationJsonObject));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with existing cpf.")
    void validateCreateExistentSimulation() {
        SimulationPOJO simulation = simulationDataFactory.selectRandomSimulationInDatabase();
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_CONFLICT, response.statusCode());
        Assertions.assertEquals("CPF já existente", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with a badly formatted CPF.")
    void validateCreateSimulationWithCPFMalformed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setCpf("9999999999");
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("CPF deve ser um cpf válido", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with cpf Null.")
    void validateCreateSimulationWithCPFNull() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setCpf(null);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[cpf:CPF não pode ser vazio]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with name of person Null.")
    void validateCreateSimulationWithNameNull() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setNome(null);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[nome:Nome não pode ser vazio]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with email Null.")
    void validateCreateSimulationWithEmailNull() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setEmail(null);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[email:E-mail não deve ser vazio]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with email out of specified format.")
    void validateCreateSimulationWithEmailMalformed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setEmail("sicredi.com");
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[email:E-mail deve ser um e-mail válido]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with amount value less than specified.")
    void validateCreateSimulationWithAmountSmallerThanAllowed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setValor(999);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[valor:Valor deve ser maior ou igual a R$ 1.000]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with amount value greater than specified.")
    void validateCreateSimulationWithAmountBiggerThanAllowed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setValor(40001);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[valor:Valor deve ser menor ou igual a R$ 40.000]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with fewer installments than allowed.")
    void validateCreateSimulationWithPortionSmallerThanAllowed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setParcelas(1);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[parcelas:Parcelas deve ser igual ou maior que 2]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Creating (POST) a new simulation with more installments than allowed.")
    void validateCreateSimulationWithPortionBiggerThanAllowed() {
        SimulationPOJO simulation = simulationDataFactory.generateNewSimulation();
        simulation.setParcelas(49);
        Response response = postRequest(simulation);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        Assertions.assertEquals("[parcelas:Parcelas deve ser igual ou menor que 48]", response.jsonPath().getString("erros"));
    }

    @Test
    @Description("Test Description: Simulation query (GET) using a non-existent CPF.")
    void validateConsultSimulationByCpfNotFound() {
        String cpf = cpfDataFactory.getRandomCpf();
        Response response = getRequestSimulation(cpf);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    @Description("Test Description: Simulation query (GET) using a valid CPF.")
    void validateConsultSimulationByCpf() {
        SimulationPOJO simulation = simulationDataFactory.selectRandomSimulationInDatabase();
        Response response = getRequestSimulation(simulation.getCpf());
        SimulationPOJO simulationJsonObject = simulationDataFactory.jsonToObject(response.getBody().print());
        Assertions.assertEquals(true, simulation.equals(simulationJsonObject));
    }

    @Test
    @Description("Test Description: Query (GET) the empty simulation base.")
    void validateStatusCodeEmptySimulationsInDatabase() {
        Response response = getRequestAllSimulationsInDatabase();
        if(response.getBody().print() == "[]") {
            Assertions.assertEquals(HttpStatus.SC_NO_CONTENT, response.statusCode());
        }
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    @Description("Test Description: Changing simulation information in a simulation with no CPF.")
    void validateChangeSimulationCpfNotFound() {
        String cpf = simulationDataFactory.generateNonExistentCpf();
        SimulationPOJO simulation = simulationDataFactory.selectRandomSimulationInDatabase();
        Response response = putRequest(cpf, simulation);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    @Description("Test Description: Change simulation information successfully.")
    void validateChangeSimulationSucefully() {
        SimulationPOJO simulation = simulationDataFactory.selectRandomSimulationInDatabase();
        SimulationPOJO newSimulation = simulationDataFactory.generateNewSimulation();
        Response response = putRequest(simulation.getCpf(), newSimulation);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    @Description("Test Description: Delete a successful database simulation.")
    void validateDeleteSimulationWithSucefully() {
        SimulationPOJO simulation = simulationDataFactory.selectRandomSimulationInDatabase();
        Response response = deleteRequest(simulation.getId());
        Assertions.assertEquals(HttpStatus.SC_NO_CONTENT, response.statusCode());
    }

    @Test
    @Description("Test Description: Deleting a database simulation using a non-existent ID.")
    void validateDeleteSimulationWithIDNotFound() {
        Response response = deleteRequest(simulationDataFactory.getNonExistentID());
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        Assertions.assertEquals("Simulação não encontrada", response.jsonPath().getString("erros"));
    }

    private Response getRequestAllSimulationsInDatabase() {
        Response response = when().
                get(BASE_PATH_SIMULATION).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().response();
        return response;
    }

    private Response getRequestSimulation(String cpf) {
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("cpf", cpf)
                .when()
                .get(BASE_PATH_SIMULATION + "/{cpf}")
                .then()
                .extract().response();
        return response;
    }

    private Response postRequest(SimulationPOJO simulation) {
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(simulation)
                .when()
                .post(BASE_PATH_SIMULATION)
                .then()
                .extract().response();
        return response;
    }

    private Response putRequest(String cpf, SimulationPOJO simulationPOJO) {
        Response response = given().
                contentType(ContentType.JSON).
                pathParam("cpf", cpf).
                body(simulationPOJO).
                when().
                put(BASE_PATH_SIMULATION + "/{cpf}").
                then().
                extract().response();
        return response;
    }

    private Response deleteRequest(int id) {
        Response response = given().
                pathParam("id", id).
                when().
                delete(BASE_PATH_SIMULATION + "/{id}").
                then().
                extract().response();
        return response;
    }

}
