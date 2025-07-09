package academico;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public abstract String toString();

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write(this.nome + "\n");
        buff.write(this.cpf + "\n");
    }
}
