import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Processador {
	List<Processo> lista_processos = new ArrayList<Processo>();

	private List<Processo> ready = new LinkedList<>();

	private final int tempo_operacao_in_out = 4;
	private List<Processo> in_out = new ArrayList<>();

	private Queue<String> execucao_final = new LinkedList<String>();

	private int tempo_atual = 0;

	public Processador(List<Processo> lp) {
		this.lista_processos = lp;
	}

	public void run() {

		while (!lista_processos.isEmpty()) {

			// checa se chegaram novos processos
			processosProntos();

			// adiciona traco na execucao final quando nenhum processo esta pronto p
			// executar
			if (ready.isEmpty())
				execucao_final.add("-");

			executaProcesso();

			// incrementa tempo de execucao
			tempo_atual++;
		}
		printRoundRobin();
	}

	private void executaProcesso() {
		// Chama método que executa processo
		System.out.println("Executa processo...");
		Processo p = lista_processos.get(0);
		p.run(tempo_atual);
		System.out.println("Tempo execucao do processo:" + p.getTempo_execucao());
		System.out.println("Tempo rodando processo:" + p.getTempo_rodando());
		System.out.println("Tempo total rodando:" + p.getTotal_rodando());

		// adiciona resultado da execução na saída final do round robin
		execucao_final.add(p.toString());

		this.lista_processos.remove(p);

	}

	private void printRoundRobin() {
		System.out.println("Final do round robin: ");
		for (String c : execucao_final)
			// printa ID do processo
			System.out.println(c);
	}

	private void processosProntos() {
		List<Processo> chegando = new ArrayList<Processo>();
		List<Processo> retorno_in_out = new ArrayList<Processo>();

		// seleciona por chegada
		for (Processo p : this.lista_processos)
			if (p.getTempo_chegada() == this.tempo_atual)
				chegando.add(p);

		for (Processo p : chegando)
			lista_processos.remove(p);

		// seleciona por retorno de interrupcao de I/O
		for (Processo p : in_out)
			if (p.getTempo_inicio_in_out() + this.tempo_operacao_in_out == tempo_atual)
				retorno_in_out.add(p);

		for (Processo p : retorno_in_out)
			in_out.remove(p);

		// adiciona na lista de pronto para executar
		ready.addAll(chegando);
		ready.addAll(retorno_in_out);

		// Ordena processos pelas prioridades
		Processo.ordenaPrioridades(ready);
	}

}
