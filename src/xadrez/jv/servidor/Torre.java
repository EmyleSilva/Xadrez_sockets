package xadrez.jv.servidor;

public class Torre extends Peca{
	
	public Torre(int posicaoX, int posicaoY, int cor)
	{
		super(posicaoX, posicaoY, cor);
	}
	
	@Override 
	public boolean validar(int posicaoX, int posicaoY)
	{
		if (super.validar(posicaoX, posicaoY))
		{
			if (posicaoX != this.posicaoAtualX && posicaoY != this.posicaoAtualY)
				return false;
			if ((posicaoX == this.posicaoAtualX || posicaoY == this.posicaoAtualY)
				&& posicaoX != this.posicaoAtualX || posicaoY != this.posicaoAtualY)
				return true;
		}
		return true;
	}
	@Override 
	public boolean movimentar(int destinoX, int destinoY)
	{
		if (validar(destinoX, destinoY))
		{
			this.posicaoOrigemX = this.posicaoAtualX;
			this.posicaoOrigemY = this.posicaoAtualY;
			this.posicaoAtualX = destinoX;
			this.posicaoAtualY = destinoY;
			return true;
		}
		return false;
	}
	
	public String explicacao()
	{
		String explicacao = "A torre é uma peça de xadrez que se move em linhas "
				+ "retas ao longo das colunas e filas do tabuleiro. "
				+ "\n\nPode avançar ou recuar qualquer número de casas desocupadas "
				+ "na direção horizontal ou vertical, o que a torna uma das peças "
				+ "mais poderosas em termos de alcance e controle de espaço. "
				+ "\n\nA torre captura peças adversárias movendo-se para a casa ocupada "
				+ "por essas peças, sem a capacidade de saltar sobre outras peças. "
				+ "\n\nATENÇÃO: Por motivos didáticos, a torre poderá saltar peças no modo simulação!"
				+ "\n\nPara ver um exemplo de movimentação da Torre, clique em 'OK'";
		return explicacao;
	}
}
