package pe.mtongo.qa.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import pe.mtongo.qa.api.ClienteApi;
import pe.mtongo.qa.api.rest.ServicioRest;
import pe.mtongo.qa.api.rest.TipoCuenta;
import pe.mtongo.qa.config.Entorno;

import java.math.BigDecimal;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static pe.mtongo.qa.api.ClienteApi.*;

public class BancaRestSteps {

    private static final int INTENTOS = 10;

    @Dado("que el cliente demo se autentica en el servicio REST")
    public void seAutenticaEnElServicioRest() {
        Actor actor = ClienteApi.demo();
        actor.attemptsTo(ServicioRest.autenticar(Entorno.usuarioDemo(), Entorno.claveDemo()));
        assertThat(lastResponse().statusCode()).as("login REST").isEqualTo(200);
        actor.remember(ID_CLIENTE, lastResponse().jsonPath().getLong("id"));
    }

    @Cuando("consulta sus cuentas")
    public void consultaSusCuentas() {
        Actor actor = ClienteApi.demo();
        actor.attemptsTo(ServicioRest.consultarCuentasDelCliente(actor.recall(ID_CLIENTE)));
        actor.remember(CUENTA_PRINCIPAL, lastResponse().jsonPath().getLong("[0].id"));
    }

    @Dado("que abre una cuenta de tipo {string} con el depósito mínimo de {int}")
    public void abreUnaCuentaConDepositoMinimo(String tipo, int montoDeposito) {
        BigDecimal deposito = BigDecimal.valueOf(montoDeposito);
        Actor actor = ClienteApi.demo();
        consultaSusCuentas();
        actor.attemptsTo(ServicioRest.abrirCuenta(actor.recall(ID_CLIENTE), TipoCuenta.valueOf(tipo),
                actor.recall(CUENTA_PRINCIPAL)));
        assertThat(lastResponse().statusCode()).as("apertura de cuenta").isEqualTo(200);
        assertThat(lastResponse().jsonPath().getString("type")).isEqualTo(tipo);
        long nuevaCuenta = lastResponse().jsonPath().getLong("id");
        actor.remember(NUEVA_CUENTA, nuevaCuenta);

        // El depósito inicial se procesa de forma asíncrona: se espera a que se refleje en el saldo.
        assertThat(esperarSaldo(actor, nuevaCuenta, deposito)).as("saldo inicial").isEqualByComparingTo(deposito);
    }

    @Cuando("transfiere {int} desde su cuenta principal hacia la nueva cuenta por REST")
    public void transfiereHaciaLaNuevaCuentaPorRest(int monto) {
        Actor actor = ClienteApi.demo();
        actor.attemptsTo(ServicioRest.transferir(monto, actor.recall(CUENTA_PRINCIPAL), actor.recall(NUEVA_CUENTA)));
    }

    @Cuando("consulta la cuenta {long}")
    public void consultaLaCuenta(long cuenta) {
        ClienteApi.demo().attemptsTo(ServicioRest.consultarCuenta(cuenta));
    }

    @Y("debería tener al menos una cuenta de tipo {string}")
    public void deberiaTenerAlMenosUnaCuentaDeTipo(String tipo) {
        List<String> tipos = lastResponse().jsonPath().getList("type");
        assertThat(tipos).as("tipos de cuenta").isNotEmpty().contains(tipo);
    }

    @Y("todas sus cuentas deberían pertenecer al cliente autenticado")
    public void todasSusCuentasPertenecenAlCliente() {
        long idCliente = ClienteApi.demo().recall(ID_CLIENTE);
        List<Long> clientes = lastResponse().jsonPath().getList("customerId", Long.class);
        assertThat(clientes).as("customerId de cada cuenta").isNotEmpty().allMatch(id -> id == idCliente);
    }

    @Y("el mensaje debería contener {string}")
    public void elMensajeDeberiaContener(String texto) {
        assertThat(lastResponse().asString()).contains(texto);
    }

    @Y("el saldo de la nueva cuenta debería ser {int}")
    public void elSaldoDeLaNuevaCuentaDeberiaSer(int saldoEsperado) {
        BigDecimal esperado = BigDecimal.valueOf(saldoEsperado);
        Actor actor = ClienteApi.demo();
        assertThat(esperarSaldo(actor, actor.recall(NUEVA_CUENTA), esperado)).isEqualByComparingTo(esperado);
    }

    /** Consulta el saldo hasta que coincida con el esperado o se agoten los intentos; devuelve el último saldo leído. */
    private BigDecimal esperarSaldo(Actor actor, long cuenta, BigDecimal esperado) {
        BigDecimal saldo = null;
        for (int i = 0; i < INTENTOS; i++) {
            actor.attemptsTo(ServicioRest.consultarCuenta(cuenta));
            Response r = lastResponse();
            saldo = new BigDecimal(r.jsonPath().getString("balance"));
            if (saldo.compareTo(esperado) == 0) {
                break;
            }
            pausa();
        }
        return saldo;
    }

    private static void pausa() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
