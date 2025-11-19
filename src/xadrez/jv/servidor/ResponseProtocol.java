package xadrez.jv.servidor;

import xadrez.jv.backend.Peca;
import xadrez.jv.backend.Tabuleiro;

public class ResponseProtocol extends Protocol {
	/**
	 * Para respostas Explicavel
	 */
	private int[] destinosSimulacao;
	private String mensagem;
	private int pecaId;

	/**
	 * Para respostas simulação
	 */
	private Tabuleiro tabuleiro;

	public ResponseProtocol() {
	}
	
	/**
	 * 
	 * GETTERS E SETTERS
	 * 
	 */
	
	public int[] getDestinosSimulacao() {
		return destinosSimulacao;
	}

	public void setDestinosSimulacao(int[] destinosSimulacao) {
		this.destinosSimulacao = destinosSimulacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public int getPecaId() {
		return pecaId;
	}

	public void setPecaId(int pecaId) {
		this.pecaId = pecaId;
	}
}
