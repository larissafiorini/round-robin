import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Processo implements Comparable<Object> {
	List<Integer> in_out;

	private int id;
	private int tempo_chegada;
	private int tempo_execucao;
	private int prioridade;

	private List<Processo> listaDeProcessos = new ArrayList<>();

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
	public int compareTo(Object arg) {
		int prioridade = ((Processo) arg).prioridade;
		System.out.println(this.prioridade - prioridade);
		return this.prioridade - prioridade;
	}

	@Override
	public String toString() {
		return id + "";
	}

	// Ordena processos em execução por prioridade
	public static void sortByPriority(List<Processo> executando) {
		Collections.sort(executando, new Comparator<Processo>() {
			@Override
			public int compare(Processo p1, Processo p2) {
				return p1.prioridade - p2.prioridade;
			}
		});
	}

}
