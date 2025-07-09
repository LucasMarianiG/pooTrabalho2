package academico;

import java.io.BufferedWriter;
import java.io.IOException;
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

    private AlunoProva getAlunoProva(String cpf) throws PessoaNaoEncontradaException {
        for(AlunoProva ap : this.notas) if (ap.getAluno().getCpf().equals(cpf)) return ap;

        throw new PessoaNaoEncontradaException("Erro: Aluno não encontrado");
    }

    @Override
    public double nota(String cpf) {
        try {
            AlunoProva ap = this.getAlunoProva(cpf);
            return ap.notaTotal();
        } catch (PessoaNaoEncontradaException error) {
            return 0;
        }
    }

    public void setNotas(List<AlunoProva> notas) {
        this.notas.addAll(notas);
    }

    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write("PROV\n");
        super.salvarDadosArq(buff);
        buff.write(this.nQuestoes + "\n");

        for (AlunoProva ap: this.notas) {
            for (double nota: ap.getNotas()) buff.write(nota + "\n");
        }
    }
}
