package events.sensor;

import descricao.Area;
import events.Event;

public abstract class SensorEvent extends Event {

    protected SensorEvent(Area area) {
        super(area);
    }

    public abstract float getValue();
}

