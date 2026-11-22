package pe.mtongo.qa.api.soap;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

/** Lee el primer elemento con ese nombre en la última respuesta SOAP, sin importar el namespace. */
public final class ElementoSoap {

    private ElementoSoap() {
    }

    public static Question<String> llamado(String elemento) {
        return Question.about("el elemento SOAP <" + elemento + ">")
                .answeredBy(actor -> SerenityRest.lastResponse().xmlPath()
                        .getString("**.find { it.name() == '" + elemento + "' }"));
    }
}
