package academico;

import java.util.ArrayList;
import java.util.List;

public class Prova extends Avaliacao {
    private int nQuestoes;
    private List<AlunoProva> notas;

    public Prova(String nome, Data dtAplic, double valor, int nQuestoes) {
        super(nome, dtAplic, valor);
        this.nQuestoes = nQuestoes;
        this.notas = new ArrayList<>();
    }

    @Override
    public double nota(String cpf) {
        for(AlunoProva ap : this.notas) {
            if (ap.getAluno().getCpf().equals(cpf)) {
                return ap.notaTotal();
            }
        }

        return 0;
    }
}
