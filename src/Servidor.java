import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        System.out.println("Servidor Iniciado");
        try {
            ServerSocket srvSocket = new ServerSocket(5566);
            System.out.println("Aguardando envio de arquivo ...");
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
