package pe.mtongo.qa.web.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class PaginaInicio {

    private PaginaInicio() {
    }

    public static final Target USUARIO = Target.the("campo usuario").locatedBy("css:input[name='username']");
    public static final Target CLAVE = Target.the("campo contraseña").locatedBy("css:input[name='password']");
    public static final Target BOTON_INGRESAR = Target.the("botón Log In").locatedBy("css:input[value='Log In']");

    public static final Target TITULO = Target.the("título principal").locatedBy("css:#rightPanel h1.title");
    public static final Target MENSAJE = Target.the("mensaje principal").locatedBy("css:#rightPanel p");
    public static final Target ERROR = Target.the("mensaje de error").locatedBy("css:#rightPanel p.error");
}
