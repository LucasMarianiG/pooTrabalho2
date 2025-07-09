package academico;

import java.io.BufferedWriter;
import java.io.IOException;

public class Professor extends Pessoa{
    private double salario;

    public Professor(String nome, String cpf, double salario) {
        super(nome, cpf);
        this.salario = salario;
    }

    @Override
    public String toString() {
        return this.nome + " (CPF: " + this.cpf + ")";
    }

    @Override
    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write("PROF\n");
        super.salvarDadosArq(buff);
        buff.write(this.salario + "\n");
    }
}
