package xadrez.jv.servidor;

import xadrez.jv.backend.Peca;

public class Protocol {
	protected String type;

	public Protocol() {}

	public Protocol(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
