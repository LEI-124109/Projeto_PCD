package HighLevelOrderEvent;

import Descricao.Area;

public class AreaLightingOrderEvent extends HighLevelOrderEvent {
    public final boolean acender;

    public AreaLightingOrderEvent(Area area, boolean acender){
        super(area);
        this.acender = acender;
    }
}
