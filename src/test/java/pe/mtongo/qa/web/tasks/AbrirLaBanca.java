package pe.mtongo.qa.web.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import pe.mtongo.qa.config.Entorno;

public final class AbrirLaBanca {

    private AbrirLaBanca() {
    }

    public static Performable enLaPagina(String pagina) {
        return Task.where("{0} abre la página " + pagina, Open.url(Entorno.urlWeb() + "/" + pagina));
    }

    public static Performable porInternet() {
        return enLaPagina("index.htm");
    }
}
