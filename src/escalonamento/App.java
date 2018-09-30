package escalonamento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * 
 * 
 *
 */


public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("****Algoritmo Round Robin****\n");
		
		Scanner scan = new Scanner(System.in);
		// System.out.println("Insira o nome do arquivo: ");
		// String file_name = scan.nextLine();
		String file_name = "input.txt";
		scan.close();
		
		// Classe que controla lista de processos
		GerProcessos processos = GerProcessos.readFile(file_name);

		// Instancia processador com lista de processos do arquivo
		Processador processador = new Processador(processos);

		// Executa round robin
		processador.executaEscalonador();

		// Imprime resultados
		processador.printResultados();
		
		System.out.println("***FIM***");
	}
}
