package events.sensor;

import descricao.Area;

public class GroundMoistureSensorEvent extends SensorEvent {

    private final float moisture;

    public GroundMoistureSensorEvent(Area area, float moisture) {
        super(area);
        this.moisture = moisture;
    }

    public float getMoisture() {
        return moisture;
    }

    @Override
    public float getValue() {
        return moisture;
    }
}
