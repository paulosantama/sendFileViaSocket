import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cliente {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Cliente Iniciado\n");
        while (true) {
            System.out.print("Informe o caminho do arquivo: ");
            String caminhoArquivo = scanner.nextLine();

            String[] partesCaminho = caminhoArquivo.split("\\\\");
            byte[] bytes = null;

            try {
                bytes = Files.readAllBytes(Paths.get(caminhoArquivo));
            } catch (NoSuchFileException e) {
                System.out.println("Arquivo não encontrado");
                continue;
            } catch (InvalidPathException e) {
                System.out.println("O caminho do arquivo é inválido");
                continue;
            }

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
                System.out.println("A conexão com o servidor não pôde ser efetuada");
            }
        }
    }

}
