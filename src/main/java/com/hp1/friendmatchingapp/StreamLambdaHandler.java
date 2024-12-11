package com.hp1.friendmatchingapp;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static{
        try{
            //실행속도 개선을 위해 async 방식으로 Init
            handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .asyncInit()
                    .springBootApplication(FriendmatchingappApplication.class)
                    .buildAndInitialize();
//            handler.getServletContext().setInitParameter("binaryContentTypes", "multipart/form-data,application/octet-stream,image/png,image/jpeg");
            handler.getContainerConfig().addBinaryContentTypes("multipart/form-data");

        } catch(ContainerInitializationException e){
            throw new RuntimeException("Spring Boot Application 실행 실패", e);
        }
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        handler.proxyStream(input, output, context);

    }
}
