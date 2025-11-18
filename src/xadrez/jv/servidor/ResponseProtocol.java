package xadrez.jv.servidor;

import xadrez.jv.backend.Peca;

public class ResponseProtocol extends Protocol {
	private Peca p;
	private String statusCode;
	// Para respostas de explicacao
	private int[] destinosSimulacao;
	private String eMessage;
	// Para respostas de simulacao

	public ResponseProtocol() {
	}

	public Peca getP() {
		return p;
	}

	public void setP(Peca p) {
		this.p = p;
	}

	public int[] getDestinosSimulacao() {
		return destinosSimulacao;
	}

	public void setDestinosSimulacao(int[] destinosSimulacao) {
		this.destinosSimulacao = destinosSimulacao;
	}

	public String geteMessage() {
		return eMessage;
	}

	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
