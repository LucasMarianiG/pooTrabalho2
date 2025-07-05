package academico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior feat <Lucas Mariani e Douglas Bolis>
 */
public class Entrada {
    private Scanner input;

    /**
     * Construtor da classe academico.Entrada
     * Se houver um arquivo input.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     */
    public Entrada() {
        try {
            // Se houver um arquivo input.txt, o Scanner vai ler dele.
            this.input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            this.initialStatements();
        }
    }

    /**
     * Construtor da classe academico.Entrada
     * Se houver um arquivo input.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     */
    public Entrada(Sistema s) {
        try {
            FileReader file = new FileReader("dados.txt");
            BufferedReader buff = new BufferedReader(file);

            String line = buff.readLine();

            // TODO ?? verificar a cada leitura de linha se encontrou as tags na hora errada ??

            while (line != null && !line.equals("FIM")) {
                switch (line) {
                    case "PROF" -> this.lerProfArq(s, buff);
                    case "ALU" -> this.lerAlunoArq(s, buff);
                    case "TUR" -> this.lerTurmaArq(s, buff);
                    default -> System.out.println("Erro ao ler dados do arquivo");
                }
                line = buff.readLine();
            }

            buff.close();
        } catch (IOException error) {
            System.out.println("Erro: Tivemos um problema ao ler alguns dados do arquivo");
        } catch (NumberFormatException error) {
            System.out.println("Erro: Tivemos um problema ao converter uma das informações do arquivo para o tipo numérico");
        } finally {
            this.initialStatements();
        }
    }

    /***************************************************/

    private void initialStatements() {
        // Habilitando a leitura pelo teclado
        this.input = new Scanner(System.in);
    }

    /***************************************************/

    /**
     * Faz a leitura de uma linha inteira
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
     * @param msg: Mensagem que será exibida ao usuário
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinha(String msg) {
        // Imprime uma mensagem ao usuário, lê uma e retorna esta linha
        System.out.print(msg);
        String linha = this.input.nextLine();

        // Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
        while (linha.charAt(0) == '#') linha = this.input.nextLine();
        return linha;
    }

    /***************************************************/

    /**
     * Faz a leitura de um número inteiro
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um inteiro e retorna este inteiro
        String linha = this.lerLinha(msg);
        return Integer.parseInt(linha);
    }

    /***************************************************/

    /**
     * Faz a leitura de um double
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um double e retorna este double
        String linha = this.lerLinha(msg);
        return Double.parseDouble(linha);
    }

    /***************************************************/

    /**
     * Lê o CPF de um academico.Professor existente e retorna-o.
     * @param s: Um objeto da classe academico.Sistema
     * @return A instância do professor encontrado
     */
    private Professor lerProf(Sistema s) {
        String cpfProf = this.lerLinha("Digite o cpf do Professor: ");
        return s.encontrarProfessor(cpfProf);
    }

    /***************************************************/

    /**
     * Lê a MATRÍCULA de uma lista de academico.Aluno existentes e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @return A lista de alunos
     */
    private List<Aluno> lerAlunos(Sistema s) {
        List<Aluno> alunos = new ArrayList<>();
        int qtdAlunos = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");
        int alunosRegistrados = 0;

        while (alunosRegistrados < qtdAlunos) {
            String matAluno = this.lerLinha("Digite a matrícula do aluno: ");
            Aluno a = s.encontrarAluno(matAluno);

            if (a != null) {
                alunos.add(a);
                alunosRegistrados += 1;
                System.out.println(alunosRegistrados + "/" + qtdAlunos + " alunos registrados com sucesso.");
            } else {
                System.out.println("Erro: Aluno '" + matAluno + "' não encontrado.");
            }
        }

        return alunos;
    }

    /***************************************************/

    /**
     * Lê os dados de um aluno na Prova e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param aluno: Um objeto da classe academico.Aluno
     * @param qtdQuestoes: quantidade de questões na Prova
     * @return Um objeto de academico.AlunoProva
     */
    private AlunoProva lerAlunoProva(Sistema s, Aluno aluno, int qtdQuestoes) {
        AlunoProva ap = new AlunoProva(aluno, qtdQuestoes);
        double[] notas = new double[qtdQuestoes];
        double nota;

        for (int i = 0; i < qtdQuestoes; i++) {
            nota = this.lerDouble("Nota de " + aluno.getNome() + " na questão " + (i + 1) + ": ");
            notas[i] = nota;
        }

        ap.setNotas(notas);

        return ap;
    }

