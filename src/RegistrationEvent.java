import Descricao.Area;
import Descricao.Tipo;

public class RegistrationEvent extends Event {
    public final Tipo tipo;

    public RegistrationEvent(Tipo tipo, Area area) {
        super(area);
        this.tipo = tipo;
    }
    public Tipo getTipo() {
        return tipo;
    }
}
