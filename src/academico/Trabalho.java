package academico;

import java.util.ArrayList;

public class Trabalho extends Avaliacao {
    private int nIntegrantes;
    private ArrayList<GrupoTrabalho> grupos;

    public Trabalho(String nome, Data dtAplic, double valor, int nIntegrantes) {
        super(nome, dtAplic, valor);
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
