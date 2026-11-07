package HighLevelOrderEvent;

import Descricao.Area;

public class AreaVentilationOrderEvent extends HighLevelOrderEvent {
    public final boolean ligar;

    public AreaVentilationOrderEvent(Area area, boolean ligar) {
        super(area);
        this.ligar = ligar;
    }


}
