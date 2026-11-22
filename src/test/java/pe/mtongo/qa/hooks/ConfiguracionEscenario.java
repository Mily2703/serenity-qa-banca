package pe.mtongo.qa.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/** Prepara el "escenario" de Screenplay antes de cada prueba y lo limpia al final. */
public class ConfiguracionEscenario {

    @Before(order = 0)
    public void prepararElEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void cerrarElEscenario() {
        OnStage.drawTheCurtain();
    }
}
