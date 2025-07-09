package academico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turma implements Comparable<Turma> {
    private String nome;
    private int ano, sem;
    private Professor prof;
    private List<Aluno> alunos;
    private List<Avaliacao> avs;

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

    public String getNome() {
        return this.nome;
    }

    public int getAno() {
        return this.ano;
    }

    public int getSem() {
        return this.sem;
    }

    public Professor getProf() {
        return this.prof;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos.addAll(alunos);
    }

    public void setAvaliacoes(List<Avaliacao> avs) {
        this.avs.addAll(avs);
    }

    public void medias() {
        double somaTotal = 0;
        int qtdAvaliacoes = this.avs.size();

        // Incluindo variáveis auxiliares para realizar a ordenação
        ComparaAlunoNota comparaAluno = new ComparaAlunoNota();

        // Capturando as notas dos alunos para facilitar a ordenação
        for (Aluno aluno : this.alunos) {
            double[] notas = new double[qtdAvaliacoes];
            // Pegando as notas do aluno em cada avaliação e armazenando no array
            for (int i = 0; i < qtdAvaliacoes; i++) {
                notas[i] = this.avs.get(i).nota(aluno.getCpf());
            }

            // Crio uma instância de AlunoProva, ela possui a estrutura ideal que podemos aproveitar para usar na ordenação
            AlunoProva alunoNota = new AlunoProva(aluno, qtdAvaliacoes);
            // Adiciono as notas do aluno no array da instância de AlunoProva criada
            alunoNota.setNotas(notas);
            // Adiciono a instância AlunoProva na lista para ordenação
            comparaAluno.addAlunoProva(alunoNota);
        }

        // Ordenando os alunos pela nota mais alta
        Collections.sort(comparaAluno.getAlunosNotas(), comparaAluno);

        System.out.println("Médias da Turma: " + this + ":");

        // Imprimindo na tela os alunos com suas notas já ordenadas
        for(AlunoProva ap: comparaAluno.getAlunosNotas()) {
            // Imprimindo os dados do aluno
            System.out.print(ap.getAluno() + ": " );

            // Imprimindo as notas de cada avaliação do aluno
            for (double nota : ap.getNotas()) System.out.print(nota + " ");

            // Capturando a nota total do aluno
            double notaTotal = ap.notaTotal();

            // Verificando se a nota do aluno foi maior que 100, se for, ele limita em MAX:100//
            double notaFinal = notaTotal > 100 ? 100: notaTotal;

            // Imprimindo a nota final do aluno
            System.out.println("= " + notaFinal);
            somaTotal += notaFinal;
        }

        System.out.println("Média da Turma: " + (comparaAluno.getAlunosNotas().isEmpty() ? 0.0 : somaTotal/comparaAluno.getAlunosNotas().size()) + "\n");
    }

    public int compareTo(Turma t2) {
        // Comparando os extremos dos anos das turmas
        if (this.ano < t2.getAno()) return 1;
        if (this.ano > t2.getAno()) return -1;

        // Comparando os extremos dos semestres das turmas
        if (this.sem < t2.getSem()) return 1;
        if (this.sem > t2.getSem()) return -1;

        int compTurmaNome = this.nome.compareTo(t2.getNome());
        if (compTurmaNome != 0) return compTurmaNome;

        return this.prof.getNome().compareTo(t2.getProf().getNome());
    }

    public void salvarDadosArq(BufferedWriter buff) throws IOException {
        buff.write("TUR\n");

        // Dados da turma
        buff.write(this.nome + "\n");
        buff.write(this.ano + "\n");
        buff.write(this.sem + "\n");
        buff.write(this.prof.getCpf() + "\n");

        // Dados dos alunos
        buff.write(this.alunos.size() + "\n");
        for (Aluno a: this.alunos) buff.write(a.getMat() + "\n");

        // Dados das avaliações
        buff.write(this.avs.size() + "\n");
        for (Avaliacao av: this.avs) av.salvarDadosArq(buff);
    }
}

