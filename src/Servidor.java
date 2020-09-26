import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor para armazenamento de arquivos via socket.
 *
 * O cliente se comunica com o servidor via socket por meio de <code>Requisicao</code>.
 *
 * Mensagens:
 *
 *  O servidor deverá armazenar um arquivo em seu sistema de arquivos caso receba uma requisicao da seguinte forma:
 *  Requisicao(
 *      tipoRequisicao = <code>TipoRequisicao.PUT</code>
 *      action = "file/send"
 *      body = <code>Arquivo</code> que deverá ser enviado
 *  )
 *
 * @see Requisicao
 * @see TipoRequisicao
 * @see Arquivo
 */
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
