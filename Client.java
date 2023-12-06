import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.security.*;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try {
            // Load the client's private key
            PrivateKey privateKey = KeyUtils.loadPrivateKey(Paths.get("C:\\Users\\koazg\\Desktop\\E-Java\\client_pkcs8_key.der"));

            // Connect to the server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + socket.getInetAddress());

            // Encrypt data with the server's public key
            PublicKey serverPublicKey = KeyUtils.loadPublicKey(Paths.get("C:\\Users\\koazg\\Desktop\\E-Java\\server_pkcs8_public_key.der"));


            String message = "Hello, Server!";
            byte[] data = message.getBytes();
            byte[] encryptedData = EncryptionUtils.encryptWithPublicKey(data, serverPublicKey);

            // Send encrypted data to the server
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(encryptedData);
            outputStream.flush();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
