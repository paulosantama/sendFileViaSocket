import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Erro: Necessário informar a porta que o servidor escutará");
            return;
        }

        int portaServidor = Integer.parseInt(args[0]);

        System.out.println("Servidor Iniciado\n");
        System.out.println("Escutando na porta "+args[0]);

        try {
            ServerSocket srvSocket = new ServerSocket(portaServidor);
            System.out.println("Aguardando...");
            while (true) {
                Socket client = srvSocket.accept();

                Connection connection = new Connection(client);
                connection.start();
            }
        } catch (IOException e) {
            System.out.println("Falha na comunicação");
        }
    }

}
