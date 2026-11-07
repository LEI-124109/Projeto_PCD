package HighLevelOrderEvent;

import Descricao.Area;

public class AreaIrrigationOrderEvent extends HighLevelOrderEvent {
    public final boolean regar;

    public AreaIrrigationOrderEvent(Area area, boolean regar) {
        super(area);
        this.regar = regar;
    }
}
