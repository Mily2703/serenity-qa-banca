package pe.mtongo.qa.web.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import pe.mtongo.qa.web.model.Cliente;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static pe.mtongo.qa.web.ui.PaginaRegistro.*;

public final class Registrarse {

    private Registrarse() {
    }

    public static Performable como(Cliente c) {
        return Task.where("{0} se registra como nueva cliente con el usuario " + c.usuario(),
                AbrirLaBanca.enLaPagina("register.htm"),
                WaitUntil.the(NOMBRE, isVisible()),
                Enter.theValue(c.nombre()).into(NOMBRE),
                Enter.theValue(c.apellido()).into(APELLIDO),
                Enter.theValue(c.direccion()).into(DIRECCION),
                Enter.theValue(c.ciudad()).into(CIUDAD),
                Enter.theValue(c.estado()).into(ESTADO),
                Enter.theValue(c.codigoPostal()).into(CODIGO_POSTAL),
                Enter.theValue(c.telefono()).into(TELEFONO),
                Enter.theValue(c.documento()).into(DOCUMENTO),
                Enter.theValue(c.usuario()).into(USUARIO),
                Enter.theValue(c.clave()).into(CLAVE),
                Enter.theValue(c.clave()).into(CONFIRMAR_CLAVE),
                Click.on(BOTON_REGISTRAR));
    }
}
