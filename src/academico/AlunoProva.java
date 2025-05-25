package academico;

public class AlunoProva {
    private Aluno aluno;
    private double[] notas;

    public AlunoProva(Aluno aluno, int nQuestoes) {
        this.aluno = aluno;
        this.notas = new double[nQuestoes];
    }

    public void setNotas(double[] notas) {
        this.notas = notas;
    }

    public double notaTotal() {
        double total = 0;

        for (double nota : this.notas) {
            total += nota;
        }

        return total;
    }

    public Aluno getAluno() {
        return aluno;
    }
}
