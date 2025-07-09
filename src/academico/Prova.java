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

    public void salvarDadosArq(BufferedWriter buff, List<Aluno> alunos) throws IOException {
        buff.write("PROV\n");
        super.salvarDadosArq(buff, alunos);
        buff.write(this.nQuestoes + "\n");

        for (Aluno a: alunos) {
            try {
                // Pegando as notas por Aluno
                // Pode ser que em algum momento a lista de alunos tenha sido ordenada anteriormente
                // Então salvamos as notas das provas de acordo com a ordem dos alunos
                for (double nota: this.getAlunoProva(a.getCpf()).getNotas()) buff.write(nota + "\n");
            } catch (PessoaNaoEncontradaException error) {
                // Cremos que os dados do Aluno na prova serão encontrados, mas caso não sejam serão salvos com a nota zerada
                for (int i = 0; i < this.nQuestoes; i++) buff.write("0.0\n");
            }
        }
    }
}
