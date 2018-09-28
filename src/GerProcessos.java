import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
*	Classe que gerencia lista de processos que serão escalonados pelo processador.
*/

public class GerProcessos {
	public final int tempo_in_out = 4;

	private int quantum;
	private List<Processo> processos = new ArrayList<>();

	public GerProcessos(int fatia_tempo) {
		this.quantum = fatia_tempo;

	}

	public void add(Processo p) {
		this.processos.add(p);
	}

	public int getQuantum() {
		return quantum;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public static GerProcessos readFile(String file_name) throws IOException {
		GerProcessos processos;
		int id = 1;
		String linha;

		// lista de entrada e saída
		List<Integer> in_out = null;

		try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
			// Reseta lista de inOut
			in_out = null;

			int numero_processos = Integer.parseInt(br.readLine());
			int quantum = Integer.parseInt(br.readLine());

			processos = new GerProcessos(quantum);

			// le e armazena informacoes de cada processo
			for (int j = 0; j < numero_processos; j++) {
				in_out = null;

				// read the line
				linha = br.readLine();

				String[] linhas = linha.split(" ");

				// 1 = tempo de chegada
				int tempo_chegada = Integer.parseInt(linhas[0]);

				// 2 = tempo de execucao
				int tempo_execucao = Integer.parseInt(linhas[1]);

				// 3 = prioridade (1 ate 9 - prioridade 1 e a melhor)
				int prioridade = Integer.parseInt(linhas[2]);

				// 4 = tempos de acesso a operacoes de E/S. Opcional.
				if (linhas.length > 3) {
					in_out = new ArrayList<Integer>();

					for (int i = 3; i < linhas.length; i++)
						in_out.add(Integer.parseInt(linhas[i]));
				}

				processos.add(new Processo(tempo_chegada, tempo_execucao, prioridade, id++, in_out));
			}
		}

		return processos;
	}
}
