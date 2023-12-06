import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Server {

    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            //PublicKey publicKey = KeyUtils.loadPublicKey(Paths.get("C:\Users\koazg\Desktop\E-Java\\server_pkcs8_key.der"));

            PrivateKey privateKey = KeyUtils.loadPrivateKey(Paths.get("C:\\Users\\koazg\\Desktop\\E-Java\\server_pkcs8_key.der"));
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Receive encrypted data from the client
                InputStream inputStream = clientSocket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                byte[] encryptedData = baos.toByteArray();

                // Decrypt the received data
                byte[] decryptedData = EncryptionUtils.decryptWithPrivateKey(encryptedData, privateKey);
                String decryptedMessage = new String(decryptedData);
                System.out.println("Received from client: " + decryptedMessage);

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
