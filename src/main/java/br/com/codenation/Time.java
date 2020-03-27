package br.com.codenation;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.Jogador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Time {
    Long id;
    String nome;
    LocalDate dataCriacao;
    String corUniformePrincipal;
    String corUniformeSecundario;
    Long idJogadorCapitao;
    List<Jogador> jogadores = new ArrayList<>();


    public Time(Long id){
        this.setId(id);
    }

    public Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        this.setId(id);
        this.setNome(nome);
        this.setDataCriacao(dataCriacao);
        this.setCorUniformePrincipal(corUniformePrincipal);
        this.setCorUniformeSecundario(corUniformeSecundario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(validaId(id)){
            this.id = id;
        }else{
            new NullPointerException("Id é obrigatório");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(validaNome(nome)){
            this.nome = nome;
        }else{
            new NullPointerException("Nome é obrigatório");
        }
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        if(validaDataCriacao(dataCriacao)){
            this.dataCriacao = dataCriacao;
        }else{
            new NullPointerException("Data de Criação é obrigatório");
        }
    }

    public String getCorUniformePrincipal() {
        return corUniformePrincipal;
    }

    public void setCorUniformePrincipal(String corUniformePrincipal) {
        if(validaCorUniformePrincipal(corUniformePrincipal)){
            this.corUniformePrincipal = corUniformePrincipal;
        }else{
            new NullPointerException("Cor do Uniforme Principal é obrigatório");
        }
    }

    public String getCorUniformeSecundario() {
        return corUniformeSecundario;
    }

    public void setCorUniformeSecundario(String corUniformeSecundario) {
        if(validaCorUniformeSecundario(corUniformeSecundario)){
            this.corUniformeSecundario = corUniformeSecundario;
        }else{
            new NullPointerException("Cor do Uniforme Secundario é obrigatório");
        }
    }

    public Long getIdJogadorCapitao() {
        return idJogadorCapitao;
    }

    public void setIdJogadorCapitao(Long idJogadorCapitao) {
        this.idJogadorCapitao = idJogadorCapitao;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void addJogador(Jogador jogador) {
        if(this.jogadores.contains(jogador)){
            new IdentificadorUtilizadoException("Jogador já incluido no time");
        }
        this.jogadores.add(jogador);
    }

    private boolean validaId(Long id){
        return  id != null && id >= 0;
    }

    private boolean validaNome(String nome){
        return nome != null && !nome.isEmpty() && nome.length() > 3;
    }

    private boolean validaDataCriacao(LocalDate dataCriacao){
        return  dataCriacao != null && !dataCriacao.toString().isEmpty();
    }

    private boolean validaCorUniformePrincipal(String corUniformePrincipal){
        return corUniformePrincipal != null && !corUniformePrincipal.isEmpty() && corUniformePrincipal.length() > 3;
    }

    private boolean validaCorUniformeSecundario(String corUniformeSecundario){
        return corUniformeSecundario != null && !corUniformeSecundario.isEmpty() && corUniformeSecundario.length() > 3;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return getId().equals(time.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}