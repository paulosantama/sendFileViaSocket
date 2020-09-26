import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread {

    private final Socket outputLine;

    PrintWriter pout = null;

    ArquivoService arquivoService;

    Connection(Socket socket) {
        outputLine = socket;
        arquivoService = new ArquivoService();
    }

    @Override
    public void run() {
        try {
            pout = new PrintWriter(outputLine.getOutputStream(), true);

            byte[] objectAsByte = new byte[outputLine.getReceiveBufferSize()];
            BufferedInputStream bf = new BufferedInputStream(outputLine.getInputStream());
            bf.read(objectAsByte);

            Requisicao requisicao = (Requisicao) getObjectFromByte(objectAsByte);

            analisarRota(requisicao);

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

    private void prepararSalvarArquivo(Requisicao requisicao) {
        try {
            Arquivo arquivo = (Arquivo) requisicao.getBody();
            arquivoService.salvarArquivo(arquivo);
            pout.println("O arquivo " + arquivo.getNome() + " foi salvo com sucesso no servidor.");
        } catch (IOException e) {
            pout.println("Erro ao salvar arquivo");
        }
    }

    private void analisarRota(Requisicao requisicao) {
        String[] rota = requisicao.getAction().split("/");
        switch (rota[0]) {
            case "file" :
                switch (rota[1]) {
                    case "send" :
                        prepararSalvarArquivo(requisicao);
                        break;
                    default:
                        retornaRotaNaoEncontrada();
                        break;
                }
                break;
            default:
                retornaRotaNaoEncontrada();
                break;
        }
    }

    private void retornaRotaNaoEncontrada() {
        pout.println("Rota não encontrada");
    }
}
