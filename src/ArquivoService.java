import java.io.FileOutputStream;
import java.io.IOException;

public class ArquivoService {

        static private final String pastaArquivosServidor = "E:\\Pontificia Universidade Catolica de Goias\\10_periodo\\Sistemas Distribuídos\\N1\\Trabalho\\data\\servidor\\"; // (Windows)
//    static private final String pastaArquivosServidor = "/mnt/e/Pontificia Universidade Catolica de Goias/10_periodo/Sistemas Distribuídos/N1/Trabalho/data/servidor/"; // (Linux)

    public void salvarArquivo(Arquivo arquivo) throws IOException {
            String dir = pastaArquivosServidor + arquivo.getNome();

            System.out.println("Armazenando arquivo " + dir);
            FileOutputStream fos = new FileOutputStream(dir);
            fos.write(arquivo.getConteudo());
            fos.close();
    }

}
