package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket SERVER_SOCKET = null;
    public final static int SOCKET_PORT = 8080;

    // Server socket constructor
    public Server(int port){
        try{
            this.SERVER_SOCKET = new ServerSocket(port);
            System.out.println("FILE SERVER listening... ");
        }catch (IOException e){ e.printStackTrace(); }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(SOCKET_PORT);
        while (true) {
            try {
                Socket CLIENT_SOCKET = SERVER_SOCKET.accept();
                Thread listenerThread = new Thread(new ClientConnectionHandler(CLIENT_SOCKET));
                listenerThread.start();

            } catch (IOException e) { e.printStackTrace(); }
        }

    }
}
