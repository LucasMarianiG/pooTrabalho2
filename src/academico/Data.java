package academico;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public int getDia() {
        return this.dia;
    }

    public int getMes() {
        return this.mes;
    }

    public int getAno() {
        return this.ano;
    }

    public boolean posterior(Data d2) {
        if (this.ano != d2.getAno()) {
            return this.ano > d2.getAno();
        }
        if (this.mes != d2.getMes()) {
            return this.mes > d2.getMes();
        }
        if (this.dia != d2.getDia()) {
            return this.dia > d2.getDia();
        }

        return false;
    }
}