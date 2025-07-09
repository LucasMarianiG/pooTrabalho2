package academico;

import java.io.BufferedWriter;
import java.io.IOException;
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

    public void setGrupos(List<GrupoTrabalho> grupos) {
        this.grupos.addAll(grupos);
    }

    public double nota(String cpf) {
        for(GrupoTrabalho gt : this.grupos) {
            if (gt.alunoNoGrupo(cpf)) {
                return gt.getNota();
            }
        }

        return 0;
    }

    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write("TRAB\n");
        super.salvarDadosArq(buff);
        buff.write(this.nIntegrantes + "\n");
        buff.write(this.grupos.size() + "\n");

        for(GrupoTrabalho gt : this.grupos) gt.salvarDadosArq(buff);
    }
}
