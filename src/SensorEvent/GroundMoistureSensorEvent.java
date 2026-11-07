package SensorEvent;

import Descricao.Area;

public class GroundMoistureSensorEvent extends SensorEvent {
    public final double humidity;

    public GroundMoistureSensorEvent(Area area, double humidity) {
        super(area);
        this.humidity = humidity;
    }
}
