package HighLevelOrderEvent;

import Descricao.Area;
import Descricao.Tipo;
import Event.Event;

public abstract class HighLevelOrderEvent extends Event {

    public HighLevelOrderEvent(Area area) {
        super(area);
    }

    @Override
    public Tipo getTipo(){
        return Tipo.Decisores;
    }

}
