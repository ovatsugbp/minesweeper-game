package br.com.javap.cm;

import br.com.javap.cm.modelo.Tabuleiro;
import br.com.javap.cm.visao.TabuleiroTerminal;

public class Aplicacao {
	public static void main(String[] args) {
		Tabuleiro tab1 = new Tabuleiro(6, 6, 6);
		
		new TabuleiroTerminal(tab1);
	}
}
