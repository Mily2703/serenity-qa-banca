package pe.mtongo.qa.web.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class PaginaRegistro {

    private PaginaRegistro() {
    }

    private static Target campo(String id, String descripcion) {
        return Target.the(descripcion).locatedBy("css:[id='" + id + "']");
    }

    public static final Target NOMBRE = campo("customer.firstName", "nombre");
    public static final Target APELLIDO = campo("customer.lastName", "apellido");
    public static final Target DIRECCION = campo("customer.address.street", "dirección");
    public static final Target CIUDAD = campo("customer.address.city", "ciudad");
    public static final Target ESTADO = campo("customer.address.state", "estado");
    public static final Target CODIGO_POSTAL = campo("customer.address.zipCode", "código postal");
    public static final Target TELEFONO = campo("customer.phoneNumber", "teléfono");
    public static final Target DOCUMENTO = campo("customer.ssn", "documento");
    public static final Target USUARIO = campo("customer.username", "usuario");
    public static final Target CLAVE = campo("customer.password", "contraseña");
    public static final Target CONFIRMAR_CLAVE = campo("repeatedPassword", "confirmar contraseña");
    public static final Target BOTON_REGISTRAR = Target.the("botón Register").locatedBy("css:input[value='Register']");
}
