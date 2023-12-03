import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EnhancedClient {
    public static void main(String args[]) throws IOException {
        Socket clientSocket = new Socket("localhost", 1234);
        System.out.println("Terhubung ke server: " + clientSocket.getInetAddress());

        BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);

        String serverMessage = serverInput.readLine();
        System.out.println(serverMessage);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kirim pesan pertama: ");
        String firstMessage = scanner.nextLine();
        clientOutput.println(firstMessage);

        while (true) {
            String response = serverInput.readLine();
            System.out.println("Server: " + response);

            System.out.print("Permintaan perhitungan (misal: 2 + 3 atau exit): ");
            String clientMessage = scanner.nextLine();
            clientOutput.println(clientMessage);

            if (clientMessage.equalsIgnoreCase("exit")) {
                break;
            }
        }

        scanner.close();
        serverInput.close();
        clientSocket.close();
    }
}