    /***************************************************/

    /**
     * Lê os dados da Prova e retorna-a.
     * @param s: Um objeto da classe academico.Sistema
     * @param alunos: Uma lista de academico.Aluno
     * @return Um objeto de academico.Prova
     */
    private Prova lerProva(Sistema s, List<Aluno> alunos) {
        List<AlunoProva> alunoProvas = new ArrayList<>();

        String nomeProva = this.lerLinha("Informe o nome desta prova: ");
        int diaProva = this.lerInteiro("Digite o dia da prova: ");
        int mesProva = this.lerInteiro("Digite o mês da prova: ");
        int anoProva = this.lerInteiro("Digite o ano da prova: ");
        double pontoMaxProva = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int qtdQuestoesProva = this.lerInteiro("Digite o número de questões: ");

        Data dataProva = new Data(diaProva, mesProva, anoProva);
        Prova prova = new Prova(nomeProva, dataProva, pontoMaxProva, qtdQuestoesProva);

        for (Aluno aluno : alunos) {
            AlunoProva ap = this.lerAlunoProva(s, aluno, qtdQuestoesProva);
            alunoProvas.add(ap);
        }

        prova.setNotas(alunoProvas);

        return prova;
    }

    /***************************************************/

    /**
     * Lê os dados dos alunos no GrupoTrabalho e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @return Um objeto de academico.GrupoTrabalho
     */
    private GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        GrupoTrabalho gt = new GrupoTrabalho();
        List<Aluno> alunos = new ArrayList<>();

        int alunosRegistrados = 0;
        int qtdAlunos = this.lerInteiro("Digite o número de alunos neste grupo: ");

        while (alunosRegistrados < qtdAlunos) {
            String matAluno = this.lerLinha("Digite a matrícula do aluno: ");
            Aluno a = s.encontrarAluno(matAluno);

            if (a != null) {
                alunos.add(a);
                alunosRegistrados += 1;
                System.out.println(alunosRegistrados + "/" + qtdAlunos + " alunos inseridos no grupo com sucesso.");
            } else {
                System.out.println("Erro: Aluno '" + matAluno + "' não encontrado.");
            }
        }

        double nota = this.lerDouble("Nota do grupo: ");
        gt.setAlunos(alunos);
        gt.setNota(nota);

