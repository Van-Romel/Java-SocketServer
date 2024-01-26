package local.javaredes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    private static DataInputStream entrada;
    private static DataOutputStream saida;

    public static void main(String[] args) {

        try {
            servidor = new ServerSocket(55000);
            conexao = servidor.accept();

            entrada = new DataInputStream(conexao.getInputStream());
            String valor = entrada.readUTF();

            // valida cpf
            String resultado = "";
            resultado = validaCpf(valor) ? "Este CPF é válido." : "Este CPF é inválido.";

            saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(resultado);

            conexao.close();
        } catch (IOException exception) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private static boolean validaCpf(String cpf) {

        // Este algoritimo foi feito e implementado por mim com inspiração do recurso B do PDF da atividade.
        // Recurso B = https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097

        cpf = cpf.replaceAll("\\.", "")
                .replaceAll("-", "");

        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (!cpf.matches("^(\\d{3}\\.){2}\\d{3}-\\d{2}|\\d{11}$")))
            return(false);

        int somaValoresDigitos = 0;
        int auxiliarFatorialInversa = 10;
        int numero;
        char dig10, dig11;
        for (int i = 0; i< 9; i++) {
            numero = (cpf.charAt(i) - 48);
            somaValoresDigitos = somaValoresDigitos + (numero * auxiliarFatorialInversa);
            auxiliarFatorialInversa = auxiliarFatorialInversa - 1;
        }

        int resultado = 11 - (somaValoresDigitos % 11);
        if ((resultado == 10) || (resultado == 11))
            dig10 = '0';
        else dig10 = (char)(resultado + 48);

        somaValoresDigitos = 0;
        auxiliarFatorialInversa = 11;
        for(int i = 0; i < 10; i++) {
            numero = (int)(cpf.charAt(i) - 48);
            somaValoresDigitos = somaValoresDigitos + (numero * auxiliarFatorialInversa);
            auxiliarFatorialInversa = auxiliarFatorialInversa - 1;
        }

        resultado = 11 - (somaValoresDigitos % 11);
        if ((resultado == 10) || (resultado == 11))
            dig11 = '0';
        else dig11 = (char)(resultado + 48);

        if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
            return(true);
        else return(false);
    }
}