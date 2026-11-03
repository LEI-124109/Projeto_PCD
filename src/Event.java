import java.io.Serializable;
import Descricao.Area;
import Descricao.Tipo;

public abstract class Event implements Serializable {
    public final Area area;

    public Event(Area area) {
        this.area = area;
    }

    public abstract Tipo getTipo();


}
