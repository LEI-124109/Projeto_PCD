package events.sensor;

import descricao.Area;

public class HumiditySensorEvent extends SensorEvent {

    private final float humidity;

    public HumiditySensorEvent(Area area, float humidity) {
        super(area);
        this.humidity = humidity;
    }

    public float getHumidity() {
        return humidity;
    }

    @Override
    public float getValue() {
        return humidity;
    }
}
