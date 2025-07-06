package academico;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
            String filename = "dados.txt";
            FileReader file = new FileReader(filename);
            BufferedReader buff = new BufferedReader(file);

            String line = buff.readLine();
            System.out.println("Iniciando a leitura do arquivo '" + filename + "'...");

            while (line != null && !line.equals("FIM")) {
                switch (line) {
                    case "PROF" -> this.lerProfArq(s, buff);
                    case "ALU" -> this.lerAlunoArq(s, buff);
                    case "TUR" -> this.lerTurmaArq(s, buff);
                    default -> throw new LeituraTagArquivoException("Erro: Na leitura do arquivo de entrada, encontramos algumas informações inconsistentes e nem todas as informações foram registradas.");
                }
                line = buff.readLine();
            }

            buff.close();
        } catch (FileNotFoundException error) {
            System.out.println("Erro: Arquivo não encontrado");
        } catch (IOException error) {
            System.out.println("Erro: Tivemos um problema ao ler um dos blocos de dados do arquivo");
        } catch (NumberFormatException error) {
            System.out.println("Erro: Tivemos um problema ao converter uma das informações do arquivo para o valor numérico");
        } catch (LeituraTagArquivoException error) {
            System.out.println(error.getMessage());
        } finally {
            this.initialStatements();
            System.out.println("...Finalizamos a leitura do arquivo.\n");
        }
    }

    /***************************************************/

    private void initialStatements() {
        // Habilitando a leitura pelo teclado
        this.input = new Scanner(System.in);
    }

    /***************************************************/

    /**
     * Faz a leitura de uma linha inteira de um arquivo
     * @param buff: Um objeto da classe BufferedReader
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinhaArq(BufferedReader buff) throws IOException, LeituraTagArquivoException {
        String line = buff.readLine();

        if (line == null || line.equals("PROF") || line.equals("ALU") || line.equals("TUR") || line.equals("FIM")) {
            throw new LeituraTagArquivoException("Erro: Identificamos uma das Tags de controle num momento inesperado.");
        }

        return line;
    }

    /***************************************************/

    /**
     * Faz a leitura de um número inteiro de um arquivo
     * @param buff: Um objeto da classe BufferedReader
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiroArq(BufferedReader buff) throws IOException, NumberFormatException, LeituraTagArquivoException {
        String linha = this.lerLinhaArq(buff);
        return Integer.parseInt(linha);
    }

    /***************************************************/

    /**
     * Faz a leitura de um double de um arquivo
     * @param buff: Um objeto da classe BufferedReader
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDoubleArq(BufferedReader buff) throws IOException, NumberFormatException, LeituraTagArquivoException {
        String linha = this.lerLinhaArq(buff);
        return Double.parseDouble(linha);
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
    private Professor lerProf(Sistema s) throws PessoaNaoEncontradaException {
        String cpfProf = this.lerLinha("Digite o cpf do Professor: ");
        return s.encontrarProfessor(cpfProf);
    }

    /***************************************************/

    /**
     * Lê a MATRÍCULA de uma lista de academico.Aluno existentes e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @return A lista de alunos
     */
    private List<Aluno> lerAlunos(Sistema s) throws PessoaNaoEncontradaException {
        List<Aluno> alunos = new ArrayList<>();
        int qtdAlunos = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");
        int alunosRegistrados = 0;

        while (alunosRegistrados < qtdAlunos) {
            String matAluno = this.lerLinha("Digite a matrícula do aluno: ");
            Aluno a = s.encontrarAluno(matAluno);

            alunos.add(a);
            alunosRegistrados += 1;
            System.out.println(alunosRegistrados + "/" + qtdAlunos + " alunos registrados com sucesso.");
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
    private GrupoTrabalho lerGrupoTrabalho(Sistema s) throws PessoaNaoEncontradaException {
        GrupoTrabalho gt = new GrupoTrabalho();
        List<Aluno> alunos = new ArrayList<>();

        int alunosRegistrados = 0;
        int qtdAlunos = this.lerInteiro("Digite o número de alunos neste grupo: ");

        while (alunosRegistrados < qtdAlunos) {
            String matAluno = this.lerLinha("Digite a matrícula do aluno: ");
            Aluno a = s.encontrarAluno(matAluno);

            alunos.add(a);
            alunosRegistrados += 1;
            System.out.println(alunosRegistrados + "/" + qtdAlunos + " alunos inseridos no grupo com sucesso.");
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
    private Trabalho lerTrabalho(Sistema s, List<Aluno> alunos) throws PessoaNaoEncontradaException {
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
    private List<Avaliacao> lerAvaliacoes(Sistema s, List<Aluno> alunos, int nAvaliacoes) throws PessoaNaoEncontradaException {
        List<Avaliacao> avs = new ArrayList<>();
        int avaliacoesRegistradas = 0;

        while (avaliacoesRegistradas < nAvaliacoes) {
            int tipoAvaliacao = this.lerInteiro("Escolha um tipo de avaliação:\n1) Prova\n2)Trabalho\n");

            // Lendo os dados dos alunos de acordo com o tipo de avaliação digitada
            if (tipoAvaliacao == 1 || tipoAvaliacao == 2) {
                avaliacoesRegistradas += 1;

                if (tipoAvaliacao == 1) {
                    // Lendo dados dos alunos na Prova
                    Prova prv = this.lerProva(s, alunos);
                    avs.add(prv);
                } else {
                    // Lendo dados dos alunos no trabaho
                    Trabalho trb = this.lerTrabalho(s, alunos);
                    avs.add(trb);
                }
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
        try {
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
        } catch (InputMismatchException error) {
            System.out.println("Erro: Tivemos um problema ao capturar a opção inserida.");
            return -1;
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Professor de um arquivo e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     */
    public void lerProfArq(Sistema s, BufferedReader buff) throws IOException, LeituraTagArquivoException {
        String nome = this.lerLinhaArq(buff);
        String cpf = this.lerLinhaArq(buff);
        double salario = this.lerDoubleArq(buff);

        try {
            s.encontrarProfessor(cpf);
            System.out.println("Erro: CPF duplicado. academico.Professor não adicionado.");
        } catch (PessoaNaoEncontradaException error) {
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
            System.out.println(s.qtdProfs() + " professor(es) cadastrado(s) com sucesso!");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Professor e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadProf(Sistema s) {
        try {
            s.listarProfs();

            String nome = this.lerLinha("Digite o nome do professor: ");
            String cpf = this.lerLinha("Digite o cpf do professor: ");
            double salario = this.lerDouble("Digite o salário do professor: R$ ");

            try {
                s.encontrarProfessor(cpf);
                System.out.println("Erro: CPF duplicado. academico.Professor não adicionado.");
            } catch (PessoaNaoEncontradaException error) {
                Professor p = new Professor(nome, cpf, salario);
                s.novoProf(p);
            }
        } catch (NumberFormatException error) {
            System.out.println("Erro: O salário informado é inválido.");
        } catch (InputMismatchException error) {
            System.out.println("Erro: Uma das informações do Professor foi inserida de maneira incorreta.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Aluno de um arquivo e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     */
    public void lerAlunoArq(Sistema s, BufferedReader buff) throws IOException, LeituraTagArquivoException {
        String nome = this.lerLinhaArq(buff);
        String cpf = this.lerLinhaArq(buff);
        String mat = this.lerLinhaArq(buff);

        try {
            s.encontrarAluno(mat);
            System.out.println("Erro: Matrícula duplicado. academico.Aluno não adicionado.");
        } catch (PessoaNaoEncontradaException error) {
            Aluno a = new Aluno(nome, cpf, mat);
            s.novoAluno(a);
            System.out.println(s.qtdAlunos() + " aluno(s) cadastrado(s) com sucesso!");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de um novo academico.Aluno e cadastra-o no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadAluno(Sistema s) {
        s.listarAlunos();

        try {
            String nome = this.lerLinha("Digite o nome do aluno: ");
            String cpf = this.lerLinha("Digite o cpf do aluno: ");
            String mat = this.lerLinha("Digite a matrícula do aluno: ");

            try {
                s.encontrarAluno(mat);
                System.out.println("Erro: Matrícula duplicado. academico.Aluno não adicionado.");
            } catch (PessoaNaoEncontradaException error) {
                Aluno a = new Aluno(nome, cpf, mat);
                s.novoAluno(a);
            }
        } catch (InputMismatchException error) {
            System.out.println("Erro: Uma das informações do Aluno foi inserida de maneira incorreta.");
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
    private AlunoProva lerAlunoProvaArq(Sistema s, BufferedReader buff, Aluno aluno, int qtdQuestoes) throws IOException, NumberFormatException, LeituraTagArquivoException {
        AlunoProva ap = new AlunoProva(aluno, qtdQuestoes);
        double[] notas = new double[qtdQuestoes];

        for (int i = 0; i < qtdQuestoes; i++) {
            notas[i] = this.lerDoubleArq(buff);
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
    private Prova lerProvaArq(Sistema s, BufferedReader buff, List<Aluno> alunos, String nomeProva, Data dataProva, Double notaMaxProva, int qtdQuestoesProva) throws IOException, NumberFormatException, LeituraTagArquivoException {
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
     * @param t: Um objeto da classe academico.Turma
     * @param numeroGrupo: Um número da classe Integer
     * @param qtdIntgranTrab: Um número da classe Integer
     * @return Um objeto de academico.GrupoTrabalho
     */
    private GrupoTrabalho lerGrupoTrabalhoArq(Sistema s, BufferedReader buff, Turma t, int numeroGrupo, int qtdIntgranTrab) throws IOException, NumberFormatException, PessoaNaoEncontradaException, LeituraTagArquivoException, AlunoGrupoTrabalhoException {
        GrupoTrabalho gt = new GrupoTrabalho();
        List<Aluno> alunos = new ArrayList<>();

        int alunosProcessados = 0;
        int qtdAlunos = this.lerInteiroArq(buff);

        if (qtdAlunos > qtdIntgranTrab) {
            throw new AlunoGrupoTrabalhoException("Erro: A quantidade de alunos no trabalho excedeu o limite permitido.");
        }

        while (alunosProcessados < qtdAlunos) {
            String matAluno = this.lerLinhaArq(buff);
            Aluno a = s.encontrarAluno(matAluno);

            alunos.add(a);
            System.out.println(alunos.size() + "/" + qtdAlunos + " alunos inseridos no grupo " + numeroGrupo + " da turma '" + t + "' com sucesso.");

            alunosProcessados += 1;
        }

        double nota = this.lerDoubleArq(buff);
        gt.setAlunos(alunos);
        gt.setNota(nota);

        return gt;
    }

    /***************************************************/

    /**
     * Lê os dados do Trabalho de um arquivo e retorna-o.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param t: Um objeto da classe academico.Turma
     * @param alunos: Uma lista de objetos da classe academico.Aluno
     * @param nomeTrab: Nome do trabalho
     * @param dataTrab: Um objeto da classe academico.Data
     * @param notaMaxTrab: Um valor numérico para a nota máxima do trabalho
     * @param qtdIntgranTrab: Um inteiro definindo a quantidade máxima de alunos no trabalho
     * @return Um objeto de academico.Trabalho
     */
    private Trabalho lerTrabalhoArq(Sistema s, BufferedReader buff, Turma t, List<Aluno> alunos, String nomeTrab, Data dataTrab, Double notaMaxTrab, int qtdIntgranTrab) throws IOException, NumberFormatException, PessoaNaoEncontradaException, LeituraTagArquivoException, AlunoGrupoTrabalhoException {
        List<GrupoTrabalho> grupos = new ArrayList<>();
        Trabalho trab = new Trabalho(nomeTrab, dataTrab, notaMaxTrab, qtdIntgranTrab);

        int numGrupos = this.lerInteiroArq(buff);

        for (int i = 0; i < numGrupos; i++) {
            GrupoTrabalho grupo = this.lerGrupoTrabalhoArq(s, buff, t, i + 1, qtdIntgranTrab);
            grupos.add(grupo);
        }
        trab.setGrupos(grupos);

        return trab;
    }

    /***************************************************/

    /**
     * Lê a MATRÍCULA de uma lista de academico.Aluno existentes e retorna-os.
     * @param s: Um objeto da classe academico.Sistema
     * @param buff: Um objeto da classe BufferedReader
     * @param t: Um objeto da classe academico.Turma
     * @param alunos: Uma lista de objetos da classe academico.Aluno
     * @param nAvaliacoes: Um número da classe Integer
     * @return A lista de alunos
     */
    private List<Avaliacao> lerAvaliacoesArq(Sistema s, BufferedReader buff, Turma t, List<Aluno> alunos, int nAvaliacoes) throws IOException, NumberFormatException, PessoaNaoEncontradaException, LeituraTagArquivoException, AlunoGrupoTrabalhoException {
        List<Avaliacao> avs = new ArrayList<>();
        int avaliacoesProcessadas = 0;

        while (avaliacoesProcessadas < nAvaliacoes) {
            String tipoAvaliacao = this.lerLinhaArq(buff);

            // Lendo os dados dos alunos de acordo com o tipo de avaliação digitada
            if (tipoAvaliacao.equals("PROV") || tipoAvaliacao.equals("TRAB")) {
                String nomeTrab = this.lerLinhaArq(buff);
                int diaAv = this.lerInteiroArq(buff);
                int mesAv = this.lerInteiroArq(buff);
                int anoAv = this.lerInteiroArq(buff);
                double notaMaxAv = this.lerDoubleArq(buff);
                int qtdItemAv = this.lerInteiroArq(buff);

                Data dataAv = new Data(diaAv, mesAv, anoAv);
                Avaliacao av = tipoAvaliacao.equals("PROV")
                        ? this.lerProvaArq(s, buff, alunos, nomeTrab, dataAv, notaMaxAv, qtdItemAv)
                        : this.lerTrabalhoArq(s, buff, t, alunos, nomeTrab, dataAv, notaMaxAv, qtdItemAv);
                avs.add(av);
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
    public void lerTurmaArq(Sistema s, BufferedReader buff) throws IOException, LeituraTagArquivoException {
        try {
            String nome = this.lerLinhaArq(buff);
            int ano = this.lerInteiroArq(buff);
            int sem = this.lerInteiroArq(buff);
            String cpfProf = this.lerLinhaArq(buff);

            // Encontrando o professor da turma
            Professor p = s.encontrarProfessor(cpfProf);

            // Criando nova turma
            Turma t = new Turma(nome, ano, sem, p);
            System.out.println("Iniciando o cadastro da turma '" + t + "'");

            // Lendo dados dos alunos e matriculando-os na turma.
            List<Aluno> alunos = new ArrayList<>();
            int qtdAlunos = this.lerInteiroArq(buff);
            int alunosProcessados = 0;

            while (alunosProcessados < qtdAlunos) {
                String matAluno = this.lerLinhaArq(buff);
                Aluno a = s.encontrarAluno(matAluno);

                alunos.add(a);
                System.out.println(alunos.size() + "/" + qtdAlunos + " alunos registrados com sucesso na turma '" + t + "'.");
                alunosProcessados += 1;
            }

            if (alunos.size() == qtdAlunos) {
                t.setAlunos(alunos);

                // Lendo dados das avaliações e registrando-os na turma.
                int qtdAvaliacoes = this.lerInteiroArq(buff);
                List<Avaliacao> avs = this.lerAvaliacoesArq(s, buff, t, alunos, qtdAvaliacoes);

                // Registrando as avaliações na turma somente se a quantidade de avaliações processadas for igual ao número de avaliações esperadas
                if (avs.size() == qtdAvaliacoes) {
                    t.setAvaliacoes(avs);

                    // Registrando a nova turma no sistema
                    s.novaTurma(t);
                    System.out.println(s.qtdTurmas() + " turma(s) cadastrada(s) com sucesso!");
                } else {
                    System.out.println("Erro: Turma '" + t + "' não cadastrada, não encontramos todas as avaliações informadas.");
                }
            } else {
                System.out.println("Erro: Turma '" + t + "' não cadastrada, não encontramos todos os alunos informados.");
            }
        } catch (PessoaNaoEncontradaException | AlunoGrupoTrabalhoException error) {
            System.out.println(error.getMessage());
        } catch (NumberFormatException error) {
            System.out.println("Erro: Tivemos problema ao converter uma das informações para o valor numérico.");
        } catch (InputMismatchException error) {
            System.out.println("Erro: Uma das informações da Turma foi inserida de maneira incorreta.");
        }
    }

    /***************************************************/

    /**
     * Lê os dados de uma nova academico.Turma e cadastra-a no sistema.
     * @param s: Um objeto da classe academico.Sistema
     */
    public void cadTurma(Sistema s) {
        s.listarTurmas();

        try {
            String nome = this.lerLinha("Digite o nome da disciplina: ");
            int ano = this.lerInteiro("Digite o ano da disciplina: ");
            int sem = this.lerInteiro("Digite o semestre da disciplina: ");

            // Encontrando o professor da turma
            Professor p = this.lerProf(s);

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
        } catch (PessoaNaoEncontradaException error) {
            System.out.println(error.getMessage());
        } catch (NumberFormatException error) {
            System.out.println("Erro: Tivemos problema ao converter uma das informações para o valor numérico.");
        } catch (InputMismatchException error) {
            System.out.println("Erro: Uma das informações do Professor foi inserida de maneira incorreta.");
        }
    }
}
