package local.javaredes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Van-Romel Alves Sena Neto
 *
 */
public class Servidor {

    private static DatagramSocket conexao;
    private static DatagramPacket datagrama;

    private static ByteArrayInputStream entradaStream;
    private static ObjectInputStream entrada;

    public static void main(String[] args) {

        try {
            conexao = new DatagramSocket(55000);

            while (true) {
                System.out.println("Aguardando conexao...");
                datagrama = new DatagramPacket(new byte[1024], 1024);
                conexao.receive(datagrama);

                entradaStream = new ByteArrayInputStream(datagrama.getData());
                entrada = new ObjectInputStream(entradaStream);
                Pessoa pessoa = (Pessoa) entrada.readObject();
                System.out.printf("%nObjeto recebido:%n\tNome: %s%n\tIdade: %d%n", pessoa.getNome(), pessoa.getIdade());

                var resposta = "Dados recebidos corretamente!".getBytes();
                datagrama = new DatagramPacket(resposta, resposta.length, datagrama.getAddress(), datagrama.getPort());
                conexao.send(datagrama);
            }
        } catch (IOException | ClassNotFoundException exception) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}