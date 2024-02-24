package local.javaredes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Van-Romel Alves Sena Neto
 *
 */
public class Servidor {

    private static ServerSocket servidor;
    private static Socket conexao;
    private static ObjectInputStream entrada;
    private static DataOutputStream saida;

    public static void main(String[] args) {

        try {
            servidor = new ServerSocket(55000);
            System.out.println("Aguardando conexao...");
            conexao = servidor.accept();

            entrada = new ObjectInputStream(conexao.getInputStream());
            Pessoa pessoa = (Pessoa) entrada.readObject();
            System.out.printf("Nome: %s%n", pessoa.getNome());
            System.out.printf("Idade: %d", pessoa.getIdade());

            saida = new DataOutputStream(conexao.getOutputStream());

            saida.writeUTF("Dados recebidos corretamente!");

            conexao.close();
        } catch (IOException | ClassNotFoundException exception) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}