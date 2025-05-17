package academico;

public class Aluno extends Pessoa {
    private String mat;

    public Aluno(String nome, String cpf, String mat) {
        super(nome, cpf);
        this.mat = mat;
    }

    @Override
    public String toString() {
        return nome + " (Matrícula: " + this.mat + ")";
    }

}
