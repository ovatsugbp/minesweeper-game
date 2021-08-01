package br.com.javap.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.javap.cm.excecao.ExplosaoException;

public class Tabuleiro {
	private int rows;
	private int columns;
	private int mines;
	
	private final List<Campo> fields = new ArrayList<>();

	public Tabuleiro(int rows, int columns, int mines) {

		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void open(int row, int column) {
		try {
			fields.parallelStream()
			.filter(c -> c.getRow() == row && c.getColumn() == column)
			.findFirst()
			.ifPresent(c -> c.open());
		} catch (ExplosaoException e) {
			fields.forEach(c -> c.setOpen(true));
			throw e;
		}
	}
	
	public void updateCheck(int row, int column) {
		fields.parallelStream()
		.filter(c -> c.getRow() == row && c.getColumn() == column)
		.findFirst()
		.ifPresent(c -> c.updateCheck());;
	}
	

	private void gerarCampos() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				fields.add(new Campo(i, j));
			}
		}
		
	}
	private void associarVizinhos() {
		for (Campo f1 : fields) {
			for (Campo f2 : fields) {
				f1.addNeighbor(f2);
			}
		}	
			
	}
	private void sortearMinas() {
			long bombMines = 0;
			Predicate<Campo> bombed = b -> b.isBombed();
			do {
				int random = (int) (Math.random() * fields.size());
				fields.get(random).bomb();
				bombMines = fields.stream().filter(bombed).count();				
			} while (bombMines < mines);
			
	}
	
	public boolean reachedGoal() {
		return fields.stream().allMatch(c -> c.reachedGoal());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int i = 0; i < columns; i++) {
			sb.append(" ");
			sb.append(i);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		for (int r = 0; r < rows; r++) {
			sb.append(r);
			sb.append(" ");
			for (int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
