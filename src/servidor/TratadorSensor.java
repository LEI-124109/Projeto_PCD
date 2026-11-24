package servidor;

import descricao.Area;
import descricao.Tipo;
import events.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TratadorSensor extends TratadorCliente {

    public TratadorSensor(Servidor servidor, Socket socket, ObjectInputStream in, ObjectOutputStream out, Area area) {
        super(servidor, socket, in, out, area);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.Sensor;
    }

    @Override
    protected void atender() throws IOException, ClassNotFoundException {
        while (true) {
            Event leitura = (Event) in.readObject();
            System.out.println("[Servidor] " + this + " enviou " + leitura.getClass().getSimpleName());
        }
    }
}