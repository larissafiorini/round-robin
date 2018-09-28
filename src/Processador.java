import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Processador {
	List<Processo> lista_processos = new ArrayList<Processo>();

	private List<Processo> prontos = new LinkedList<>();
	private List<Processo> terminados = new LinkedList<>();
	private ArrayList<Processo> in_out = new ArrayList<>();

	// Grafico mostrando como os processos foram executados
	private ArrayList<String> saida_execucao = new ArrayList<String>();

	private GerProcessos processos;
	private Processo anterior;

	private int tempo_atual = 0;

	public Processador(GerProcessos lp) {
		this.processos = lp;
	}

	public void run() {

		while (!lista_processos.isEmpty()) {

			// checa se chegaram novos processos
			// processosProntos();

			// adiciona traco na execucao final quando nenhum processo esta pronto p
			// executar
			if (terminados.isEmpty())
				saida_execucao.add("-");

			executaProcesso();

			// incrementa tempo de execucao
			tempo_atual++;
		}
	}

	public void printResultados() {
		// Grafico mostrando como os processos foram executados
		for (String s : saida_execucao)
			System.out.print(s);

		System.out.println("******Algoritmo Round Robin******\n");
		System.out.println("Media de resposta: ");
		System.out.println("Media de espera: \n");
	}

	private void executaProcesso() {
		// Chama método que executa processo
		System.out.println("Executa processo...");
		Processo p = lista_processos.get(0);
		p.executa(tempo_atual);
		System.out.println("Tempo execucao do processo:" + p.getTempo_execucao());
		System.out.println("Tempo rodando processo:" + p.getTempo_rodando());
		System.out.println("Tempo total rodando:" + p.getTotal_rodando());

		// adiciona resultado da execução na saída final do round robin
		saida_execucao.add(p.toString());

		this.lista_processos.remove(p);

	}

	// private void processosProntos() {
	// List<Processo> chegando = new ArrayList<Processo>();
	// List<Processo> retorno_in_out = new ArrayList<Processo>();
	//
	// // seleciona por chegada
	// for (Processo p : this.lista_processos)
	// if (p.getTempo_chegada() == this.tempo_atual)
	// chegando.add(p);
	//
	// for (Processo p : chegando)
	// lista_processos.remove(p);
	//
	// // seleciona por retorno de interrupcao de I/O
	// for (Processo p : in_out)
	// if (p.getTempo_inicio_in_out() + this.tempo_operacao_in_out == tempo_atual)
	// retorno_in_out.add(p);
	//
	// for (Processo p : retorno_in_out)
	// in_out.remove(p);
	//
	// // adiciona na lista de pronto para executar
	// terminados.addAll(chegando);
	// terminados.addAll(retorno_in_out);
	//
	// // Ordena processos pelas prioridades
	// Processo.ordenaPrioridades(terminados);
	// }

}
