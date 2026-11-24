package servidor;

import descricao.Area;
import descricao.Tipo;
import events.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TratadorDecisor extends TratadorCliente {

    public TratadorDecisor(Servidor servidor, Socket socket, ObjectInputStream in, ObjectOutputStream out, Area area) {
        super(servidor, socket, in, out, area);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.Decisores;
    }

    @Override
    protected void atender() throws IOException, ClassNotFoundException {
        while (true) {
            Event ordem = (Event) in.readObject();
            System.out.println("[Servidor] " + this + " enviou " + ordem.getClass().getSimpleName());
        }
    }
}
