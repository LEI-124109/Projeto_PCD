package servidor;

import descricao.Area;
import descricao.Tipo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class TratadorCliente extends Thread {

    protected Servidor servidor;
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Area area;
    protected int id;

    public TratadorCliente(Servidor servidor, Socket socket, ObjectInputStream in, ObjectOutputStream out, Area area) {
        this.servidor = servidor;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.area = area;
        this.id = servidor.novoId();
    }

    public abstract Tipo getTipo();

    protected abstract void atender() throws IOException, ClassNotFoundException;

    @Override
    public void run() {
        try {
            atender();
        } catch (IOException | ClassNotFoundException e) {
        }
        servidor.remover(this);
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public Area getArea() {
        return area;
    }

    @Override
    public String toString() {
        return getTipo() + "#" + id + "@" + area;
    }
}