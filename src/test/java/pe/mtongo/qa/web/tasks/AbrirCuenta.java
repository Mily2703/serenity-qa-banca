package pe.mtongo.qa.web.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static pe.mtongo.qa.web.ui.PaginaCuentas.*;

public final class AbrirCuenta {

    private AbrirCuenta() {
    }

    /** @param tipo CHECKING o SAVINGS, tal como aparece en el combo de ParaBank */
    public static Performable deTipo(String tipo) {
        return Task.where("{0} abre una nueva cuenta de tipo " + tipo,
                AbrirLaBanca.enLaPagina("openaccount.htm"),
                WaitUntil.the(OPCIONES_CUENTA_ORIGEN, isPresent()),
                SelectFromOptions.byVisibleText(tipo).from(TIPO_DE_CUENTA),
                Click.on(BOTON_ABRIR_CUENTA),
                WaitUntil.the(RESULTADO_APERTURA, isVisible()),
                WaitUntil.the(NUMERO_NUEVA_CUENTA, isVisible()));
    }
}
