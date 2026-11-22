package pe.mtongo.qa.web.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;

/**
 * ParaBank deja en el DOM bloques ocultos (resultado, error) y los muestra con JavaScript.
 * Esta pregunta devuelve solo los textos que el usuario realmente ve.
 */
public final class TextosVisibles {

    private TextosVisibles() {
    }

    public static Question<List<String>> de(Target target) {
        return Question.about("los textos visibles de " + target.getName())
                .answeredBy(actor -> target.resolveAllFor(actor).stream()
                        .filter(WebElementFacade::isCurrentlyVisible)
                        .map(e -> e.getText().trim())
                        .filter(t -> !t.isEmpty())
                        .toList());
    }
}
