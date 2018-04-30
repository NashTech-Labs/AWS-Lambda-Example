package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.models.Request;
import com.example.utils.constant.Constants;
import com.example.utils.fileprocessor.FileReader;
import com.example.models.Response;
import com.example.utils.config.ConfigReader;
import com.google.gson.Gson;

import java.nio.file.Paths;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ConfigReader configReader = ConfigReader.getConfigReader(Constants.LAMBDA);
    private final Gson gson = new Gson();

    //Lambda Function
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try {
            //fetching the value send in the request body
            String message = requestEvent.getBody();
            Request request = gson.fromJson(message, Request.class);

            //fetching the response data from the json file
            String responseFilePath = Paths.get(this.getClass()
                    .getResource("/")
                    .toURI())
                    .toString()
                    + configReader.getProperty(Constants.RESPONSE);
            String responseData = FileReader.getDataFromFile(responseFilePath);
            Response response = gson.fromJson(responseData, Response.class);
            response.setMessage("Got " + request.getMessage() + "!!");

            //setting up the response message
            responseEvent.setBody(gson .toJson(response));
            responseEvent.setStatusCode(200);

            return responseEvent;

        } catch(Exception ex) {
            responseEvent.setBody(Constants.INVALID_RESPONSE);
            responseEvent.setStatusCode(500);
            return responseEvent;
        }
    }
}
