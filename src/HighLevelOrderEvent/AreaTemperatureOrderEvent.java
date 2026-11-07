package HighLevelOrderEvent;

import Descricao.AirConditioningMode;
import Descricao.Area;

public class AreaTemperatureOrderEvent extends HighLevelOrderEvent {
    public final boolean ligar;
    public final AirConditioningMode mode;

    public AreaTemperatureOrderEvent(Area area, boolean ligar,  AirConditioningMode mode){
        super(area);
        this.ligar = ligar;
        this.mode = mode;
    }
}
