package pe.mtongo.qa.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import pe.mtongo.qa.api.ClienteApi;
import pe.mtongo.qa.api.soap.ElementoSoap;
import pe.mtongo.qa.api.soap.ServicioSoap;
import pe.mtongo.qa.api.rest.TipoCuenta;
import pe.mtongo.qa.config.Entorno;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static pe.mtongo.qa.api.ClienteApi.*;

public class BancaSoapSteps {

    @Dado("(que )el cliente demo se autentica por SOAP")
    public void seAutenticaPorSoap() {
        Actor actor = ClienteApi.demo();
        actor.attemptsTo(ServicioSoap.operacion("login")
                .con("username", Entorno.usuarioDemo())
                .con("password", Entorno.claveDemo())
                .enviar());
        if (lastResponse().statusCode() == 200) {
            actor.remember(ID_CLIENTE, Long.parseLong(actor.asksFor(ElementoSoap.llamado("id"))));
        }
    }

    @Cuando("consulta por SOAP el detalle de su cuenta principal")
    public void consultaElDetalleDeSuCuentaPrincipal() {
        Actor actor = ClienteApi.demo();
        long principal = cuentaPrincipal(actor);
        actor.attemptsTo(ServicioSoap.operacion("getAccount").con("accountId", principal).enviar());
    }

    @Dado("abre por SOAP una cuenta de tipo {string}")
    public void abrePorSoapUnaCuenta(String tipo) {
        Actor actor = ClienteApi.demo();
        long principal = cuentaPrincipal(actor);
        actor.attemptsTo(ServicioSoap.operacion("createAccount")
                .con("customerId", actor.recall(ID_CLIENTE))
                .con("newAccountType", TipoCuenta.valueOf(tipo).codigo())
                .con("fromAccountId", principal)
                .enviar());
        assertThat(lastResponse().statusCode()).as("createAccount SOAP").isEqualTo(200);
        assertThat(actor.asksFor(ElementoSoap.llamado("type"))).isEqualTo(tipo);
        actor.remember(NUEVA_CUENTA, Long.parseLong(actor.asksFor(ElementoSoap.llamado("id"))));
    }

    @Cuando("transfiere por SOAP {int} desde su cuenta principal hacia la nueva cuenta")
    public void transfierePorSoap(int monto) {
        Actor actor = ClienteApi.demo();
        actor.attemptsTo(ServicioSoap.operacion("transfer")
                .con("fromAccountId", actor.recall(CUENTA_PRINCIPAL))
                .con("toAccountId", actor.recall(NUEVA_CUENTA))
                .con("amount", monto)
                .enviar());
    }

    @Cuando("consulta por SOAP la cuenta {long}")
    public void consultaPorSoapLaCuenta(long cuenta) {
        ClienteApi.demo().attemptsTo(ServicioSoap.operacion("getAccount").con("accountId", cuenta).enviar());
    }

    @Y("la respuesta SOAP debería contener {string} igual a {string}")
    public void laRespuestaSoapDeberiaContenerIgualA(String elemento, String valor) {
        assertThat(ClienteApi.demo().asksFor(ElementoSoap.llamado(elemento))).isEqualTo(valor);
    }

    @Y("la respuesta SOAP debería contener {string} que incluya {string}")
    public void laRespuestaSoapDeberiaContenerQueIncluya(String elemento, String texto) {
        assertThat(ClienteApi.demo().asksFor(ElementoSoap.llamado(elemento))).contains(texto);
    }

    @Y("la cuenta devuelta debería pertenecer al cliente autenticado")
    public void laCuentaDevueltaPerteneceAlCliente() {
        Actor actor = ClienteApi.demo();
        long idCliente = actor.recall(ID_CLIENTE);
        assertThat(actor.asksFor(ElementoSoap.llamado("customerId"))).isEqualTo(String.valueOf(idCliente));
        assertThat(actor.asksFor(ElementoSoap.llamado("id")))
                .isEqualTo(String.valueOf((long) actor.recall(CUENTA_PRINCIPAL)));
    }

    /** Obtiene (con getAccounts) y recuerda la primera cuenta del cliente autenticado. */
    private long cuentaPrincipal(Actor actor) {
        actor.attemptsTo(ServicioSoap.operacion("getAccounts").con("customerId", actor.recall(ID_CLIENTE)).enviar());
        assertThat(lastResponse().statusCode()).as("getAccounts SOAP").isEqualTo(200);
        long principal = Long.parseLong(actor.asksFor(ElementoSoap.llamado("id")));
        actor.remember(CUENTA_PRINCIPAL, principal);
        return principal;
    }
}
