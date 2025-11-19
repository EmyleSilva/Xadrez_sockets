package xadrez.jv.servidor.enuns;

public enum Status {
	OK("Ação realizada"), 
	INVALIDO("Movimento Inválido"), 
	PECA_NULL("Peça/Posição Nula");

	private final String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
