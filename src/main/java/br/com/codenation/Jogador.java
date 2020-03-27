package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Jogador {
    Long id;
    Long idTime;
    String nome;
    LocalDate dataNascimento;
    Integer nivelHabilidade;
    BigDecimal salario;


    public Jogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        this.setId(id);
        this.setIdTime(idTime);
        this.setNome(nome);
        this.setDataNascimento(dataNascimento);
        this.setNivelHabilidade(nivelHabilidade);
        this.setSalario(salario);
    }

    public Jogador(Long id){
        this.setId(id);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTime() {
        return idTime;
    }

    public void setIdTime(Long idTime) {
        this.idTime = idTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getNivelHabilidade() {
        return nivelHabilidade;
    }

    public void setNivelHabilidade(Integer nivelHabilidade) {
        if(validaNivelHabilidade(nivelHabilidade)){
            this.nivelHabilidade = nivelHabilidade;
        }else{
            throw new NullPointerException("Você deve informar valor difente de null e na faixa de 0 a 100");
        }

    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    private boolean validaId(Long id){
        return id != null && !id.toString().isEmpty();
    }

    private boolean validaIdTime(Long idTime){
        return idTime != null && !idTime.toString().isEmpty();
    }

    private boolean validaNome(String nome){
        return nome != null && !nome.isEmpty() && nome.length() > 3;
    }

    private boolean validaDataNascimento(LocalDate dataNascimento){
        return dataNascimento != null && !dataNascimento.toString().isEmpty();
    }

    private boolean validaNivelHabilidade(Integer nivelHabilidade){
        return nivelHabilidade != null &&  (nivelHabilidade.intValue() > 0  && nivelHabilidade.intValue() <= 100);
    }

    private boolean validaSalario(BigDecimal salario){
        return salario != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return id.equals(jogador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", idTime=" + idTime +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", nivelHabilidade=" + nivelHabilidade +
                ", salario=" + salario +
                '}';
    }
}