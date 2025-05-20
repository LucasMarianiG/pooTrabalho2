package academico;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Turma {
    private String nome;
    private int ano, sem;
    private Professor prof;
    private ArrayList<Aluno> alunos;
    private ArrayList<Avaliacao> avs;

    public Turma(String nome, int ano, int sem, Professor prof) {
        this.nome = nome;
        this.ano = ano;
        this.sem = sem;
        this.prof = prof;

        this.alunos = new ArrayList<>();
        this.avs = new ArrayList<>();
    }

    public String toString(){
        return this.nome + " ("+ this.ano + "/" + this.sem + ")";
    }

    public void medias(){
        int totalAluno = this.alunos.size();
        double somaTotal = 0;
        System.out.println("Médias da Turma: " + this + ":");
        for( Aluno a: this.alunos){
            double somaAluno = 0;
            System.out.print(a + ": " );
            for (Avaliacao av : this.avs){
                double nota = av.nota(a.getCpf());
                somaAluno += nota;
                System.out.print(nota + " ");
            }
            // Verificando se a nota do aluno foi maior que 100, se for, ele limita em MAX:100//
            somaAluno = somaAluno > 100 ? 100: somaAluno;
            System.out.println("= " + somaAluno);
            somaTotal = somaAluno;

        }
        System.out.println("Média da Turma: " + (somaTotal/(totalAluno == 0 ? 1 : totalAluno)));
    }


}

