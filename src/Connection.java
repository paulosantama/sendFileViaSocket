import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread {

    static private final String pastaArquivosServidor = "E:\\Pontificia Universidade Catolica de Goias\\10_periodo\\Sistemas Distribuídos\\N1\\Trabalho\\data\\servidor\\";
    private final Socket outputLine;

    Connection(Socket socket) {
        outputLine = socket;
    }

    @Override
    public void run() {
        PrintWriter pout = null;
        try {
            pout = new PrintWriter(outputLine.getOutputStream(), true);

            byte[] objectAsByte = new byte[outputLine.getReceiveBufferSize()];
            BufferedInputStream bf = new BufferedInputStream(outputLine.getInputStream());
            bf.read(objectAsByte);

            Arquivo arquivo = (Arquivo) getObjectFromByte(objectAsByte);
            String dir = pastaArquivosServidor + arquivo.getNome();

            System.out.println("Armazenando arquivo " + dir);
            FileOutputStream fos = new FileOutputStream(dir);
            fos.write(arquivo.getConteudo());
            fos.close();

            pout.println("O arquivo " + arquivo.getNome() + " foi salvo com sucesso no servidor.");
            outputLine.close();
        } catch (SocketException e) {
            String msgErro = "Não foi possível receber as informações no servidor (Socket)";
            System.out.println(msgErro);
            if (pout != null) {
                pout.println(msgErro);
            }
        } catch (IOException e) {
            String msgErro = "Problema na comunicação";
            System.out.println(msgErro);
            if (pout != null) {
                pout.println(msgErro);
            }
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
