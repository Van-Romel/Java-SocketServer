package local.javaredes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Van-Romel Alves Sena Neto
 *
 */
public class Cliente {

    private static Socket conexao;
    private static DataInputStream entrada;
    private static DataOutputStream saida;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            conexao = new Socket("127.0.0.1", 55000);

            saida = new DataOutputStream(conexao.getOutputStream());

            System.out.println("Digite um CPF para verificação:");

            var inputCpf = scanner.next();

            saida.writeUTF(inputCpf);

            entrada = new DataInputStream(conexao.getInputStream());
            String resposta = entrada.readUTF();
            System.out.printf("Resposta do servidor: %s", resposta);

            conexao.close();
        } catch (IOException exception) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, exception);
        }

    }
}