package academico;

import java.util.ArrayList;

public class GrupoTrabalho {
    private ArrayList<Aluno> alunos;
    private double nota;

    public GrupoTrabalho() {
        this.alunos = new ArrayList<>();
        this.nota = 0;
    }

    public double getNota() {
        return this.nota;
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
