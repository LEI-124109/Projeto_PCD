package events.highLevelOrder;

import descricao.Area;
import events.Event;

public abstract class HighLevelOrderEvent extends Event {

    protected HighLevelOrderEvent(Area area) {
        super(area);
    }
}
