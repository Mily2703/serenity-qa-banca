package pe.mtongo.qa.web.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static pe.mtongo.qa.web.ui.PaginaInicio.*;

public final class IniciarSesion {

    private IniciarSesion() {
    }

    public static Performable con(String usuario, String clave) {
        return Task.where("{0} inicia sesión con el usuario '" + usuario + "'",
                Enter.theValue(usuario).into(USUARIO),
                Enter.theValue(clave).into(CLAVE),
                Click.on(BOTON_INGRESAR));
    }

    public static Performable sinCredenciales() {
        return Task.where("{0} intenta iniciar sesión sin usuario ni contraseña",
                Click.on(BOTON_INGRESAR));
    }
}
