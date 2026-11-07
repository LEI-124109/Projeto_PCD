package events.sensor;

import descricao.Area;

public class LightSensorEvent extends SensorEvent {

    private final float luminosity;

    public LightSensorEvent(Area area, float luminosity) {
        super(area);
        this.luminosity = luminosity;
    }

    public float getLuminosity() {
        return luminosity;
    }

    @Override
    public float getValue() {
        return luminosity;
    }
}
