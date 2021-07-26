package simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class SimulationPOJO {

    @JsonIgnore
    private int id;
    private String cpf;
    private String nome;
    private String email;
    private double valor;
    private int parcelas;
    private boolean seguro;

    public SimulationPOJO(String cpf, String nome, String email, double valor, int parcelas, boolean seguro) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.valor = valor;
        this.parcelas = parcelas;
        this.seguro = seguro;
    }

    public SimulationPOJO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public boolean isSeguro() {
        return seguro;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationPOJO that = (SimulationPOJO) o;
        return Double.compare(that.valor, valor) == 0 &&
                parcelas == that.parcelas &&
                seguro == that.seguro &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, email, valor, parcelas, seguro);
    }

    @Override
    public String toString() {
        return "SimulationPOJO{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", valor=" + valor +
                ", parcelas=" + parcelas +
                ", seguro=" + seguro +
                '}';
    }
}
