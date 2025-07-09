package academico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public abstract class Avaliacao {
    protected String nome;
    protected Data dtAplic;
    protected double valor;

    public Avaliacao(String nome, Data dtAplic, double valor) {
        this.nome = nome;
        this.dtAplic = dtAplic;
        this.valor = valor;
    }

    public abstract double nota(String cpf);

    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write(this.nome + "\n");
        buff.write(this.dtAplic.getDia() + "\n");
        buff.write(this.dtAplic.getMes() + "\n");
        buff.write(this.dtAplic.getAno() + "\n");
        buff.write(this.valor + "\n");
    }
}
