import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static private final String pastaArquivosServidor = "E:\\Pontificia Universidade Catolica de Goias\\10_periodo\\Sistemas Distribuídos\\N1\\Trabalho\\data\\servidor\\";

    public static void main(String[] args) {
        try {
            ServerSocket srvSocket = new ServerSocket(5566);
            System.out.println("Aguardando envio de arquivo ...");
            while (true) {
                Socket socket = srvSocket.accept();

                byte[] objectAsByte = new byte[socket.getReceiveBufferSize()];
                BufferedInputStream bf = new BufferedInputStream(
                        socket.getInputStream());
                bf.read(objectAsByte);

                Arquivo arquivo = (Arquivo) getObjectFromByte(objectAsByte);
                String dir = pastaArquivosServidor + arquivo.getNome();
                System.out.println("Escrevendo arquivo " + dir);

                FileOutputStream fos = new FileOutputStream(dir);
                fos.write(arquivo.getConteudo());
                fos.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Problema ao salvar o arquivo");
        } catch (IOException e) {
            System.out.println("Problema de IO");
        }

    }

    private static Object getObjectFromByte(byte[] objectAsByte) {
        Object obj = null;
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        try {
            bis = new ByteArrayInputStream(objectAsByte);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();

            bis.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
