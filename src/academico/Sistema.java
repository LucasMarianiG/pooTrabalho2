package academico;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    /***************************************************/

    public void novoProf(Professor p) {
        this.profs.add(p);
    }

    /***************************************************/

    public void novoAluno(Aluno a) {
        this.alunos.add(a);
    }

    /***************************************************/

    public void novaTurma(Turma t) {
        this.turmas.add(t);
    }

    /***************************************************/

    public Professor encontrarProfessor(String cpf) throws PessoaNaoEncontradaException {
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) return p;
        }

        throw new PessoaNaoEncontradaException("Erro: Professor (CPF '" + cpf + "') não encontrado.");
    }

    /***************************************************/

    public Aluno encontrarAluno(String mat) throws PessoaNaoEncontradaException {
        for (Aluno a : this.alunos) {
            if (a.getMat().equals(mat)) {
                return a;
            }
        }

        throw new PessoaNaoEncontradaException("Erro: Aluno (Matrícula '" + mat + "') não encontrado.");
    }

    /***************************************************/

    public void listarProfs() {
        if (!this.profs.isEmpty()) {
            System.out.println("Professores cadastrados:");

            for (Professor p : this.profs) {
                System.out.println("* " + p);
            }
        } else {
            System.out.println("Nenhum professor cadastrado até o momento.");
        }
    }

    /***************************************************/

    public void listarAlunos() {
        if (!this.alunos.isEmpty()) {
            System.out.println("Alunos cadastrados:");

            for (Aluno a : this.alunos) {
                System.out.println("* " + a);
            }
        } else {
            System.out.println("Nenhum aluno cadastrado até o momento.");
        }
    }

    /***************************************************/

    public void listarTurmas() {
        if (!this.turmas.isEmpty()) {
            System.out.println("Turmas cadastradas:");

            for (Turma t : this.turmas) {
                t.medias();
            }
        } else {
            System.out.println("Nenhuma turma cadastrada até o momento.");
        }
    }

    /***************************************************/

    public int qtdProfs() {
        return this.profs.size();
    }

    /***************************************************/

    public int qtdAlunos() {
        return this.alunos.size();
    }

    /***************************************************/

    public int qtdTurmas() {
        return this.turmas.size();
    }
}
