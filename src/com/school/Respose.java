package com.school;

import java.io.*;
import java.util.Arrays;

public class Respose {
    private static final int BUFFER_SIZE =1024;
    Request request;
    OutputStream outputStream;
    DataOutputStream dataOutputStream;

    public Respose(OutputStream outputStream) {
        this.outputStream = outputStream;
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void handlerRequest(){
        FileInputStream fis = null;
        try {
            File file = new File(WebServer.WEB_APPS, request.getUri());

            System.out.println("HTTP-response");
            if (file.exists()) {
                String line="HTTP/1.1 200 OK \r\n";
                System.out.print("line="+line);
                //sout.writeChars(line);
                dataOutputStream.write(line.getBytes());//用字节传输，不能用字符，浏览器无法解析

                String header="Content-Type: text/html; charset=utf-8 \r\n"
                        +"Content-length: "+file.length()+" \r\n\r\n";
                System.out.print("header="+header);
                dataOutputStream.writeBytes(header);

                fis = new FileInputStream(file);
                byte[] bytes = new byte[BUFFER_SIZE];
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch!=-1) { //ch==-1表示读到末尾了
                    dataOutputStream.write(bytes, 0, ch); //写出到浏览器
                    System.out.print(Arrays.toString(bytes));
                    ch = fis.read(bytes, 0, BUFFER_SIZE);//再读会接上一次读的位置往下读，如果读到末尾就会返回-1，终止输出
                }
                dataOutputStream.close();
            } else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found \r\n"
                        + "Content-Type: text/html \r\n"
                        + "Content-Length: 23 \r\n"
                        + "\r\n"
                        + "<h1>File Not Found</h1>";
                System.out.println(errorMessage);
                outputStream.write(errorMessage.getBytes());
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis!=null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
