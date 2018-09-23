import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Processador {
	List<Processo> listaDeProcessos = new ArrayList<Processo>();

	private List<Processo> ready = new LinkedList<>();
	private Queue<String> execucao_final = new LinkedList<String>();

	private int tempo_atual = 0;

	public Processador(List<Processo> lp) {
		this.listaDeProcessos = lp;
	}

	public void run() {
		// adiciona traco na execucao final quando nenhum processo esta pronto p
		// executar
		if (ready.isEmpty())
			execucao_final.add("-");

		executaProcesso();
		printRoundRobin();
	}

	private void executaProcesso() {
		// Chama método que executa processo
		System.out.println("Executa processo...");
		Processo p = listaDeProcessos.get(0);
		p.run(tempo_atual);
		System.out.println("Tempo execucao do processo:" + p.getTempo_execucao());
		System.out.println("Tempo rodando processo:" + p.getTempo_rodando());
		System.out.println("Tempo total rodando:" + p.getTotal_rodando());

		// adiciona resultado da execução na saída final do round robin
		execucao_final.add(p.toString());
	}

	private void printRoundRobin() {
		System.out.println("Final do round robin: ");
		for (String c : execucao_final)
			// printa ID do processo
			System.out.println(c);
	}

}
