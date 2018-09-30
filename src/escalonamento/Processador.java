package escalonamento;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * Verificacoes:
 * 1- Se ha processos executando
 * 2- Se fatia de tempo(quantum) foi atingida
 * 3- Se deve interromper execucao do processo atual
 * 4- Se deve executar operacao de entrada/saida
 * 5- Se processo atual terminou sua execucao
 * 6- Se nenhuma das anteriores e valida, entao executa processo atual
 * 
 */
public class Processador {
	private GerProcessos processos;

	// processos prontos para executar
	private List<Processo> prontos = new LinkedList<>();
	// processos que terminaram sua execucao
	private List<Processo> finalizados = new LinkedList<>();
	// processos realizando operacoes de entrada e saida
	private ArrayList<Processo> inOut = new ArrayList<>();

	// Grafico mostrando como os processos foram executados
	private ArrayList<String> execucao_final = new ArrayList<String>();

	private int tempo_atual = 0;
	private Processo atual_anterior;

	public Processador(GerProcessos processos) {
		this.processos = processos;
	}

	public void executaEscalonador() {
		boolean executou_atual = false;

		while (!vazio()) {
			executou_atual = false;

			// Procura processos prontos para executar
			encontraProntos();

			if (prontos.isEmpty())
				execucao_final.add("-");

			else if (terminouQuantum()) {
				prontos.get(0).setTempo_rodando(0);
				trocaProcesso();
			}

			else if (checkInterrupcao())
				trocaProcesso();

			else if (deveIO())
				executaInOut();

			else if (terminouAtual())
				terminaProcesso();

			else {
				// troca o contexto para novo processo se necessário
				if (execucao_final.get(execucao_final.size() - 1).contains("-")) {
					execucao_final.add("C");
					tempo_atual++;
				}
				executaProcesso();
				executou_atual = true;
			}
			addTempoEspera(!executou_atual);
			tempo_atual++;
		}
	}

	private void encontraProntos() {
		List<Processo> p, returnInOut;

		// entrando no tempo atual
		p = processos.getProcessosTempo(tempo_atual);

		// por interrupcao IO
		returnInOut = terminouInOut();

		prontos.addAll(p);
		prontos.addAll(returnInOut);

		if (!prontos.isEmpty()) {
			atual_anterior = prontos.get(0);
			Processo.ordenaPrioridades(prontos);
		}
	}

	private boolean terminouQuantum() {
		if (!prontos.isEmpty())
			if (prontos.get(0).getTempo_rodando() >= processos.getQuantum())
				return true;
		
		return false;
	}
	
	private boolean checkInterrupcao() {
		if (prontos.get(0) != atual_anterior)
			return true;
		else
			return false;
	}
	
	private boolean vazio() {
		return processos.listaVazia() && prontos.isEmpty() && inOut.isEmpty();
	}

	private void executaProcesso() {
		prontos.get(0).executa(tempo_atual);
		// add na saida
		execucao_final.add(prontos.get(0).toString());
	}


	private boolean deveIO() {
		return !prontos.isEmpty() && prontos.get(0).verificaInOut();
	}

	private void executaInOut() {
		prontos.get(0).setTempo_inicio_in_out(tempo_atual);
		inOut.add(prontos.get(0));
		prontos.remove(0);
		execucao_final.add("C");
	}

	private List<Processo> terminouInOut() {
		List<Processo> lista = new ArrayList<>();

		for (Processo p : inOut)
			if (p.getTempo_inicio_in_out() + processos.tempo_in_out == tempo_atual)
				lista.add(p);

		for (Processo p : lista)
			inOut.remove(p);

		return lista;
	}

	private boolean terminouAtual() {
		return !prontos.isEmpty() && prontos.get(0).terminou();
	}

	private void terminaProcesso() {
		finalizados.add(prontos.get(0));
		prontos.remove(0);
		// muda contexto
		if (!vazio())
			execucao_final.add("C");
	}

	private void addTempoEspera(boolean naoExecutouAtual) {
		Processo atual = null;

		if (!prontos.isEmpty())
			atual = prontos.get(0);

		for (Processo p : prontos)
			if (p != atual || naoExecutouAtual == true)
				p.aumentaTempoEspera();

		// Aumenta tempo de espera se processo esta esperando operacao IO
		for (Processo p : inOut)
			p.aumentaTempoEspera();
	}

	

	private void trocaProcesso() {
		int prioridades = 0;

		if (!prontos.isEmpty()) {
			for (Processo p : prontos)
				if (prontos.get(0).getPrioridade() >= p.getPrioridade())
					prioridades++;

			// checa se ha mais de um processo com mesma prioridade
			if (prioridades > 0) {
				Processo atual = prontos.get(0);

				prontos.remove(atual);
				prontos.add(prioridades - 1, atual);
			}
		}
		// troca contexto
		execucao_final.add("C");
	}
	

	public void printResultados() {
		// Grafico mostrando como os processos foram executados
		System.out.println("Grafico de execucao final dos processos: \n");
		for (String c : execucao_final)
			System.out.print(c);

		int tempo_espera = 0;
		int tempo_resposta = 0;

		for (Processo p : finalizados) {
			tempo_espera += p.getTempoEspera();
			tempo_resposta += p.getTempo_resposta();
		}

		double media_espera = (double) tempo_espera / finalizados.size();
		System.out.println("\n\nMedia de espera: " + media_espera);

		double media_resposta = (double) tempo_resposta / finalizados.size();
		System.out.println("Media de resposta: " + media_resposta + "\n");
	}


}
