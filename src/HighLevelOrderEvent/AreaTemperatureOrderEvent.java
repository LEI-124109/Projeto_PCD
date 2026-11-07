package HighLevelOrderEvent;

import Descricao.AirConditioningMode;
import Descricao.Area;

public class AreaTemperatureOrderEvent extends HighLevelOrderEvent {
    public final AirConditioningMode mode;

    public AreaTemperatureOrderEvent(Area area,AirConditioningMode mode){
        super(area);
        this.mode = mode;
    }
}
