package xadrez.jv.protocolo;

import com.google.gson.annotations.Expose;
import xadrez.jv.backend.Tabuleiro;

public class RequestProtocol extends Protocol {
	/**
	 * Para requests de Explicação e Simulação
	 */
	@Expose(serialize = true, deserialize = true)
	private String peca;
	/**
	 * Para requests de Simulação (Posicionar no tabuleiro)
	 */
	@Expose(serialize = true, deserialize = true)
	private Integer cor; // ← Mude de int para Integer (aceita null)
	@Expose(serialize = true, deserialize = true)
	private Integer posX;
	@Expose(serialize = true, deserialize = true)
	private Integer posY;
	/**
	 * Para requests de Simulação (Movimentar peça)
	 */
	@Expose(serialize = true, deserialize = true)
	private Integer destinoX;
	@Expose(serialize = true, deserialize = true)
	private Integer destinoY;

	@Expose(serialize = true, deserialize = true)
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

	public Integer getCor() {
		return cor;
	}

	public void setCor(Integer cor) {
		this.cor = cor;
	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getDestinoX() {
		return destinoX;
	}

	public void setDestinoX(Integer destinoX) {
		this.destinoX = destinoX;
	}

	public Integer getDestinoY() {
		return destinoY;
	}

	public void setDestinoY(Integer destinoY) {
		this.destinoY = destinoY;
	}

	public Tabuleiro getT() {
		return t;
	}

	public void setT(Tabuleiro t) {
		this.t = t;
	}

}
