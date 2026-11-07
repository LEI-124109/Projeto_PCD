package events.highLevelOrder;

import descricao.Area;

public class AreaLightingOrderEvent
        extends HighLevelOrderEvent {

    private final boolean ligado;

    public AreaLightingOrderEvent(Area area, boolean ligado) {
        super(area);
        this.ligado = ligado;
    }

    public boolean isLigado() {
        return ligado;
    }
}
