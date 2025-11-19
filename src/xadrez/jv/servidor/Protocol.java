package xadrez.jv.servidor;

import xadrez.jv.servidor.enuns.Status;
import xadrez.jv.servidor.enuns.Tipo;

public class Protocol {
	protected Tipo tipo;
	protected Status status;

	public Protocol() {
		
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
