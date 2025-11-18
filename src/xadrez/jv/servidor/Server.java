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

public class Server {
	private static final int PORT = 8089;
	private static final Gson gson = new Gson();
	private ServerSocket server;
	
	public void start () throws IOException {
		this.server = new ServerSocket(PORT);
		System.out.println("Servidor ouvindo na porta " + PORT + "...");
		clientConnectionLoop();
	}
	
	private void clientConnectionLoop() throws IOException {
		while (true) {
			//Cria o Socket local para comunicação com o cliente
			Socket clientSocket = this.server.accept();
			System.out.println("Cliente " + clientSocket.getInetAddress().getHostAddress());
			
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				
				//Recebe a requisição no formato JSON
				String jsonReceveid = in.readLine();
				System.out.println("JSON recebido: " + jsonReceveid);
				
				//Desserializa JSON para objeto do tipo Mensagem
				RequestProtocol request = gson.fromJson(jsonReceveid, RequestProtocol.class);
				
				String type = request.getType();
				
//				if (type == "EXPLICAVEL") {
					ResponseProtocol response = processExplicavelRequest(request);					
//				}else if (type == "SIMULACAO") {
					// TODO
//					ResponseProtocol response = processSimulacaoRequest(request);
//				}
				
				//Serializa a resposta para JSON e envia para o cliente
				String jsonResponse = gson.toJson(response);
				out.println(jsonResponse);
				
				clientSocket.close();
				
			} catch (IOException e) {
				
				System.out.println("Erro ao iniciar conexão: " + e.getMessage());
			}
		}
	}
	
	/**
	 * TODO
	 * @param request
	 * @return 
	 */
//	public ResponseProtocol processSimulacaoRequest(RequestProtocol request) {
//		
//		ResponseProtocol response;
//		
//		return response;
//		
//	}
	
	public ResponseProtocol processExplicavelRequest(RequestProtocol request)
	{
		String peca = request.getPeca();
		ResponseProtocol response = new ResponseProtocol();
	
		response.setType("EXPLICAVEL_RESPONSE");
		Peca p;
		
		if (peca == "BISPO") {
			int[] destinos = {7, 7, 5, 5, 7, 3}; 
			p = new Bispo(0, 0, 1);
			
			response.setP(p);
			response.setDestinosSimulacao(destinos);
			response.seteMessage(p.explicacao());
		}else if (peca == "CAVALO") {
			int[] destinos = {1, 2, 3, 1, 5, 2};
			p = new Cavalo(0, 0, 1);
			
			response.setP(p);
			response.setDestinosSimulacao(destinos);
			response.seteMessage(p.explicacao());
		}else if (peca == "TORRE") {
			int[] destinos = {5, 0, 3, 0, 3, 3};
			p = new Torre(0, 0, 1);
			
			response.setP(p);
			response.setDestinosSimulacao(destinos);
			response.seteMessage(p.explicacao());
		}else {
			response.setStatusCode("101 ERRO - Peça indisponível");
			return response;
		}
		
		response.setStatusCode("200 OK");
		
		return response;
	}
		
	
	public static void main (String args[]) {
		
		try {
			
			Server server = new Server(); 
			server.start();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
}
