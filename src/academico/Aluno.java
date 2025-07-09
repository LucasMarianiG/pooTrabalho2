package academico;

import java.io.BufferedWriter;
import java.io.IOException;

public class Aluno extends Pessoa {
    private String mat;

    public Aluno(String nome, String cpf, String mat) {
        super(nome, cpf);
        this.mat = mat;
    }

    @Override
    public String toString() {
        return this.nome + " (Matrícula: " + this.mat + ")";
    }

    public String getMat() {
        return this.mat;
    }

    @Override
    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write("ALU\n");
        super.salvarDadosArq(buff);
        buff.write(this.mat + "\n");
    }
}
