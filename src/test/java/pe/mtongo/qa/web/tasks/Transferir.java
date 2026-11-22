package pe.mtongo.qa.web.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static pe.mtongo.qa.web.ui.PaginaCuentas.*;

public final class Transferir {

    private Transferir() {
    }

    /** Transfiere desde la primera cuenta (principal) hacia la cuenta indicada. */
    public static Performable elMonto(int monto, String cuentaDestino) {
        return Task.where("{0} transfiere " + monto + " hacia la cuenta #" + cuentaDestino,
                AbrirLaBanca.enLaPagina("transfer.htm"),
                WaitUntil.the(OPCIONES_CUENTA_ORIGEN, isPresent()),
                Enter.theValue(String.valueOf(monto)).into(MONTO),
                SelectFromOptions.byIndex(0).from(CUENTA_ORIGEN),
                SelectFromOptions.byVisibleText(cuentaDestino).from(CUENTA_DESTINO),
                Click.on(BOTON_TRANSFERIR),
                WaitUntil.the(RESULTADO_TRANSFERENCIA, isVisible()));
    }

    public static Performable sinMonto() {
        return Task.where("{0} intenta transferir sin ingresar un monto",
                AbrirLaBanca.enLaPagina("transfer.htm"),
                WaitUntil.the(OPCIONES_CUENTA_ORIGEN, isPresent()),
                Click.on(BOTON_TRANSFERIR));
    }
}
