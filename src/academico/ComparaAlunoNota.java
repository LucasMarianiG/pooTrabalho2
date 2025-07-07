package academico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparaAlunoNota implements Comparator<AlunoProva> {
    private List<AlunoProva> alunosNotas;

    public ComparaAlunoNota() {
        this.alunosNotas = new ArrayList<>();
    }

    public List<AlunoProva> getAlunosNotas() {
        return this.alunosNotas;
    }

    public void addAlunoProva(AlunoProva ap) {
        this.alunosNotas.add(ap);
    }

    public int compare(AlunoProva ap1, AlunoProva ap2) {
        if (ap1.notaTotal() < ap2.notaTotal()) return 1;
        if (ap1.notaTotal() > ap2.notaTotal()) return -1;

        return ap1.getAluno().getNome().compareTo(ap2.getAluno().getNome());
    }
}
