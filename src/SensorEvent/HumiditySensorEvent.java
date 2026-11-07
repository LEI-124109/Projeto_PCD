package SensorEvent;

import Descricao.Area;

public class HumiditySensorEvent extends SensorEvent {
    public final double humidity;

    public HumiditySensorEvent(Area area, double humidity) {
        super(area);
        this.humidity = humidity;
    }
}
