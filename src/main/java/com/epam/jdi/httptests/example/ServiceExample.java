package com.epam.jdi.httptests.example;

import com.epam.http.requests.RestMethod;
import com.epam.http.annotations.Header;
import com.epam.http.annotations.Headers;
import com.epam.http.annotations.ServiceDomain;
import com.epam.http.annotations.POST;
import com.epam.http.annotations.PUT;
import com.epam.http.annotations.GET;
import com.epam.http.annotations.PATCH;
import com.epam.http.annotations.DELETE;
import com.epam.http.annotations.ContentType;
import com.epam.http.annotations.Cookies;
import com.epam.http.annotations.Cookie;
import com.epam.jdi.httptests.example.dto.Info;

import static io.restassured.http.ContentType.HTML;
import static io.restassured.http.ContentType.JSON;

/**
 * Example of simple service with several types of endpoints
 */
@ServiceDomain("https://httpbin.org/")
public class ServiceExample {
    @ContentType(JSON) @GET("/get")
    @Headers({
            @Header(name = "Name", value = "Roman"),
            @Header(name = "Id", value = "Test")
    })
    static RestMethod<Info> getInfo;

    public static Info getInfo() {
        return getInfo.callAsData(Info.class);
    }

    @Header(name = "Type", value = "Test")
    @POST("/post")
    RestMethod postMethod;

    @PUT("/put") RestMethod putMethod;
    @PATCH("/patch") RestMethod patch;
    @DELETE("/delete") RestMethod delete;
    @GET("/status/{status}") RestMethod status;

    @GET("/status/{status}?q={value}") RestMethod statusWithQuery;
    @PUT("/get?q=1") RestMethod getMethodWithQuery;

    @ContentType(HTML) @GET("/html")
    RestMethod getHTMLMethod;

    @Cookies({
            @Cookie(name = "session_id", value = "1234"),
            @Cookie(name = "hello", value = "world")
    })
    @GET("/cookies") RestMethod getCookies;

    @GET("/basic-auth/user/password") RestMethod getWithAuth;
}
