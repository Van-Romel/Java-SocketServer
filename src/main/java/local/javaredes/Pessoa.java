package local.javaredes;

import java.io.Serializable;

/**
 * @author Van-Romel Alves Sena Neto
 */
public class Pessoa implements Serializable {

    private String nome;
    private int idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
