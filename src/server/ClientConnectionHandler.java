package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientConnectionHandler implements Runnable {

    private File SERVER_FILES = new File("ServerFiles/");
    private File CLIENT_FILES = new File("ClientFiles/");
    private SocketAddress SOCKET_ADDRESS;
    private Socket CLIENT_SOCKET;

    private BufferedReader br = null;
    private ObjectInputStream ois;
    private static PrintStream os;

    public final static int FILE_SIZE = 7000000; // file size hard coded. Bigger than file to be downloaded

    public ClientConnectionHandler(Socket socket) {
        this.CLIENT_SOCKET = socket;
        SOCKET_ADDRESS = CLIENT_SOCKET.getLocalSocketAddress();
    }

    @Override
    public void run() {

        try {
            receiveFile();
            br = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            ois = new ObjectInputStream(CLIENT_SOCKET.getInputStream());

            String line;
            while((line = br.readLine()) != null) {
                if (line.equals("UPLOAD")) { transferTo(SERVER_FILES); }
                if (line.equals("DOWNLOAD")) { transferTo(CLIENT_FILES); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void receiveFile() throws IOException{
        os = new PrintStream( CLIENT_SOCKET.getOutputStream());
        os.println(this.SERVER_FILES.getName());
    }

    public void transferTo(File FILE_DIR) {
        try {
            int bytesRead;

            DataInputStream dis = new DataInputStream(CLIENT_SOCKET.getInputStream());
            String fileName = dis.readUTF();
            OutputStream os = new FileOutputStream((FILE_DIR + "/" + fileName));

            long size = dis.readLong();
            byte[] byteArray = new byte[FILE_SIZE];
            while (size > 0 && (bytesRead = dis.read(byteArray, 0, (int) Math.min(byteArray.length, size))) > -1) {
                os.write(byteArray, 0, bytesRead);
                size -= bytesRead;
            }
            os.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

}
