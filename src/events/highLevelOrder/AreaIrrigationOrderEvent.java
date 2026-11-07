package events.highLevelOrder;

import descricao.Area;

public class AreaIrrigationOrderEvent
        extends HighLevelOrderEvent {

    private final boolean ligado;

    public AreaIrrigationOrderEvent(Area area, boolean ligado) {
        super(area);
        this.ligado = ligado;
    }

    public boolean isLigado() {
        return ligado;
    }
}
