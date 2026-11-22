package pe.mtongo.qa.api;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import pe.mtongo.qa.config.Entorno;

/** Actor que consume los servicios del banco (REST y SOAP comparten la misma URL base). */
public final class ClienteApi {

    public static final String ID_CLIENTE = "id del cliente";
    public static final String CUENTA_PRINCIPAL = "cuenta principal";
    public static final String NUEVA_CUENTA = "nueva cuenta";

    private ClienteApi() {
    }

    public static Actor demo() {
        Actor actor = OnStage.theActorCalled("Cliente demo");
        if (actor.abilityTo(CallAnApi.class) == null) {
            actor.can(CallAnApi.at(Entorno.urlServicios()));
        }
        return actor;
    }
}
