package servidor;

import descricao.Area;
import descricao.Tipo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TratadorAtuador extends TratadorCliente {

    public TratadorAtuador(Servidor servidor, Socket socket, ObjectInputStream in, ObjectOutputStream out, Area area) {
        super(servidor, socket, in, out, area);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.Realizadores;
    }

    @Override
    protected void atender() throws IOException, ClassNotFoundException {
        while (true) {
            in.readObject();
        }
    }
}
