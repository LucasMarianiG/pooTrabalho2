package academico;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Professor> profs;

    public Sistema() {
        this.profs = new ArrayList<>();
    }

    /***************************************************/

    public void novoProf(Professor p) {
        this.profs.add(p);
    }

    /***************************************************/

    public Professor encontrarProfessor(String cpf) {
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    /***************************************************/

    public void listarProfs() {
        if (this.profs.size() > 0) {
            System.out.println("Professores cadastrados:");
            for (Professor p : this.profs) {
                System.out.println("* " + p);
            }
        }
        else {
            System.out.println("Nenhum professor cadastrado até o momento.");
        }
    }

}
