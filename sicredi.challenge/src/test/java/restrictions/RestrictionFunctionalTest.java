package restrictions;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class RestrictionFunctionalTest extends RestrictionBase {

	@Test
	void validateCpfWithRestriction() {
		//Given
		String cpf = cpfDataFactory.getCpfWithRestricition();
		Response response = getRestrictionResponse(cpf);
		//When
		String message = response.jsonPath().getString("mensagem");
		//Then
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
		Assertions.assertEquals(String.format("O CPF %s tem problema", cpf), message);
	}

	@Test
	void validateCpfWithoutRestriction() {
		String cpf = cpfDataFactory.getRandomCpf();
		Response response = getRestrictionResponse(cpf);
		Assertions.assertEquals(HttpStatus.SC_NO_CONTENT, response.statusCode());
	}

	private Response getRestrictionResponse(String cpf) {
		Response response = given()
				.contentType(ContentType.JSON)
				.pathParam("cpf", cpf)
				.when()
				.get(BASE_PATH_RESTRICTION)
				.then()
				.extract().response();
		return response;
	}

}
