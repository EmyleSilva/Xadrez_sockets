package xadrez.jv.servidor;

public class Cavalo extends Peca{
	
	public Cavalo(int posicaoOrigemX, int posicaoOrigemY, int cor)
	{
		super(posicaoOrigemX, posicaoOrigemY, cor);
	}
	
	@Override 
	public boolean validar(int posicaoX, int posicaoY)
	{
		if (super.validar(posicaoX, posicaoY)) {
			if (posicaoX == this.posicaoAtualX + 2 || posicaoX == this.posicaoAtualX - 2)
			{
				if (posicaoY == this.posicaoAtualY + 1 || posicaoY == posicaoAtualY - 1)
					return true;
			}else if(posicaoY == this.posicaoAtualY + 2 || posicaoY == posicaoAtualY - 2){
				if (posicaoX == this.posicaoAtualX + 1 || posicaoX == this.posicaoAtualX - 1)
					return true;
			}
		}
		return false;			
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
		String explicacao = "O cavalo no xadrez se move em formato de ‘L’.\n\n "
				+ "Ele avança duas casas em uma linha reta (para frente, para trás, "
				+ "para a esquerda ou para a direita) e depois uma casa para o lado. "
				+ "Ou seja, ele pode se mover, por exemplo, duas casas para a frente "
				+ "e uma para a esquerda ou para a direita. \n\nAlém disso, o cavalo "
				+ "é a única peça que pode pular sobre outras peças no tabuleiro."
				+ "\n\nPara ver um exemplo de movimentação do Cavalo, clique em 'OK'";
		return explicacao;
	}
}
