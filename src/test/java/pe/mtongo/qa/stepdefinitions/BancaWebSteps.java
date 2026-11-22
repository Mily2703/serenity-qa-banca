package pe.mtongo.qa.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import pe.mtongo.qa.web.model.Cliente;
import pe.mtongo.qa.web.questions.TextosVisibles;
import pe.mtongo.qa.web.tasks.AbrirCuenta;
import pe.mtongo.qa.web.tasks.AbrirLaBanca;
import pe.mtongo.qa.web.tasks.IniciarSesion;
import pe.mtongo.qa.web.tasks.Registrarse;
import pe.mtongo.qa.web.tasks.Transferir;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;
import static pe.mtongo.qa.web.ui.PaginaCuentas.*;
import static pe.mtongo.qa.web.ui.PaginaInicio.*;

public class BancaWebSteps {

    private static final String CLIENTE = "cliente registrado";
    private static final String NUEVA_CUENTA = "nueva cuenta";

    @Dado("que {word} se registra como nueva cliente en la banca por internet")
    public void seRegistraComoNuevaCliente(String nombre) {
        Actor actor = theActorCalled(nombre);
        Cliente cliente = Cliente.nuevo(nombre);
        actor.attemptsTo(Registrarse.como(cliente));
        actor.remember(CLIENTE, cliente);
    }

    @Dado("que {word} abre la banca por internet")
    public void abreLaBancaPorInternet(String nombre) {
        theActorCalled(nombre).attemptsTo(AbrirLaBanca.porInternet());
    }

    @Cuando("inicia sesión sin ingresar usuario ni contraseña")
    public void iniciaSesionSinCredenciales() {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.sinCredenciales());
    }

    @Cuando("abre una nueva cuenta de tipo {string}")
    public void abreUnaNuevaCuenta(String tipo) {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(AbrirCuenta.deTipo(tipo));
        actor.remember(NUEVA_CUENTA, Text.of(NUMERO_NUEVA_CUENTA).answeredBy(actor).trim());
    }

    @Cuando("transfiere {int} desde su cuenta principal hacia la nueva cuenta")
    public void transfiereHaciaLaNuevaCuenta(int monto) {
        Actor actor = theActorInTheSpotlight();
        String nuevaCuenta = actor.recall(NUEVA_CUENTA);
        actor.attemptsTo(Transferir.elMonto(monto, nuevaCuenta));
    }

    @Cuando("intenta transferir sin ingresar un monto")
    public void intentaTransferirSinMonto() {
        theActorInTheSpotlight().attemptsTo(Transferir.sinMonto());
    }

    @Entonces("debería ver la bienvenida con su nombre de usuario")
    public void deberiaVerLaBienvenida() {
        Actor actor = theActorInTheSpotlight();
        Cliente cliente = actor.recall(CLIENTE);
        actor.attemptsTo(Ensure.that(TITULO).text().isEqualTo("Welcome " + cliente.usuario()));
    }

    @Y("el mensaje {string}")
    public void elMensaje(String mensaje) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(MENSAJE).text().isEqualTo(mensaje));
    }

    @Entonces("debería ver el título {string}")
    public void deberiaVerElTitulo(String titulo) {
        Actor actor = theActorInTheSpotlight();
        assertThat(TextosVisibles.de(TITULOS).answeredBy(actor)).contains(titulo);
    }

    @Y("el número de su nueva cuenta")
    public void elNumeroDeSuNuevaCuenta() {
        String numero = theActorInTheSpotlight().recall(NUEVA_CUENTA);
        assertThat(numero).as("número de la nueva cuenta").matches("\\d+");
    }

    @Y("el monto transferido debería ser {string}")
    public void elMontoTransferidoDeberiaSer(String monto) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(MONTO_TRANSFERIDO).text().isEqualTo(monto));
    }

    @Entonces("debería ver el error de monto {string}")
    public void deberiaVerElErrorDeMonto(String error) {
        Actor actor = theActorInTheSpotlight();
        assertThat(TextosVisibles.de(ERRORES_DE_MONTO).answeredBy(actor)).contains(error);
    }

    @Entonces("debería ver el error de acceso {string}")
    public void deberiaVerElErrorDeAcceso(String error) {
        theActorInTheSpotlight().attemptsTo(Ensure.that(ERROR).text().isEqualTo(error));
    }
}
