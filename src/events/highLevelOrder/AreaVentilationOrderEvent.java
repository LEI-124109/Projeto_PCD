package events.highLevelOrder;

import descricao.Area;

public class AreaVentilationOrderEvent
        extends HighLevelOrderEvent {

    private final boolean ligado;

    public AreaVentilationOrderEvent(Area area, boolean ligado) {
        super(area);
        this.ligado = ligado;
    }

    public boolean isLigado() {
        return ligado;
    }
}
