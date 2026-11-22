package pe.mtongo.qa.web.ui;

import net.serenitybdd.screenplay.targets.Target;

/** Pantallas "Open New Account" y "Transfer Funds". Ambas cargan las cuentas por AJAX. */
public final class PaginaCuentas {

    private PaginaCuentas() {
    }

    // Abrir cuenta
    public static final Target TIPO_DE_CUENTA = Target.the("tipo de cuenta").locatedBy("css:#type");
    public static final Target CUENTA_ORIGEN = Target.the("cuenta de origen").locatedBy("css:#fromAccountId");
    public static final Target OPCIONES_CUENTA_ORIGEN = Target.the("cuentas de origen cargadas")
            .locatedBy("css:#fromAccountId option");
    public static final Target BOTON_ABRIR_CUENTA = Target.the("botón Open New Account")
            .locatedBy("css:input[value='Open New Account']");
    public static final Target RESULTADO_APERTURA = Target.the("resultado de apertura").locatedBy("css:#openAccountResult");
    public static final Target NUMERO_NUEVA_CUENTA = Target.the("número de la nueva cuenta").locatedBy("css:#newAccountId");

    // Transferencias
    public static final Target MONTO = Target.the("monto").locatedBy("css:#amount");
    public static final Target CUENTA_DESTINO = Target.the("cuenta de destino").locatedBy("css:#toAccountId");
    public static final Target BOTON_TRANSFERIR = Target.the("botón Transfer").locatedBy("css:input[value='Transfer']");
    public static final Target RESULTADO_TRANSFERENCIA = Target.the("resultado de transferencia").locatedBy("css:#showResult");
    public static final Target MONTO_TRANSFERIDO = Target.the("monto transferido").locatedBy("css:#amountResult");
    public static final Target ERRORES_DE_MONTO = Target.the("errores del monto").locatedBy("//p[@id='amount.errors']");

    public static final Target TITULOS = Target.the("títulos de la página").locatedBy("css:#rightPanel h1.title");
}
