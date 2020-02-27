package com.epam.jdi.httptests.example;

import com.epam.http.requests.ServiceSettings;
import com.epam.http.requests.errorhandler.ErrorHandler;
import com.epam.http.response.ResponseStatusType;
import com.epam.http.response.RestResponse;
import com.epam.jdi.httptests.example.dto.Organization;
import com.epam.jdi.httptests.utils.TrelloDataGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Type;

import static com.epam.http.requests.ServiceInit.init;
import static com.epam.http.response.ResponseStatusType.CLIENT_ERROR;
import static io.restassured.RestAssured.given;

/**
 * Example of using common settings for all service endpoints
 */
public class ServiceSettingsTests {

    private ServiceSettings serviceSettings;
    private ErrorHandler errorHandler;
    private ObjectMapper objectMapper;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void initErrorHandler() {
        errorHandler = new ErrorHandler() {
            @Override
            public boolean hasError(RestResponse restResponse) {
                //only Client errors will be caught
                return ResponseStatusType.getStatusTypeFromCode(restResponse.getStatus().code) == CLIENT_ERROR;
            }

            @Override
            public void handleError(RestResponse restResponse) {
                Assert.fail("Exception is caught: " + restResponse.toString());
            }
        };
    }

    @BeforeClass
    public void initObjectMapper() {
        objectMapper = new Jackson2Mapper(new Jackson2ObjectMapperFactory() {
            @Override
            public com.fasterxml.jackson.databind.ObjectMapper create(Type type, String s) {
                com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper;
            }
        });
    }

    @BeforeClass
    public void initRequestSpecification() {
        requestSpecification = given().filter(new AllureRestAssured());
        requestSpecification.auth().basic("user", "password");
    }

    @BeforeClass(dependsOnMethods = {"initErrorHandler", "initObjectMapper", "initErrorHandler"})
    public void initService() {
        serviceSettings = ServiceSettings.builder()
                .errorHandler(errorHandler)
                .objectMapper(objectMapper)
                .requestSpecification(requestSpecification)
                .build();
        init(TrelloService.class, serviceSettings);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void assignBoardToOrganization() {

        //Create organization
        Organization organization = TrelloDataGenerator.generateOrganization();
        organization.setName(null);
        organization.setDisplayName(null);
        //this endpoint causes 400 error
        TrelloService.createOrganization(organization);
    }
}
