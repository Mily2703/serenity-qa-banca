package pe.mtongo.qa.stepdefinitions;

import io.cucumber.java.es.Entonces;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;

/** Validaciones comunes a REST y SOAP. */
public class RespuestaSteps {

    @Entonces("el código de respuesta debería ser {int}")
    public void elCodigoDeRespuestaDeberiaSer(int codigo) {
        assertThat(lastResponse().statusCode())
                .as("código HTTP. Respuesta: %s", lastResponse().asString())
                .isEqualTo(codigo);
    }
}
