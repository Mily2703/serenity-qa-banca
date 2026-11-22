package pe.mtongo.qa.config;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

/** Lee las URLs y credenciales del ambiente activo definido en serenity.conf. */
public final class Entorno {

    private Entorno() {
    }

    private static String propiedad(String nombre) {
        return EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getProperty(nombre);
    }

    public static String urlWeb() {
        return propiedad("parabank.web.url");
    }

    public static String urlServicios() {
        return propiedad("parabank.services.url");
    }

    public static String usuarioDemo() {
        return propiedad("parabank.demo.user");
    }

    public static String claveDemo() {
        return propiedad("parabank.demo.password");
    }
}
