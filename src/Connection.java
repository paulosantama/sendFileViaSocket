import java.io.*;
import java.net.Socket;

public class Connection extends Thread {

    static private final String pastaArquivosServidor = "E:\\Pontificia Universidade Catolica de Goias\\10_periodo\\Sistemas Distribuídos\\N1\\Trabalho\\data\\servidor\\";
    private final Socket outputLine;

    Connection(Socket socket) {
        outputLine = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter pout = new PrintWriter(outputLine.getOutputStream(), true);

            byte[] objectAsByte = new byte[outputLine.getReceiveBufferSize()];
            BufferedInputStream bf = new BufferedInputStream(outputLine.getInputStream());
            bf.read(objectAsByte);

            Arquivo arquivo = (Arquivo) getObjectFromByte(objectAsByte);
            String dir = pastaArquivosServidor + arquivo.getNome();
            System.out.println("Escrevendo arquivo " + dir);

            FileOutputStream fos = new FileOutputStream(dir);
            fos.write(arquivo.getConteudo());
            fos.close();

            pout.println("O arquivo " + arquivo.getNome() + " foi salvo com sucesso no servidor.");
            outputLine.close();
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
