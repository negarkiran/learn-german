package com.kiran.german.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import com.jayway.jsonpath.PathNotFoundException;
import com.kiran.german.api.responses.WordMetadata;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.Matcher;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StepDefs extends SpringIntegrationTest {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String responseBody;
    private int statusCode;

    @When("^I make a GET call to uri (.+)$")
    public void getCall(String url) throws IOException {
        HttpGet request = new HttpGet(String.format(BASE_URL+"%s", url));
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        setResponse(httpResponse);
    }

    @When("^I make a POST call to uri (.+) with body$")
    public void getCall(String url, String body) throws IOException {
        HttpPost request = new HttpPost(String.format(BASE_URL+"%s", url));
        StringEntity stringEntity = new StringEntity(body);
        request.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
        request.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        request.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(request);

        setResponse(httpResponse);
    }

    @Then("the response should be (\\d+)$")
    public void theResponseShouldBe(int responseCode) throws IOException {
        assertThat("status code is incorrect"
                        + objectMapper.readValue(responseBody, WordMetadata.class),
                statusCode, is(responseCode));
    }

    @Then("^the response matches$")
    public void assertThatResponseJsonMatches(List<Map<String, String>> assertions) {
        assertJson(responseBody, assertions);
    }

    private void setResponse(CloseableHttpResponse httpResponse) throws IOException {
        statusCode = httpResponse.getStatusLine().getStatusCode();
        responseBody = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    private void assertJson(String response, List<Map<String, String>> assertions) {

        JsonAsserter asserter = JsonAssert.with(response);

        assertions.forEach(a -> {
            String path = a.get("path");
            String matcher = a.get("matcher");
            if (matcher.equals("notDefined")) {
                try {
                    asserter.assertNotDefined(path);
                } catch (AssertionError error) {
                    throw new AssertionError(response, error);
                }
            } else {
                String expected = a.get("expected");
                try {
                    asserter.assertThat(path,
                            getMatcher(matcher, expected),
                            String.format(
                                    "At path [%s], for matcher [%s], we had expected [%s]. The complete json was [%s]",
                                    path,
                                    matcher,
                                    expected,
                                    response));
                } catch (PathNotFoundException ex) {
                    throw new AssertionError(response, ex);
                }
            }
        });
    }

    private Matcher<?> getMatcher(String matcher, String expected) {
        switch (matcher) {
            case "isInt":
                return is(Integer.valueOf(expected));
            case "notNull":
                return is(notNullValue());
            case "is":
                return is(expected);
            default:
                throw new RuntimeException("unable to infer match condition : " + matcher);
        }
    }
}
