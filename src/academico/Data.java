package academico;

import java.util.Scanner;

public class Data {
    private Entrada io;
    private int dia;
    private int mes;
    private int ano;

    public Data() {
        Entrada io = new Entrada();

        this.dia = this.io.lerInteiro("Dia: ");
        this.mes = this.io.lerInteiro("Mês: ");
        this.ano = this.io.lerInteiro("Ano: ");
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

    public String formato() {
        return this.dia + "/" + this.mes + "/" + this.ano;
    }
}