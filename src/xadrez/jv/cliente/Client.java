package xadrez.jv.cliente;

import java.net.InetSocketAddress;

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

    public ResponseProtocol request(RequestProtocol request) {
        // Cria o socket vazio
        Socket socket = new Socket();
        try {
            // Tenta conectar com um limite de 2000 milissegundos (2 segundos)
            socket.connect(new InetSocketAddress(this.host, this.port), 2000);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String jsonReq = gson.toJson(request);
            out.println(jsonReq);

            String jsonResp = in.readLine();
            
            socket.close();
            
            return gson.fromJson(jsonResp, ResponseProtocol.class);
            
        } catch (Exception e) {
            // Se der erro ou timeout, cai aqui
            // e.printStackTrace();
            try { socket.close(); } catch (Exception ex) {} // Garante fechamento
            return null;
        }
    }
}
