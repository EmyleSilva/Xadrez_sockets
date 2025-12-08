package xadrez.jv.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import xadrez.jv.backend.Bispo;
import xadrez.jv.backend.Cavalo;
import xadrez.jv.backend.Peca;
import xadrez.jv.backend.Torre;
import xadrez.jv.servidor.enuns.Status;
import xadrez.jv.servidor.enuns.Tipo;

public class Server {
	private static final int PORT = 8089;
	private static final Gson gson = new Gson();
	private ServerSocket server;

	public void start() throws IOException {
		this.server = new ServerSocket(PORT);
		System.out.println("\n\n Servidor ouvindo na porta " + PORT + "...");
		clientConnectionLoop();
	}

	private void clientConnectionLoop() throws IOException {
		while (true) {
			// Cria o Socket local para comunicação com o cliente
			Socket clientSocket = this.server.accept();
			System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				// Recebe a requisição no formato JSON
				String jsonReceveid = in.readLine();
				System.out.println("\n\nSolicitação recebida: " + jsonReceveid);

				// Desserializa JSON para objeto do tipo Mensagem
				RequestProtocol request = gson.fromJson(jsonReceveid, RequestProtocol.class);

				ResponseProtocol response = processRequest(request);

				// Serializa a resposta para JSON e envia para o cliente
				String jsonResponse = gson.toJson(response);
				System.out.println("\n\nResposta enviada: " + jsonResponse);
				out.println(jsonResponse);

				clientSocket.close();

			} catch (IOException e) {

				System.out.println("Erro ao iniciar conexão: " + e.getMessage());
			}
		}
	}

	/**
	 * Processa a requisição e fornece uma resposta (no formato objeto) com base no
	 * tipo e nos dados da requisição.
	 * 
	 * @param request A requição feita pelo cliente
	 * @return response A resposta no formato objeto
	 */
	public ResponseProtocol processRequest(RequestProtocol request) {
		ResponseProtocol response = new ResponseProtocol();

		Tipo tipo = request.getTipo();

		if (tipo == Tipo.GET_EXP) {
			response = processGetExp(request, response);
		} else if (tipo == Tipo.GET_PECA) {
			response = processGetPeca(request, response);
		} else if (tipo == Tipo.RMOV_PECA) {
			response = processRMovPeca(request, response);
		} else {
			// TODO: Tratar requisições incorretas?
		}

		return response;
	}

	public ResponseProtocol processGetExp(RequestProtocol request, ResponseProtocol response) {

		String peca = request.getPeca();
		Peca p = null;
		response.setTipo(Tipo.POST_EXP);
		response.setStatus(Status.OK);
		
		if (peca.equalsIgnoreCase("BISPO")) {
			Integer[] destinos = { 7, 7, 5, 5, 7, 3 };
			p = new Bispo(0, 0, 1);
			response.setDestinosSimulacao(destinos);
		} else if (peca.equalsIgnoreCase("CAVALO")) {
			Integer[] destinos = { 1, 2, 3, 1, 5, 2 };
			p = new Cavalo(0, 0, 1);
			response.setDestinosSimulacao(destinos);
		} else if (peca.equalsIgnoreCase("TORRE")) {
			Integer[] destinos = { 5, 0, 3, 0, 3, 3 };
			p = new Torre(0, 0, 1);
			response.setDestinosSimulacao(destinos);
		} else {
			response.setStatus(Status.PECA_NULL);
			response.setMensagem("Peça não encontrada");
		}

		if(p != null){
			response.setMensagem(p.explicacao());
		}
		return response;
	}

	public ResponseProtocol processGetPeca(RequestProtocol request, ResponseProtocol response) {

		Peca p;

		String peca = request.getPeca();

		response.setTipo(Tipo.POST_PECA);

		if (peca == "BISPO") {

			p = new Bispo(0, 0, request.getCor());

		} else if (peca == "CAVALO") {

			p = new Cavalo(0, 0, request.getCor());

		} else if (peca == "TORRE") {

			p = new Torre(0, 0, request.getCor());

		} else {
			response.setStatus(Status.PECA_NULL);
			return response;
		}

		p.setPosicaoOrigemX(request.getDestinoX());
		p.setPosicaoOrigemY(request.getDestinoY());

		request.getT().setPeca(p);

		response.setTabuleiro(request.getT());
		response.setStatus(Status.OK);

		return response;
	}

	public ResponseProtocol processRMovPeca(RequestProtocol request, ResponseProtocol response) {
		
		Peca p = request.getT().getMatrizPosicao(request.getPosX(), request.getPosY());
		response.setTipo(Tipo.MOV_PECA);
		
		if (p == null) {
			response.setStatus(Status.PECA_NULL);
		}else if (!request.getT().movimentarPeca(request.getDestinoX(), request.getDestinoY(), p.getId())) {
			response.setStatus(Status.INVALIDO);
		}else {
			response.setStatus(Status.OK);
		}
		response.setTabuleiro(request.getT());
		
		return response;
	}

	public static void main(String args[]) {

		try {

			Server server = new Server();
			server.start();

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
