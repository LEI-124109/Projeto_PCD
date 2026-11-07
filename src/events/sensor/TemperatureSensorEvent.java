package events.sensor;

import descricao.Area;

public class TemperatureSensorEvent extends SensorEvent {

    private final float temperature;

    public TemperatureSensorEvent(Area area, float temperature) {
        super(area);
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    @Override
    public float getValue() {
        return temperature;
    }
}
