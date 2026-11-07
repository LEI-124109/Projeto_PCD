package SensorEvent;
import Descricao.Area;
import Descricao.Tipo;
import Event.Event;

public abstract class SensorEvent extends Event {


    public SensorEvent(Area area){
        super(area);
    }

    @Override
    public Tipo getTipo(){
        return Tipo.Sensor;
    }


}
