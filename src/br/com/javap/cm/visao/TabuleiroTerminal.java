package br.com.javap.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.javap.cm.excecao.ExplosaoException;
import br.com.javap.cm.excecao.SairExcecao;
import br.com.javap.cm.modelo.Tabuleiro;

public class TabuleiroTerminal {
 private Tabuleiro tabuleiro;
 private Scanner input = new Scanner(System.in);

	public TabuleiroTerminal(Tabuleiro tabuleiro) {
	
		this.tabuleiro = tabuleiro;
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean next = true;
			while(next) {
				gameCycle();
				System.out.println("Outra partida? (S/n): ");
				String answer = input.nextLine();
				if("n".equalsIgnoreCase(answer)) {
					next = false;
				} else {
					tabuleiro.restart();
				}
			}
		} catch (SairExcecao e) {
			System.out.println("Tchau!!");
		} finally {
			input.close();
		}
		
	}

	private void gameCycle() {
		try {
			while(!tabuleiro.reachedGoal()) {
				System.out.println(tabuleiro);
				
				String typed = catchTypedValue("Digite (linha, coluna): ");
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
						.map(i -> Integer.parseInt(i.trim())).iterator();
				
				typed = catchTypedValue("Escolha 1 - Abrir ou 2 - (Des)Marcar: ");
				
				if("1".equals(typed)) {
					tabuleiro.open(xy.next(), xy.next());
				} else if("2".equals(typed)) {
					tabuleiro.updateCheck(xy.next(), xy.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}
		
	}
	
	private String catchTypedValue(String value) {
		System.out.print(value);
		String typedValue = input.nextLine();
		
		if("sair".equalsIgnoreCase(typedValue)) {
			throw new SairExcecao();
		}
		
		return typedValue;
	}
 
}
