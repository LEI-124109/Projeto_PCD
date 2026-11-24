package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private ServerSocket serverSocket;
    private List<TratadorCliente> clientes = new ArrayList<>();
    private int ultimoId = 0;

    public Servidor(int porto) throws IOException {
        serverSocket = new ServerSocket(porto);
    }

    public void iniciar() throws IOException {
        System.out.println("Servidor à escuta...");
        while (true) {
            Socket socket = serverSocket.accept();

            new Rececao(socket, this).start();
        }
    }

    public synchronized int novoId() {
        ultimoId++;

        return ultimoId;
    }

    public synchronized void registar(TratadorCliente cliente) {
        clientes.add(cliente);

        System.out.println("Registado: " + cliente);
    }

    public synchronized void remover(TratadorCliente cliente) {
        clientes.remove(cliente);

        System.out.println("Saiu: " + cliente);
    }

    public static void main(String[] args) throws IOException {
        int porto = Integer.parseInt(args[0]);

        new Servidor(porto).iniciar();
    }
}
