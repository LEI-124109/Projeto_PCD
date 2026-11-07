package SensorEvent;


import Descricao.Area;

public class TemperatureSensorEvent extends SensorEvent {
    public double temperature;

    public TemperatureSensorEvent(Area area, double temperature) {
        super(area);
        this.temperature = temperature;
    }
}
