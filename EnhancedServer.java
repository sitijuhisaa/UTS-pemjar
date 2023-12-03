import java.io.*;
import java.net.*;

public class EnhancedServer {
    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server berjalan. Menunggu koneksi...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client terhubung: " + clientSocket.getInetAddress());

        BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);

        serverOutput.println("Selamat datang di server. Silakan kirim pesan pertama.");

        String firstMessage = clientInput.readLine();
        System.out.println("Pesan pertama dari client: " + firstMessage);

        while (true) {
            serverOutput.println("Silakan kirim permintaan perhitungan (misal: 2 + 3 atau exit).");

            String clientMessage = clientInput.readLine();
            if (clientMessage.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println("Permintaan perhitungan dari client: " + clientMessage);

            try {
                double result = evaluateExpression(clientMessage);
                serverOutput.println("Hasil perhitungan: " + result);
            } catch (ArithmeticException e) {
                serverOutput.println("Error: " + e.getMessage());
            }
        }

        clientInput.close();
        serverOutput.close();
        serverSocket.close();
    }

    private static double evaluateExpression(String expression) {
        String[] parts = expression.split(" ");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[2]);
        String operator = parts[1];

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
