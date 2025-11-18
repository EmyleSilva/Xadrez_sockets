package xadrez.jv.servidor;

import xadrez.jv.backend.Peca;

public class RequestProtocol extends Protocol {
	// Para requisicao de explicacao
	private String peca;
	// Para requisica de movimentacao
	private int posicaoDestinoX;
	private int posicaoDestinoY;
	private Peca movPeca;

	// Construtor para requests de modo Explicavel
	public RequestProtocol(String type, String peca) {
		super(type);
		this.peca = peca;
	}

	// Construtor para requests de modo Simulação
	public RequestProtocol(String type, Peca peca, int posX, int posY) {
		super(type);
		this.movPeca = peca;
		this.posicaoDestinoX = posX;
		this.posicaoDestinoY = posY;
	}

	public int getPosicaoDestinoX() {
		return posicaoDestinoX;
	}

	public void setPosicaoDestinoX(int posicaoDestinoX) {
		this.posicaoDestinoX = posicaoDestinoX;
	}

	public int getPosicaoDestinoY() {
		return posicaoDestinoY;
	}

	public void setPosicaoDestinoY(int posicaoDestinoY) {
		this.posicaoDestinoY = posicaoDestinoY;
	}

	public String getPeca() {
		return peca;
	}

	public void setPeca(String peca) {
		this.peca = peca;
	}

	public Peca getMovPeca() {
		return movPeca;
	}

	public void setMovPeca(Peca movPeca) {
		this.movPeca = movPeca;
	}

}