        return gt;
    }

    /***************************************************/

    /**
     * Lê os dados do Trabalho e retorna-o.
     * @param s: Um objeto da classe academico.Sistema
     * @param alunos: Uma lista de academico.Aluno
     * @return Um objeto de academico.Trabalho
     */
    private Trabalho lerTrabalho(Sistema s, List<Aluno> alunos) {
        List<GrupoTrabalho> grupos = new ArrayList<>();

        String nomeTrab = this.lerLinha("Informe o nome desta avaliação: ");
        int diaTrab = this.lerInteiro("Digite o dia do trabalho: ");
        int mesTrab = this.lerInteiro("Digite o mês do trabalho: ");
        int anoTrab = this.lerInteiro("Digite o ano do trabalho: ");
        double pontoMaxTrab = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int qtdIntgranTrab = this.lerInteiro("Digite o número máximo de integrantes: ");

        Data dataTrab = new Data(diaTrab, mesTrab, anoTrab);
        Trabalho trab = new Trabalho(nomeTrab, dataTrab, pontoMaxTrab, qtdIntgranTrab);

        int numGrupos = this.lerInteiro("Digite o número de grupos: ");

        for (int i = 0; i < numGrupos; i++) {
            GrupoTrabalho grupo = this.lerGrupoTrabalho(s);
            grupos.add(grupo);
        }

        trab.setGrupos(grupos);

        return trab;
    }

    /***************************************************/

    /**
     * Lê a MATRÍCULA de uma lista de academico.Aluno existentes e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param alunos: Uma lista de objetos da classe academico.Aluno
     * @param nAvaliacoes: Um número da classe Integer
     * @return A lista de alunos
     */
    private List<Avaliacao> lerAvaliacoes(Sistema s, List<Aluno> alunos, int nAvaliacoes) {
        List<Avaliacao> avs = new ArrayList<>();
        int avaliacoesRegistradas = 0;

        while (avaliacoesRegistradas < nAvaliacoes) {
            int tipoAvaliacao = this.lerInteiro("Escolha um tipo de avaliação:\n1) Prova\n2)Trabalho\n");

            // Lendo os dados dos alunos de acordo com o tipo de avaliação digitada
            if (tipoAvaliacao == 1) {
                avaliacoesRegistradas += 1;

                // Lendo dados dos alunos na Prova
                Prova prv = this.lerProva(s, alunos);
                avs.add(prv);
            } else if (tipoAvaliacao == 2) {
                avaliacoesRegistradas += 1;

                // Lendo dados dos alunos no trabaho
                Trabalho trb = this.lerTrabalho(s, alunos);
                avs.add(trb);
            } else {
                System.out.println("Erro: Tipo de avaliação inválida.");
            }
        }

        return avs;
    }

    /***************************************************/

    /**
    * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
    * @return Inteiro contendo a opção escolhida pelo usuário
    */
    public int menu() {
        // Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
        String msg = "*********************\n" +
            "Escolha uma opção:\n" +
            "1) Cadastrar professor:\n" +
            "2) Cadastrar aluno:\n" +
            "3) Cadastrar turma:\n" +
            "4) Listar turmas:\n" +
            "0) Sair\n";

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Professor de um arquivo e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     */
    public void lerProfArq(Sistema s, BufferedReader buff) throws IOException {
        String nome = buff.readLine();
        String cpf = buff.readLine();
        double salario = Double.parseDouble(buff.readLine());

        if (s.encontrarProfessor(cpf) == null) { // Garantindo que o não CPF esteja duplicado.
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
        } else {
            System.out.println("Erro: CPF duplicado. academico.Professor não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Professor e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadProf(Sistema s) {
        s.listarProfs();

        String nome = this.lerLinha("Digite o nome do professor: ");
        String cpf = this.lerLinha("Digite o cpf do professor: ");
        double salario = this.lerDouble("Digite o salário do professor: R$ ");

        if (s.encontrarProfessor(cpf) == null) { // Garantindo que o não CPF esteja duplicado.
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
        } else {
            System.out.println("Erro: CPF duplicado. academico.Professor não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Aluno de um arquivo e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     */
    public void lerAlunoArq(Sistema s, BufferedReader buff) throws IOException {
        String nome = buff.readLine();
        String cpf = buff.readLine();
        String mat = buff.readLine();

        if (s.encontrarAluno(mat) == null) { // Garantindo que a Matrícula não esteja duplicada.
            Aluno a = new Aluno(nome, cpf, mat);
            s.novoAluno(a);
        } else {
            System.out.println("Erro: Matrícula duplicado. academico.Aluno não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Aluno e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadAluno(Sistema s) {
        s.listarAlunos();

        String nome = this.lerLinha("Digite o nome do aluno: ");
        String cpf = this.lerLinha("Digite o cpf do aluno: ");
        String mat = this.lerLinha("Digite a matrícula do aluno: ");

        if (s.encontrarAluno(mat) == null) { // Garantindo que a Matrícula não esteja duplicada.
            Aluno a = new Aluno(nome, cpf, mat);
            s.novoAluno(a);
        } else {
            System.out.println("Erro: Matrícula duplicado. academico.Aluno não adicionado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um aluno na Prova de um arquivo e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param aluno: Um objeto da classe academico.Aluno
     * @param qtdQuestoes: quantidade de questões na Prova
     * @return Um objeto de academico.AlunoProva
     */
    private AlunoProva lerAlunoProvaArq(Sistema s, BufferedReader buff, Aluno aluno, int qtdQuestoes) throws IOException, NumberFormatException {
        AlunoProva ap = new AlunoProva(aluno, qtdQuestoes);
        double[] notas = new double[qtdQuestoes];

        for (int i = 0; i < qtdQuestoes; i++) {
            notas[i] = Double.parseDouble(buff.readLine());
        }

        ap.setNotas(notas);

        return ap;
    }

    /***************************************************/

    /**
     * Lê os dados da Prova de um arquivo e retorna-a.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param alunos: Uma lista de academico.Aluno
     * @return Um objeto de academico.Prova
     */
    private Prova lerProvaArq(Sistema s, BufferedReader buff, List<Aluno> alunos, String nomeProva, Data dataProva, Double notaMaxProva, int qtdQuestoesProva) throws IOException, NumberFormatException {
        List<AlunoProva> alunoProvas = new ArrayList<>();
        Prova prova = new Prova(nomeProva, dataProva, notaMaxProva, qtdQuestoesProva);

        for (Aluno aluno : alunos) {
            AlunoProva ap = this.lerAlunoProvaArq(s, buff, aluno, qtdQuestoesProva);
            alunoProvas.add(ap);
        }

        prova.setNotas(alunoProvas);

        return prova;
    }

    /***************************************************/

    /**
     * Lê os dados dos alunos no GrupoTrabalho num arquivo e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param numeroGrupo: Um número da classe Integer
     * @param qtdIntgranTrab: Um número da classe Integer
     * @return Um objeto de academico.GrupoTrabalho
     */
    private GrupoTrabalho lerGrupoTrabalhoArq(Sistema s, BufferedReader buff, int numeroGrupo, int qtdIntgranTrab) throws IOException, NumberFormatException {
        GrupoTrabalho gt = new GrupoTrabalho();
        List<Aluno> alunos = new ArrayList<>();

        int alunosProcessados = 0;
        int qtdAlunos = Integer.parseInt(buff.readLine());

        if (qtdAlunos > qtdIntgranTrab) {
            return null;
        }

        while (alunosProcessados < qtdAlunos) {
            String matAluno = buff.readLine();
            Aluno a = s.encontrarAluno(matAluno);

            if (a != null) {
                alunos.add(a);
                System.out.println(alunos.size() + "/" + qtdAlunos + " alunos inseridos no grupo " + numeroGrupo + " com sucesso.");
            } else {
                System.out.println("Erro: Aluno '" + matAluno + "' não encontrado.");
            }

            alunosProcessados += 1;
        }

        double nota = Double.parseDouble(buff.readLine());
        gt.setAlunos(alunos);
        gt.setNota(nota);

        return gt;
    }

    /***************************************************/

    /**
     * Lê os dados do Trabalho de um arquivo e retorna-o.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param alunos: Uma lista de academico.Aluno
     * @return Um objeto de academico.Trabalho
     */
    private Trabalho lerTrabalhoArq(Sistema s, BufferedReader buff, List<Aluno> alunos, String nomeTrab, Data dataTrab, Double pontoMaxTrab, int qtdIntgranTrab) throws IOException, NumberFormatException {
        List<GrupoTrabalho> grupos = new ArrayList<>();
        Trabalho trab = new Trabalho(nomeTrab, dataTrab, pontoMaxTrab, qtdIntgranTrab);

        int numGrupos = Integer.parseInt(buff.readLine());

        for (int i = 0; i < numGrupos; i++) {
            GrupoTrabalho grupo = this.lerGrupoTrabalhoArq(s, buff, i + 1, qtdIntgranTrab);

            if (grupo != null) {
                grupos.add(grupo);
            }
        }

        if (grupos.isEmpty()) {
            return null;
        }

        trab.setGrupos(grupos);
        return trab;
    }

    /***************************************************/

    /**
     * Lê a MATRÍCULA de uma lista de academico.Aluno existentes e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param alunos: Uma lista de objetos da classe academico.Aluno
     * @param nAvaliacoes: Um número da classe Integer
     * @return A lista de alunos
     */
    private List<Avaliacao> lerAvaliacoesArq(Sistema s, BufferedReader buff, List<Aluno> alunos, int nAvaliacoes) throws IOException, NumberFormatException {
        List<Avaliacao> avs = new ArrayList<>();
        int avaliacoesProcessadas = 0;

        while (avaliacoesProcessadas < nAvaliacoes) {
            String tipoAvaliacao = buff.readLine();

            // Lendo os dados dos alunos de acordo com o tipo de avaliação digitada
            if (tipoAvaliacao.equals("PROV") || tipoAvaliacao.equals("TRAB")) {
                String nomeTrab = buff.readLine();
                int diaAv = Integer.parseInt(buff.readLine());
                int mesAv = Integer.parseInt(buff.readLine());
                int anoAv = Integer.parseInt(buff.readLine());
                double notaMaxAv = Double.parseDouble(buff.readLine());
                int qtdItemAv = Integer.parseInt(buff.readLine());

                Data dataAv = new Data(diaAv, mesAv, anoAv);

                if (tipoAvaliacao.equals("PROV")) {
                    // Lendo dados dos alunos na Prova
                    Prova prv = this.lerProvaArq(s, buff, alunos, nomeTrab, dataAv, notaMaxAv, qtdItemAv);
                    avs.add(prv);
                } else {
                    // Lendo dados dos alunos no trabaho
                    Trabalho trb = this.lerTrabalhoArq(s, buff, alunos, nomeTrab, dataAv, notaMaxAv, qtdItemAv);
                    avs.add(trb);
                }
            } else {
                System.out.println("Erro: Tipo de avaliação inválida.");
            }

            avaliacoesProcessadas += 1;
        }

        // Returna uma lista vazia se a quantidade de avaliações processadas for diferente do número de avaliações esperadas.
        return avs.size() == nAvaliacoes ? avs : new ArrayList<>();
    }

    /***************************************************/

    /**
     * Lê os dados de uma nova academico.Turma de um arquivo e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     */
    public void lerTurmaArq(Sistema s, BufferedReader buff) throws IOException, NumberFormatException {
        String nome = buff.readLine();
        int ano = Integer.parseInt(buff.readLine());
        int sem = Integer.parseInt(buff.readLine());
        String cpfProf = buff.readLine();

        // Encontrando o professor da turma
        Professor p = s.encontrarProfessor(cpfProf);

        // Criando nova turma
        Turma t = new Turma(nome, ano, sem, p);

        if (p != null) {
            // Lendo dados dos alunos e matriculando-os na turma.
            List<Aluno> alunos = new ArrayList<>();
            int qtdAlunos = Integer.parseInt(buff.readLine());
            int alunosProcessados = 0;

            while (alunosProcessados < qtdAlunos) {
                String matAluno = buff.readLine();
                Aluno a = s.encontrarAluno(matAluno);

                if (a != null) {
                    alunos.add(a);
                    System.out.println(alunos.size() + "/" + qtdAlunos + " alunos registrados com sucesso na turma '" + t + "'.");
                } else {
                    System.out.println("Erro: Aluno '" + matAluno + "' não encontrado.");
                }

                alunosProcessados += 1;
            }

            if (alunos.size() == qtdAlunos) {
                t.setAlunos(alunos);

                // Lendo dados das avaliações e registrando-os na turma.
                int qtdAvaliacoes = Integer.parseInt(buff.readLine());
                List<Avaliacao> avs = this.lerAvaliacoesArq(s, buff, alunos, qtdAvaliacoes);

                // Registrando as avaliações na turma somente se a quantidade de avaliações processadas for igual ao número de avaliações esperadas
                if (avs.size() == qtdAvaliacoes) {
                    t.setAvaliacoes(avs);

                    // Registrando a nova turma no sistema
                    s.novaTurma(t);
                } else {
                    System.out.println("Erro: Turma '" + t + "' não cadastrada, não encontramos todas as avaliações informadas.");
                }
            } else {
                System.out.println("Erro: Turma '" + t + "' não cadastrada, não encontramos todos os alunos informados.");
            }
        } else {
            System.out.println("Erro: Turma '" + t + "' não cadastrada, Professor não encontrado.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de uma nova academico.Turma e cadastra-a no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadTurma(Sistema s) {
        s.listarTurmas();

        String nome = this.lerLinha("Digite o nome da disciplina: ");
        int ano = this.lerInteiro("Digite o ano da disciplina: ");
        int sem = this.lerInteiro("Digite o semestre da disciplina: ");

        // Encontrando o professor da turma
        Professor p = this.lerProf(s);

        if (p != null) {
            // Criando nova turma
            Turma t = new Turma(nome, ano, sem, p);

            // Lendo dados dos alunos e matriculando-os na turma.
            List<Aluno> alunos = this.lerAlunos(s);
            t.setAlunos(alunos);

            // Lendo dados das avaliações e registrando-os na turma.
            int qtdAvaliacoes = this.lerInteiro("Digite a quantidade de avaliações na disciplina: ");
            List<Avaliacao> avs = this.lerAvaliacoes(s, alunos, qtdAvaliacoes);
            t.setAvaliacoes(avs);

            // Registrando a nova turma no sistema
            s.novaTurma(t);
        } else {
            System.out.println("Erro: Professor não encontrado.");
        }
    }
}
