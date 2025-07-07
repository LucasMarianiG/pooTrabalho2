package academico;

public class Data implements Comparable<Data> {
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

    public int compareTo(Data d2) {
        // Comparando os extremos dos anos
        if (this.ano < d2.getAno()) return -1;
        if (this.ano > d2.getAno()) return 1;

        // Comparando os extremos dos meses
        if (this.mes < d2.getMes()) return -1;
        if (this.mes > d2.getMes()) return 1;

        // Comparando os extremos dos dias
        if (this.dia < d2.getDia()) return -1;
        if (this.dia > d2.getDia()) return 1;

        // Quando as datas são iguais
        return 0;
    }
}