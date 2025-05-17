package academico;

import java.util.ArrayList;
import java.util.Vector;

public class AlunoProva {
    private Aluno aluno;
    private double[] notas;

    public AlunoProva(Aluno aluno, int nQuestoes) {
        this.aluno = aluno;
        this.notas = new double[nQuestoes];
    }

    public double notaTotal() {
        double total = 0;

        for (int i = 0; i < this.notas.length; i++) {
            total += this.notas[i];
        }

        return total;
    }

    public Aluno getAluno() {
        return aluno;
    }
}
