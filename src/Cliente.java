import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cliente {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.print("Informe o caminho do arquivo: ");
            String caminhoArquivo = scanner.nextLine();

            String[] partesCaminho = caminhoArquivo.split("\\\\");

            byte[] bytes = Files.readAllBytes(Paths.get(caminhoArquivo));

            Arquivo arquivo = new Arquivo();
            arquivo.setConteudo(bytes);
            arquivo.setNome(partesCaminho[partesCaminho.length - 1]);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream;
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(arquivo);

            try {
                Socket socket = new Socket("127.0.0.1", 5566);

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                bufferedOutputStream.write(byteArrayOutputStream.toByteArray());

                bufferedOutputStream.flush();

                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                System.out.println(bufferedReader.readLine());

                bufferedOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
