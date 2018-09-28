import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 		Define cada processo da lista e seus atributos. 
 *
 */
public class Processo implements Comparable<Object> {

	private int id;
	private int tempo_chegada;
	private int tempo_execucao;
	private int prioridade;

	// Execucao
	private int tempo_resposta = -1;
	private int tempo_rodando = 0;
	private int total_rodando = 0;
	
	// entrada/saida
	List<Integer> in_out;
	private int tempo_inicio_in_out;


	public int getTempo_inicio_in_out() {
		return tempo_inicio_in_out;
	}

	public int getTempo_resposta() {
		return tempo_resposta;
	}

	public int getTempo_rodando() {
		return tempo_rodando;
	}

	public int getTotal_rodando() {
		return total_rodando;
	}

	public Processo(int tempo_chegada, int tempo_execucao, int prioridade, int id, List<Integer> in_out) {
		this.id = id;
		this.tempo_chegada = tempo_chegada;
		this.tempo_execucao = tempo_execucao;
		this.prioridade = prioridade;
		this.in_out = in_out;

		if (in_out != null)
			Collections.sort(in_out);
	}

	public int getId() {
		return id;
	}

	public int getTempo_chegada() {
		return tempo_chegada;
	}

	public int getTempo_execucao() {
		return tempo_execucao;
	}

	public int getPrioridade() {
		return prioridade;
	}

	@Override
	public String toString() {
		return id + "";
	}

	public void executa(int tempo_atual) {
		if (tempo_resposta < 0)
			tempo_resposta = tempo_atual - tempo_chegada;

		tempo_execucao--;
		tempo_rodando++;
		total_rodando++;
	}

	@Override
	public int compareTo(Object arg) {
		int prioridade = ((Processo) arg).prioridade;
		System.out.println(this.prioridade - prioridade);
		return this.prioridade - prioridade;
	}

	// Ordena processos em execução por prioridade
	public static void ordenaPrioridades(List<Processo> executando) {
		Collections.sort(executando, new Comparator<Processo>() {
			@Override
			public int compare(Processo p1, Processo p2) {
				return p1.prioridade - p2.prioridade;
			}
		});
	}
	
	

}
