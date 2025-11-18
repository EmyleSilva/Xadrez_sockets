package xadrez.jv.backend;

public abstract class Peca implements IExplicavel{
	private static String[] colors = {"Branco", "Preto"};
	
	protected int posicaoOrigemX;
	protected int posicaoOrigemY;
	protected int posicaoAtualX;
	protected int posicaoAtualY;
	protected boolean estado;
	protected int id;
	protected String cor;
	
	public Peca(int posicaoOrigemX, int posicaoOrigemY, int cor)
	{
		this.posicaoOrigemX = this.posicaoAtualX = posicaoOrigemX;
		this.posicaoOrigemY = this.posicaoAtualY = posicaoOrigemY;
		this.cor = Peca.colors[cor];
		this.estado = true;
		this.id = -1;
	}

	public int getPosicaoOrigemX() {
		return this.posicaoOrigemX;
	}

	public int getPosicaoOrigemY() {
		return this.posicaoOrigemY;
	}
	
	public void setPosicaoOrigemX(int posicaoOrigemX) {
		this.posicaoOrigemX = this.posicaoAtualX = posicaoOrigemX;
	}

	public void setPosicaoOrigemY(int posicaoOrigemY) {
		this.posicaoOrigemY = this.posicaoAtualY = posicaoOrigemY;
	}

	public int getPosicaoAtualX() {
		return this.posicaoAtualX;
	}

	public int getPosicaoAtualY() {
		return this.posicaoAtualY;
	}

	public boolean isEstado() {
		return this.estado;
	}

	public int getId() {
		return this.id;
	}

	public String getCor() {
		return this.cor;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void mudarEstado()
	{
		this.estado = false;
	}
	
	public boolean validar(int posicaoX, int posicaoY)
	{
		if ((posicaoX > 7 || posicaoX < 0) || (posicaoY > 7 || posicaoY < 0))
			return false;
		return true;
	}
	
	public abstract boolean movimentar(int destinoX, int destinoY);
	
	public void desfazer_movimento()
	{
		this.posicaoAtualX = this.posicaoOrigemX;
		this.posicaoAtualY = this.posicaoOrigemY;
	}	
}
