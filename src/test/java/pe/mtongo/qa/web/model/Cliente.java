package pe.mtongo.qa.web.model;

/** Datos de prueba de un cliente nuevo. El usuario es único en cada ejecución para evitar colisiones. */
public record Cliente(String nombre, String apellido, String direccion, String ciudad, String estado,
                      String codigoPostal, String telefono, String documento, String usuario, String clave) {

    public static Cliente nuevo(String nombre) {
        String sufijo = Long.toString(System.currentTimeMillis(), 36);
        return new Cliente(nombre, "Tongo", "Av. Javier Prado 123", "Lima", "LI", "15036",
                "999888777", "12345678", "qa_" + sufijo, "Clave123*");
    }
}
