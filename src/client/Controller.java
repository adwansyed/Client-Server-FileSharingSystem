package client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;

public class Controller {

    public final static int SOCKET_PORT = 8080;   // port number:8080
    private static String SERVER = "localhost";   // localhost 127.0.0.1
    private static Socket socket;
    private File CLIENT_FILES = new File("ClientFiles/");
    private File SERVER_FILES = new File("ServerFiles/");

    @FXML
    private ListView<String> clientList, serverList;

    @FXML GridPane gridPane;

    // Initialize file lists
    public void initialize() throws IOException{
        clientList.setItems(FXCollections.observableArrayList(CLIENT_FILES.list()));
        serverList.setItems(FXCollections.observableArrayList(SERVER_FILES.list()));

        try {
            socket = new Socket(SERVER, SOCKET_PORT);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Restart GridPane with fx:id = gridPane
    // NOTE: Resolves uploading and downloading multiple files
    void restartClient(){
        Stage prevStage = (Stage) gridPane.getScene().getWindow();
        prevStage.hide();
        System.out.println("Loading CLIENT Files...");
        System.out.println("Loading SERVER Files...");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Exit program with accelerator CTRL+E
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void download(ActionEvent event) throws IOException{
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        String name = serverList.getSelectionModel().getSelectedItem();
        String filePath = SERVER_FILES + "/" + name;
        printStream.println("DOWNLOAD");
        transferFile(filePath);

        clientList.setItems(null);
        clientList.setItems(FXCollections.observableArrayList(CLIENT_FILES.list()));
        restartClient();

    }

    @FXML
    void upload(ActionEvent event) throws IOException{
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        String name = clientList.getSelectionModel().getSelectedItem();
        String filePath = CLIENT_FILES + "/" + name;
        printStream.println("UPLOAD");
        transferFile(filePath);

        serverList.setItems(null);
        serverList.setItems(FXCollections.observableArrayList(SERVER_FILES.list()));
        restartClient();
    }

    public static void transferFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] byteArray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(byteArray, 0, byteArray.length);

            OutputStream os = socket.getOutputStream();

            //Transfer file
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(byteArray.length);
            dos.write(byteArray, 0, byteArray.length);
            dos.flush();
            System.out.println("Transferred " + myFile.getName());
        } catch (Exception e) { e.printStackTrace(); }
    }
}

