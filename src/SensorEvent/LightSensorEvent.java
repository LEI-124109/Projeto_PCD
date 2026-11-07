package SensorEvent;


import Descricao.Area;

public class LightSensorEvent extends SensorEvent {
    public final double light;

    public LightSensorEvent(Area area, double light) {
        super(area);
        this.light = light;
    }



}
