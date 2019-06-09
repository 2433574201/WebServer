package com.school;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static final String WEB_APPS = System.getProperty("user.dir") + File.separator + "webapps";
    private static final int PORT = 9999;
    public static void main(String[] args) {
	// write your code here
        try {
            ServerSocket serverSocket = new ServerSocket(PORT,1, InetAddress.getByName("127.0.0.1"));
            while(true){
                Socket socket = serverSocket.accept();
                Runnable client = new Client(socket);
                Thread thread = new Thread(client);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private void run(){
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(PORT,1, InetAddress.getByName("127.0.0.1"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        while (true){
//            Socket socket;
//            InputStream inputStream;
//            OutputStream outputStream;
//            try {
//                 socket = serverSocket.accept();
//                 inputStream = socket.getInputStream();
//                 outputStream = socket.getOutputStream();
//
//                 Request request = new Request(inputStream);
//                 request.prase();
//                 System.out.println("requestResource:"+request.getUri());
//
//                 Respose respose = new Respose(outputStream);
//                 respose.setRequest(request);
//                 respose.handlerRequest();
//                 socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }

    static class Client implements Runnable{

        private Socket socket;
        public Client(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
                InputStream inputStream;
                OutputStream outputStream;
                try {
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();

                    Request request = new Request(inputStream);
                    request.prase();
                    System.out.println("requestResource:"+request.getUri());

                    Respose respose = new Respose(outputStream);
                    respose.setRequest(request);
                    respose.handlerRequest();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

}
