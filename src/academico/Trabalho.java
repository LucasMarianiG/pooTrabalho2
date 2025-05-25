package academico;

import java.util.ArrayList;
import java.util.List;

public class Trabalho extends Avaliacao {
    private int nIntegrantes;
    private List<GrupoTrabalho> grupos;

    public Trabalho(String nome, Data dtAplic, double valor, int nIntegrantes) {
        super(nome, dtAplic, valor);
        this.grupos = new ArrayList<>();
        this.nIntegrantes = nIntegrantes;
    }

    public double nota(String cpf) {
        for(GrupoTrabalho gt : this.grupos) {
            if (gt.alunoNoGrupo(cpf)) {
                return gt.getNota();
            }
        }

        return 0;
    }
}
