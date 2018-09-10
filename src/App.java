import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	private static int quantum;

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Insira o nome do arquivo: ");
		String file_name = scan.nextLine();
		readFile(file_name);
		scan.close();
	}

	public static List<Processo> readFile(String file_name) throws IOException {
		List<Processo> listaDeProcessos = new ArrayList<>();
		int id = 1;
		String linha;
		List<Integer> in_out = null;

		try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
			// lista de tempos de acesso a operações de E/S
			in_out = null;
			// LINHA 1: número de processos
			int numeroDeProcessos = Integer.parseInt(br.readLine());
			// LINHA 2: quantum (fatia de tempo)
			quantum = Integer.parseInt(br.readLine());

			// lê informacoes de cada processo
			for (int j = 0; j < numeroDeProcessos; j++) {
				in_out = null;
				linha = br.readLine();
				String[] linhas = linha.split(" ");

				int tempo_chegada = Integer.parseInt(linhas[0]);
				int tempo_execucao = Integer.parseInt(linhas[1]);

				// (1 até 9 - prioridade 1 e o melhor)
				int prioridade = Integer.parseInt(linhas[2]);

				// tempos de acesso a operações de E/S.
				if (linhas.length > 3) {
					in_out = new ArrayList<Integer>();
					for (int i = 3; i < linhas.length; i++)
						in_out.add(Integer.parseInt(linhas[i]));
				}
				System.out.println("tempo chegada " + tempo_chegada);
				System.out.println("tempo execucao " + tempo_execucao);
				System.out.println("prioridade " + prioridade);
				System.out.println("in/out " + in_out);
				System.out.println("\n");
				listaDeProcessos.add(new Processo(id++, tempo_chegada, tempo_execucao, prioridade, in_out));
			}
		}

		return listaDeProcessos;
	}
}
