package pe.mtongo.qa.api.soap;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.LinkedHashMap;
import java.util.Map;

/** Construye sobres SOAP 1.1 para el servicio ParaBank (/services/ParaBank) y los envía. */
public final class ServicioSoap {

    private static final String NAMESPACE = "http://service.parabank.parasoft.com/";

    private final String operacion;
    private final Map<String, Object> parametros = new LinkedHashMap<>();

    private ServicioSoap(String operacion) {
        this.operacion = operacion;
    }

    public static ServicioSoap operacion(String operacion) {
        return new ServicioSoap(operacion);
    }

    public ServicioSoap con(String nombre, Object valor) {
        parametros.put(nombre, valor);
        return this;
    }

    String sobre() {
        StringBuilder cuerpo = new StringBuilder();
        parametros.forEach((k, v) -> cuerpo.append("<ser:").append(k).append('>')
                .append(v).append("</ser:").append(k).append('>'));
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="%s">
                  <soapenv:Header/>
                  <soapenv:Body><ser:%s>%s</ser:%s></soapenv:Body>
                </soapenv:Envelope>""".formatted(NAMESPACE, operacion, cuerpo, operacion);
    }

    public Performable enviar() {
        String xml = sobre();
        return Task.where("{0} invoca la operación SOAP " + operacion + " " + parametros,
                Post.to("/ParaBank").with(r -> r
                        .contentType("text/xml;charset=UTF-8")
                        .header("SOAPAction", "")
                        .body(xml)));
    }
}
