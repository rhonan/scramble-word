package com.ufc.scramble_word.util;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static boolean executando = true;
    private static String mensagem;
    private static final int PORTA = 1234;

    public static void main(String[] args) {
        try {     
            ServerSocket server = new ServerSocket(PORTA);
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("----------- SERVIDOR CONECTADO "
                    + addr.getHostAddress() + " PORTA " + PORTA
                    + " -----------");
            System.out.println("Esperando Conex›es.");

            Socket socket = server.accept();

            System.out.println("Sevidor-> Conectou Ip "
                    + socket.getInetAddress().getHostAddress());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            try {
                while (executando) {
                    mensagem = in.readUTF();
                    System.out.println("Servidor-> Recebeu Mensagem: "
                            + mensagem);
                    out.writeUTF(mensagem);  // Escreve mensagem
                    out.flush();
                }
                System.out.println("Servidor-> Finalizado.");

                in.close();
                out.close();
                socket.close();
                server.close(); 
            } catch (Exception e) {
            	System.out.println("faiou");
                System.err.println("Servidor-> Erro: " + e.getMessage());
                executando = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
