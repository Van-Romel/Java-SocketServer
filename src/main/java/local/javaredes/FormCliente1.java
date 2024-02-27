package local.javaredes;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Van-Romel Alves Sena Neto
 */
public class FormCliente1 implements Runnable {

    private static final JFrame janela;
    private static final JLabel rotuloNome;
    private static final JTextField caixaTextoNome;
    private static final JLabel rotuloIdade;
    private static final JTextField caixaTextoIdade;
    private static final JLabel rotuloRetorno;
    private static final JTextArea caixaTextoRetorno;
    private static final JButton jButtonEnviar;
    private DatagramSocket conexao;
    private DatagramPacket datagrama;
    private ByteArrayOutputStream saidaStream;
    private ObjectOutputStream saida;

    private final String nome;

    private final int idade;

    static {
        janela = new JFrame();
        rotuloNome = new JLabel();
        caixaTextoNome = new JTextField(55);
        rotuloIdade = new JLabel();
        caixaTextoIdade = new JTextField(55);
        rotuloRetorno = new JLabel();
        caixaTextoRetorno = new JTextArea(6, 55);
        jButtonEnviar = new JButton();
    }

    public FormCliente1(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }


    public static void main(String[] args) {
        janela.setSize(600, 300);
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        rotuloNome.setText("Nome");
        rotuloIdade.setText("Idade");
        rotuloRetorno.setText("Retorno do Servidor");
        caixaTextoRetorno.setEditable(false);
        jButtonEnviar.setText("Enviar");
        jButtonEnviar.addActionListener(FormCliente1::jBEnviarActionPerformed);
        jButtonEnviar.setFocusable(false);

        janela.add(rotuloNome);
        janela.add(caixaTextoNome);
        janela.add(rotuloIdade);
        janela.add(caixaTextoIdade);
        janela.add(rotuloRetorno);
        janela.add(caixaTextoRetorno);
        janela.add(jButtonEnviar);
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        janela.setLayout(flowLayout);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
    private static void jBEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviarActionPerformed
        SwingUtilities.invokeLater(() -> {
            try {
                new Thread(new FormCliente1(caixaTextoNome.getText(), Integer.parseInt(caixaTextoIdade.getText()))).start();
                limparCampos();
            } catch (NumberFormatException ex) {
                caixaTextoRetorno.append("Falha no preenchimento, por favor verifique os campos.\n");
            }
        });
    }

    private static void limparCampos() {
        caixaTextoNome.setText("");
        caixaTextoIdade.setText("");
    }

    @Override
    public void run() {

        try {
            this.conexao = new DatagramSocket();

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(nome);
            pessoa.setIdade(idade);

            saidaStream = new ByteArrayOutputStream();
            saida = new ObjectOutputStream(saidaStream);
            saida.writeObject(pessoa);
            byte[] dados = saidaStream.toByteArray();
            DatagramPacket pacoteEnviado = new DatagramPacket(dados, dados.length,
                    InetAddress.getByName("127.0.0.1"), 55000);

            conexao.send(pacoteEnviado);

            datagrama = new DatagramPacket(new byte[120], 120);
            conexao.receive(datagrama);
            var respostaServidor = new String(datagrama.getData());

            caixaTextoRetorno.append(respostaServidor + "\n");
        } catch (SocketException e) {
            var errorMessage = "Não foi possivel estabelecer conxão com o servidor.";
            System.out.println(errorMessage);
        } catch (IOException e) {
            var errorMessage = "Não foi possivel manipular o objeto.";
            System.out.println(errorMessage);
        }
    }
}