package xadrez.jv.servidor;

public class Bispo extends Peca{
	
	public Bispo(int posicaoOrigemX, int posicaoOrigemY, int cor)
	{
		super(posicaoOrigemX, posicaoOrigemY, cor);
	}
	
	@Override
	public boolean validar(int posicaoX, int posicaoY)
	{
		if (posicaoX == this.posicaoAtualX || posicaoY == this.posicaoAtualY)
			return false;
		if (Math.abs(posicaoY - this.posicaoAtualY) != Math.abs(posicaoX - this.posicaoAtualX))
			return false;
		return true;
	}
	
	@Override 
	public boolean movimentar(int destinoX, int destinoY)
	{
		if (super.validar(destinoX, destinoY))
		{
			if (validar(destinoX, destinoY))
			{
				this.posicaoOrigemX = this.posicaoAtualX;
				this.posicaoOrigemY = this.posicaoAtualY;
				this.posicaoAtualX = destinoX;
				this.posicaoAtualY = destinoY;
				return true;
			}
		}
		return false;
	}
	
	public String explicacao()
	{
		String explicacao = "O bispo move-se exclusivamente ao longo das diagonais, "
				+ "podendo avançar para qualquer casa desocupada em qualquer direção "
				+ "diagonal (nordeste, noroeste, sudeste e sudoeste). "
				+ "\n\nATENÇÃO: No jogo convencional, o bispo não pode 'pular' por cima de uma peça no tabuleiro."
				+ "No modo simulação, ele poderá fazer esse 'pulo' apenas por fins didáticos!"
				+ "\n\nPara ver um exemplo de movimentação do Bispo, clique em 'OK'";
		return explicacao;
	}
}
