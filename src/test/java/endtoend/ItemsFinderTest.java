package endtoend;

import com.company.search.database.Item;
import com.company.search.service.SearchService;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URLEncoder;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-context.xml")
public class ItemsFinderTest extends AbstractITemsTest {

    @Autowired
    SearchService searchService;

    @Before
    public void createData() throws Exception {
        searchService.save(new Item("APPLE"));
        searchService.save(new Item("APPLE CAKE"));
        searchService.save(new Item("BANANA"));
        searchService.save(new Item("LEMON"));
        searchService.save(new Item("LEMONADE"));
        searchService.save(new Item("POTATO"));
        searchService.save(new Item("PASTA"));
        searchService.save(new Item("GRAPES"));
        searchService.save(new Item("WINE"));
    }

    @After
    public void deleteData() throws Exception{
        searchService.deleteTable();
    }


    @Test
    public void searchForAPPLE() throws Exception {
        SearchResponse searchResponse = hit("http://localhost:8080/findByName/APPLE");
        assertThat(searchResponse.getStatusCode(), Is.is(200));
        assertThat(searchResponse.content, Is.is("{\"items\":[{\"name\":\"APPLE\"},{\"name\":\"APPLE CAKE\"}]}"));
    }

    @Test
    public void searchForLempon() throws Exception {
        String response = hit("http://localhost:8080/findByName/LEMON").content;
        assertThat(response, Is.is("{\"items\":[{\"name\":\"LEMON\"},{\"name\":\"LEMONADE\"}]}"));
    }

    @Test
    public void searchForBANANA() throws Exception {
        String response = hit("http://localhost:8080/findByName/BANANA").content;
        assertThat(response, Is.is("{\"items\":[{\"name\":\"BANANA\"}]}"));
    }

    @Test
    public void searchForKINGS_CROSS() throws Exception {
        SearchResponse searchResponse = hit("http://localhost:8080/findByName/" + URLEncoder.encode("KINGS CROSS", "UTF-8"));
        assertThat(searchResponse.getStatusCode(), Is.is(200));
        assertThat(searchResponse.content, Is.is("{\"items\":[]}"));
    }


}
