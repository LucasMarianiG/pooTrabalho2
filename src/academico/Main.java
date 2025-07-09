package academico;

/**
 * Classe principal
 * @author Hilario Seibel Junior
 */
public class Main {
    public static void main(String[] args) {
        Sistema s = new Sistema();
        Entrada io = new Entrada(s);

        int op = io.menu();

        while (op != 0) {
            if (op == 1) {
                io.cadProf(s);
            }
            if (op == 2) {
                io.cadAluno(s);
            }
            if (op == 3) {
                io.cadTurma(s);
            }
            if (op == 4) {
                s.listarTurmas();
            }

            op = io.menu();
        }
    }
}
