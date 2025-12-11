package xadrez.jv.backend;

import java.util.ArrayList;

public class Tabuleiro {
	private Peca[][] matrizPosicao; 
	private ArrayList<Peca> pecas;
	private ArrayList<Peca> capturadasBrancas;
	private ArrayList<Peca> capturadasPretas;
	
	public Tabuleiro()
	{
		this.matrizPosicao = new Peca[8][8];
		this.pecas = new ArrayList<>();
		this.capturadasBrancas = new ArrayList<>();
		this.capturadasPretas = new ArrayList<>();
	}
	
	public Peca getMatrizPosicao(int posicaoX, int posicaoY) {
		return matrizPosicao[posicaoX][posicaoY];
	}
	
	public ArrayList<Peca> getPecas() {
		return pecas;
	}
	
	public ArrayList<Peca> getCapturadasBrancas() {
		return capturadasBrancas;
	}
	
	public ArrayList<Peca> getCapturadasPretas() {
		return capturadasPretas;
	}
	
	public void setPecas(ArrayList<Peca> pecas) {
		this.pecas = pecas;
	}
	
	public void setPeca(Peca peca)
	{
		int indice = this.pecas.size();
		peca.id = indice;
		this.pecas.add(peca);
		this.matrizPosicao[peca.posicaoAtualX][peca.posicaoAtualY] = peca;
	}
	
	public boolean validarMovimentacao(int destinoX, int destinoY, Peca peca)
	{
		if (this.matrizPosicao[destinoX][destinoY] == null)
			return true;
		
		Peca pecaDestino = this.matrizPosicao[destinoX][destinoY];
		if (pecaDestino.getCor().equals(peca.getCor()))
			return false;
		
		pecaDestino.mudarEstado();
		if (pecaDestino.getCor().equals("Branco")) this.capturadasBrancas.add(pecaDestino);
		else this.capturadasPretas.add(pecaDestino);
		return true;
	}
	
	public boolean movimentarPeca(int destinoX, int destinoY, int indice)
	{
		Peca peca = this.pecas.get(indice);
		if (peca.movimentar(destinoX, destinoY))
		{
			if (validarMovimentacao(destinoX, destinoY, peca))
			{
				this.matrizPosicao[destinoX][destinoY] = peca;
				this.matrizPosicao[peca.posicaoOrigemX][peca.posicaoOrigemY] = null;		
				return true;
			}
			//Caso o movimento seja válido, mas o tabuleiro na posição de destino não é livre
			this.pecas.get(indice).desfazer_movimento();
		}
		return false;
	}
	
	public boolean isEmpty(int x, int y)
	{
		return (this.matrizPosicao[x][y] == null) ? true : false;
	}
	
	public void limparTabuleiro()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (!isEmpty(i, j)) 
					this.matrizPosicao[i][j] = null;
			}
		}
	}
	
	public void limparCapturados()
	{
		this.capturadasBrancas.removeAll(capturadasBrancas);
		this.capturadasPretas.removeAll(capturadasPretas);
	}
}
