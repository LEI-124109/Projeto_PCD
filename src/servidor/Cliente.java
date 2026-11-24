package servidor;

import descricao.Area;
import descricao.Tipo;
import events.RegistrationEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    protected Tipo tipo;
    protected Area area;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    public Cliente(Tipo tipo, Area area) {
        this.tipo = tipo;
        this.area = area;
    }

    public void ligar(String ip, int porto) throws IOException {
        Socket socket = new Socket(ip, porto);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());

        out.writeObject(new RegistrationEvent(tipo, area));
        out.flush();
        System.out.println("Ligado como " + tipo + " em " + area);
    }

    // Fica ligado até o servidor fechar a ligação.
    public void esperar() throws IOException, ClassNotFoundException {
        while (true) {
            in.readObject();
        }
    }

    // Uso: IP PORTO Area Tipo   (ex.: localhost 8080 Sala Sensor)
    public static void main(String[] args) throws Exception {
        String ip = args[0];
        int porto = Integer.parseInt(args[1]);
        Area area = Area.valueOf(args[2]);
        Tipo tipo = Tipo.valueOf(args[3]);

        Cliente cliente = new Cliente(tipo, area);
        cliente.ligar(ip, porto);
        cliente.esperar();
    }
}