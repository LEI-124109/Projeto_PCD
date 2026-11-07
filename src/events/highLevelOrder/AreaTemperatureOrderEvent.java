package events.highLevelOrder;

import descricao.Area;
import descricao.ModoClima;

public class AreaTemperatureOrderEvent
        extends HighLevelOrderEvent {

    private final ModoClima modo;
    private final float temperaturaAlvo;

    public AreaTemperatureOrderEvent(
            Area area,
            ModoClima modo,
            float temperaturaAlvo) {

        super(area);
        this.modo = modo;
        this.temperaturaAlvo = temperaturaAlvo;
    }

    public ModoClima getModo() {
        return modo;
    }

    public float getTemperaturaAlvo() {
        return temperaturaAlvo;
    }
}
