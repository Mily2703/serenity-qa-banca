package pe.mtongo.qa.api.rest;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;

/** Operaciones del servicio REST de ParaBank (/services/bank). */
public final class ServicioRest {

    private ServicioRest() {
    }

    public static Performable autenticar(String usuario, String clave) {
        return Task.where("{0} se autentica por REST como " + usuario,
                Get.resource("/bank/login/{usuario}/{clave}").with(r -> r
                        .accept(ContentType.JSON)
                        .pathParam("usuario", usuario)
                        .pathParam("clave", clave)));
    }

    public static Performable consultarCuentasDelCliente(long idCliente) {
        return Task.where("{0} consulta las cuentas del cliente #" + idCliente,
                Get.resource("/bank/customers/{id}/accounts").with(r -> r
                        .accept(ContentType.JSON)
                        .pathParam("id", idCliente)));
    }

    public static Performable consultarCuenta(long idCuenta) {
        return Task.where("{0} consulta la cuenta #" + idCuenta,
                Get.resource("/bank/accounts/{id}").with(r -> r
                        .accept(ContentType.JSON)
                        .pathParam("id", idCuenta)));
    }

    public static Performable abrirCuenta(long idCliente, TipoCuenta tipo, long cuentaOrigen) {
        return Task.where("{0} abre una cuenta " + tipo + " desde la cuenta #" + cuentaOrigen,
                Post.to("/bank/createAccount").with(r -> r
                        .accept(ContentType.JSON)
                        .queryParam("customerId", idCliente)
                        .queryParam("newAccountType", tipo.codigo())
                        .queryParam("fromAccountId", cuentaOrigen)));
    }

    public static Performable transferir(int monto, long origen, long destino) {
        return Task.where("{0} transfiere " + monto + " de #" + origen + " a #" + destino,
                Post.to("/bank/transfer").with(r -> r
                        .accept(ContentType.JSON)
                        .queryParam("fromAccountId", origen)
                        .queryParam("toAccountId", destino)
                        .queryParam("amount", monto)));
    }
}
