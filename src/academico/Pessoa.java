package academico;

public class Pessoa {
    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + this.cpf + ")";
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

}
