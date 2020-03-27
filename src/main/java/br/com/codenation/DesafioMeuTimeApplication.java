package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	List<Time> times = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if(this.buscaTime(id) != null) {
			throw new IdentificadorUtilizadoException("Time já cadastrado");
		}else {
			times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
		}
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {

		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		List<Jogador> jogadores =  time.getJogadores();
		if( jogadores.contains(new Jogador(id)) ){  // jogadores.stream().filter(j -> j.getId() == id).findFirst().isPresent()
			throw new IdentificadorUtilizadoException("Jogador já cadastrado");
		}

		time.addJogador(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {

		Jogador jogador = this.buscaJogador(idJogador);

		if(jogador == null){
			throw new JogadorNaoEncontradoException("Jogador não cadastrado");
		}

		Time time = this.buscaTime(jogador.getIdTime());
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		time.setIdJogadorCapitao(jogador.getId());

	}


	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {

		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		if(time.getIdJogadorCapitao() == null){
			throw new CapitaoNaoInformadoException("Capitão não definido");
		}

		return time.getIdJogadorCapitao();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		Jogador jogador = this.buscaJogador(idJogador);
		if(jogador == null){
			throw new JogadorNaoEncontradoException("Jogador não cadastrado");
		}
		return jogador.getNome();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}
		return time.getNome();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {

		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}
		return time.getJogadores()
				.stream().sorted((j1,j2) -> j1.getId().compareTo(j2.getId()))
				.map(j -> j.getId())
				.collect(Collectors.toList());

	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {

		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}
		return 	time.getJogadores()
				.stream().max((j1, j2) -> j1.getNivelHabilidade().compareTo(j2.getNivelHabilidade()))
				.get()
				.getId();
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		return time.getJogadores()
				.stream()
				.sorted(Comparator.comparing(Jogador::getDataNascimento)
						.thenComparing(Jogador::getId))
				.findFirst()
				.get()
				.getId();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return times.stream()
				.sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
				.map(t -> t.getId())
				.collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Time time = this.buscaTime(idTime);
		if(time == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}
		return time.getJogadores()
				.stream()
				.max((Comparator.comparing(Jogador::getSalario)))
				.get()
				.getId();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		Jogador jogador = this.buscaJogador(idJogador);
		if(jogador == null){
			throw new JogadorNaoEncontradoException("Jogador não cadastrado");
		}
		return jogador.getSalario();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {

		if(times.isEmpty()){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		return 	times.stream()
				.map(t -> t.getJogadores())
				.flatMap( jogadors -> jogadors.stream())
				.sorted (Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
				.limit(top)
				.map(l -> l.getId()).collect(Collectors.toList());
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		Time timeCasa = this.buscaTime(timeDaCasa);
		Time timeFora = this.buscaTime(timeDeFora);

		if(timeCasa == null || timeFora == null){
			throw new TimeNaoEncontradoException("Time não cadastrado");
		}

		if(timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())){
			return timeFora.getCorUniformeSecundario();
		}

		return timeFora.getCorUniformePrincipal();

	}

	private Time buscaTime(Long id){
		for (Time time: times ) {
			if(time.equals(new Time(id) )){
				return time;
			}
		}
		return null;
	}

	private Jogador buscaJogador(Long id){
		for (Time time: times) {
			for (Jogador jogador: time.getJogadores()) {
				if( jogador.equals(new Jogador(id))){
					return jogador;
				}
			}
		}
		return null;
	}


}