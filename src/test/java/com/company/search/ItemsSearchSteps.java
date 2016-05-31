package com.company.search;

import com.company.search.database.Item;
import com.company.search.service.SearchService;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import server.SearchServer;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static org.junit.Assert.assertThat;

@ContextConfiguration("classpath:cucumber.xml")
public class ItemsSearchSteps {

    @Autowired
    SearchService searchService;

    CloseableHttpClient client = HttpClients.createDefault();
    SearchServer server = new SearchServer();
    private SearchResponse searchResponse;


    @Before
    public void beforeScenario() throws Exception {
        server.start();
    }

    @After
    public void afterScenario() throws Exception {
        server.stop();
    }

    @Given("the following search items exist:")
    public void setupitems(DataTable items) throws Exception {
        List<List<String>> data = items.raw();
        data.stream().forEach(row -> searchService.save(new Item(row.get(0))));

    }

    @Given("^the service is up an running$")
    public void the_service_is_up_an_running() throws Throwable {

    }

    @Given("^I am on a RESTFull client screen$")
    public void i_am_on_a_RESTFull_client_screen() throws Throwable {
    }


    @Given("^the service not is up an running$")
    public void the_service_not_is_up_an_running() throws Throwable {
        server.stop();
        server.startAnother();
    }

    @When("^I call the (.+) API$")
    public void i_call_the_searchAllThatDoesNotExist_API(final String searchApi) throws Throwable {
        searchResponse = hit("http://localhost:8080/" + searchApi);
    }

    @When("^I enter the search item name '(.+)'$")
    public void i_enter_the_search_item_name(final String searchText) throws Throwable {
        searchResponse = hit("http://localhost:8080/findByName/" + searchText);
    }

    @When("^the input '(.+)'$")
    public void the_input_(final String searchText) throws Throwable {
        searchResponse = hit("http://localhost:8080/findByName/" + URLEncoder.encode(searchText, "UTF-8"));
    }

    @Then("^the response code should be (\\d+)$")
    public void the_response_code_should_be(int arg1) throws Throwable {
        assertThat(searchResponse.getStatusCode(), Is.is(arg1));
    }

    @Then("^the search should return a JSON response as following:'(.+)'$")
    public void the_search_should_return_a_JSON_response_as_following(final String expected) throws Throwable {
        assertThat(searchResponse.getContent(), Is.is(expected));
    }

    @Then("^the search should return a JSON response containing the '(.+)' item$")
    public void the_search_should_return_a_JSON_response_containing_the_item(final String item) throws Throwable {
        assertThat(searchResponse.getContent().contains(item), Is.is(true));
    }


    @Then("^the search should return no characters and no items$")
    public void the_search_should_return_no_characters_and_no_items() throws Throwable {
        assertThat(searchResponse.getContent(), Is.is("{\"items\":[]}"));
    }

    @Then("^the response body should contain a JSON message telling that the service is UP$")
    public void the_response_body_should_contain_a_JSON_message_telling_that_the_service_is_UP() throws Throwable {
        assertThat(searchResponse.getContent(), Is.is("{\"message\":[\"service is UP\"]}"));
    }


    public SearchResponse hit(String uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        String content = EntityUtils.toString(response.getEntity());
        response.close();
        return new SearchResponse(statusLine.getStatusCode(), content);
    }

    public static class SearchResponse {
        int statusCode;
        String content;

        public SearchResponse(int statusCode, String content) {
            this.statusCode = statusCode;
            this.content = content;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getContent() {
            return content;
        }
    }
}