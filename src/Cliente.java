import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cliente {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.print("Informe o caminho do arquivo: ");
            String caminhoArquivo = in.nextLine();
            System.out.println(caminhoArquivo);

            String[] partesCaminho = caminhoArquivo.split("\\\\");

            byte[] bytes = Files.readAllBytes(Paths.get(caminhoArquivo));

            Arquivo arquivo = new Arquivo();
            arquivo.setConteudo(bytes);
            arquivo.setNome(partesCaminho[partesCaminho.length - 1]);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream ous;
            ous = new ObjectOutputStream(bao);
            ous.writeObject(arquivo);

            try {
                Socket socket = new Socket("127.0.0.1", 5566);

                BufferedOutputStream bf = new BufferedOutputStream(socket.getOutputStream());
                bf.write(bao.toByteArray());
                bf.flush();
                bf.close();

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
