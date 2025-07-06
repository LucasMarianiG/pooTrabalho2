package academico;

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
}
