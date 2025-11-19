package xadrez.jv.servidor;

import xadrez.jv.backend.Tabuleiro;

public class RequestProtocol extends Protocol {
	/**
	 * Para requests de Explicação e Simulação
	 */
	private String peca;
	/**
	 * Para requests de Simulação (Posicionar no tabuleiro)
	 */
	private int cor;
	private int posX;
	private int posY;
	/**
	 * Para requests de Simulação (Movimentar peça)
	 */
	private int destinoX;
	private int destinoY;

	private Tabuleiro t;

	/**
	 * 
	 * GETTERS E SETTERS
	 * 
	 */

	public String getPeca() {
		return peca;
	}

	public void setPeca(String peca) {
		this.peca = peca;
	}

	public int getCor() {
		return cor;
	}

	public void setCor(int cor) {
		this.cor = cor;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getDestinoX() {
		return destinoX;
	}

	public void setDestinoX(int destinoX) {
		this.destinoX = destinoX;
	}

	public int getDestinoY() {
		return destinoY;
	}

	public void setDestinoY(int destinoY) {
		this.destinoY = destinoY;
	}

	public Tabuleiro getT() {
		return t;
	}

	public void setT(Tabuleiro t) {
		this.t = t;
	}

}
