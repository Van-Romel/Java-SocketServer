package local.javaredes;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Van-Romel Alves Sena Neto
 */
public class FormCliente implements ActionListener {

    private static final FormCliente formCliente;
    private static final JFrame janela;
    private static final JLabel rotuloNome;
    private static final JTextField caixaTextoNome;
    private static final JLabel rotuloIdade;
    private static final JTextField caixaTextoIdade;
    private static final JLabel rotuloRetorno;
    private static final JTextArea caixaTextoRetorno;
    private static final JButton jButtonEnviar;

    static {
        formCliente = new FormCliente();
        janela = new JFrame();
        rotuloNome = new JLabel();
        caixaTextoNome = new JTextField(55);
        rotuloIdade = new JLabel();
        caixaTextoIdade = new JTextField(55);
        rotuloRetorno = new JLabel();
        caixaTextoRetorno = new JTextArea(6, 55);
        jButtonEnviar = new JButton();
    }

    private static Socket conexao;
    private static DataInputStream entrada;
    private static ObjectOutputStream saida;

    public static void main(String[] args) {

        janela.setSize(600, 300);
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        janela.setLayout(new GridLayout(7,1));

        rotuloNome.setText("Nome");
        rotuloIdade.setText("Idade");
        rotuloRetorno.setText("Retorno do Servidor");
        caixaTextoRetorno.setEditable(false);
        jButtonEnviar.setText("Enviar");
        jButtonEnviar.addActionListener(formCliente);
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

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(jButtonEnviar)) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(caixaTextoNome.getText());

            pessoa.setIdade(Integer.parseInt(caixaTextoIdade.getText()));

            try {
                conexao = new Socket("127.0.0.1", 55000);
                saida = new ObjectOutputStream(conexao.getOutputStream());

                saida.writeObject(pessoa);

                entrada = new DataInputStream(conexao.getInputStream());
                String resposta = entrada.readUTF();
                caixaTextoRetorno.setText(String.format("Recebeu do servidor: %n%s", resposta));

                conexao.close();
            } catch (IOException  exception) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, exception);

                try {
                    conexao.close();
                } catch (IOException ignored) {}
            }
        }
    }
}