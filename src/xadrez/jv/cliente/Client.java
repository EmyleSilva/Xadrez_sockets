package xadrez.jv.cliente;

import xadrez.jv.backend.Peca;
import xadrez.jv.protocolo.PecaAdapter;
import xadrez.jv.protocolo.RequestProtocol;
import xadrez.jv.protocolo.ResponseProtocol;
import xadrez.jv.protocolo.Tipo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Client {
    private final String host;
    private final int port;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Peca.class, new PecaAdapter()).create();

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public ResponseProtocol request(RequestProtocol request){
        try(Socket socket = new Socket(this.host, this.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String jsonReq = gson.toJson(request);
            // System.out.println("\n\n\n >>> Enviando requisição: " + jsonReq + "\n\n");
            out.println(jsonReq);

            String jsonResp = in.readLine();
            // System.out.println("\n\n\n >>> Resposta recebida: " + jsonResp + "\n\n");
            return gson.fromJson(jsonResp, ResponseProtocol.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
