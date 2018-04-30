package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.example.models.Response;
import com.example.utils.config.ConfigReader;
import com.example.utils.constant.Constants;
import com.example.utils.fileprocessor.FileReader;
import com.google.gson.Gson;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LambdaHandlerTest {

    private final ConfigReader configReader = ConfigReader.getConfigReader(Constants.LAMBDA);
    Gson gson = new Gson();

    @Test
    public void handleRequest() throws Exception {

        String requestData = "{\n" +
                "  \"message\" : \"Hello Coders\"\n" +
                "}";
        LambdaHandler lambdaHandler = new LambdaHandler();
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setBody(requestData);
        Context context = null;
        assertEquals(200, (int)lambdaHandler.handleRequest(requestEvent, context).getStatusCode());
    }

    @Test
    public void handleInvalidCase() throws Exception {

        String requestData = "message";
        LambdaHandler lambdaHandler = new LambdaHandler();
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setBody(requestData);
        Context context = null;
        assertEquals(500, (int)lambdaHandler.handleRequest(requestEvent, context).getStatusCode());
    }

}