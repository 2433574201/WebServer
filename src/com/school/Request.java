package com.school;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream inputStream;

    public String getUri() {
        return uri;
    }

    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void prase(){
        StringBuffer request = new StringBuffer(2048);
        int length;
        byte[] buffer = new byte[2048];

        try {
            length = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            length = -1;
        }
        for(int i = 0;i<length;i++){
            request.append((char)buffer[i]);
        }
        System.out.println("HTTP-Request");
        System.out.print(request.toString());
        uri = parseUri(request.toString().replace('/','\\'));
    }

    public String parseUri(String request){
        int firstTab,secondTab;
        firstTab = request.indexOf(' ');
        if(firstTab!=-1){
            secondTab = request.indexOf(' ',firstTab+1);
            if(secondTab>firstTab){
                return request.substring(firstTab+1,secondTab);
            }
        }
        return "";
    }
}
