package servidor;

import events.RegistrationEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Rececao extends Thread {

    private Socket socket;
    private Servidor servidor;

    public Rececao(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            RegistrationEvent registo = (RegistrationEvent) in.readObject();

            Atendimento atendimento;
            switch (registo.getTipo()) {
                case Sensor:
                    atendimento = new AtendimentoSensor(servidor, socket, in, out, registo.getArea());
                    break;
                case Decisores:
                    atendimento = new AtendimentoDecisor(servidor, socket, in, out, registo.getArea());
                    break;
                case Realizadores:
                    atendimento = new AtendimentoAtuador(servidor, socket, in, out, registo.getArea());
                    break;
                default:
                    System.out.println("Ligação recusada: tipo inexistente");
                    socket.close();
                    return;
            }

            servidor.registar(atendimento);
            atendimento.start();

        } catch (IOException | ClassNotFoundException | ClassCastException | NullPointerException e) {
            System.out.println("Ligação recusada: registo inválido");
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}