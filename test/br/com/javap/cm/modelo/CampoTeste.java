package br.com.javap.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.javap.cm.excecao.ExplosaoException;

public class CampoTeste {
	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoDistancia1() {
		Campo vizinho = new Campo(3, 2);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(3, 4);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDistancia3() {
		Campo vizinho = new Campo(2, 3);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDistancia4() {
		Campo vizinho = new Campo(4, 3);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonal() {
		Campo vizinho = new Campo(2, 2);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertTrue(result);
	}
	@Test
	void testeNaoVizinhoDistancia() {
		Campo vizinho = new Campo(5, 5);
		
		boolean result = campo.addNeighbor(vizinho);
		
		assertFalse(result);
	}
	
	@Test
	void testeValorPadraoMarcado() {
		assertFalse(campo.isChecked());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.updateCheck();
		assertTrue(campo.isChecked());
	}
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.updateCheck();
		campo.updateCheck();
		assertFalse(campo.isChecked());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.open());
	}
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.updateCheck();
		assertFalse(campo.open());
	}
	@Test
	void testeAbrirMinadoMarcado() {
		campo.updateCheck();
		campo.bomb();
		assertFalse(campo.open());
	}
	@Test
	void testeAbrirComVizinho() {
		Campo vizinho1 = new Campo(1, 1);
		Campo vizinho2 = new Campo(2, 2);

		vizinho2.addNeighbor(vizinho1);
		campo.addNeighbor(vizinho2);
		
		campo.open();
		
		assertTrue(vizinho1.isOpened() && vizinho2.isOpened());
	}
	@Test
	void testeAbrirMinadoENaoMarcado() {
		campo.bomb();
		
		assertThrows(ExplosaoException.class, () -> {
			campo.open();
		});
	}
}
