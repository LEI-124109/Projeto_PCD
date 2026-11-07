package events;

import descricao.Area;
import java.io.Serializable;

public abstract class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Area area;
    private final long timestamp;

    protected Event(Area area) {

        if (area == null) {
            throw new IllegalArgumentException(
                    "A zona não pode ser nula."
            );
        }

        this.area = area;
        this.timestamp = System.currentTimeMillis();

    }

    public Area getArea() {
        return area;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

