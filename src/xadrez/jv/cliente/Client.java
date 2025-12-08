package xadrez.jv.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import xadrez.jv.servidor.RequestProtocol;
import xadrez.jv.servidor.ResponseProtocol;
import xadrez.jv.servidor.enuns.Tipo;

public class Client {
    private final String host;
    private final int port;
    private final Gson gson = new Gson();

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
