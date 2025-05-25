package academico;

import java.util.ArrayList;
import java.util.List;

public class GrupoTrabalho {
    private List<Aluno> alunos;
    private double nota;

    public GrupoTrabalho() {
        this.alunos = new ArrayList<>();
        this.nota = 0;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos.addAll(alunos);
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public boolean alunoNoGrupo(String cpf) {
        for(Aluno a : this.alunos) {
            if (a.getCpf().equals(cpf)) {
                return true;
            }
        }

        return false;
    }
}
