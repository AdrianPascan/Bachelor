package examSelenium.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://en.wikipedia.org/wiki/Main_Page")
public class WikipediaPage extends PageObject {

    @FindBy(id = "searchInput")
    private WebElementFacade searchInput;

    @FindBy(id = "searchButton")
    private WebElementFacade searchButton;

    public void enter_search_input(String searchInput) {
        this.searchInput.type(searchInput);
    }

    public void press_search_button() {
        searchButton.click();
    }

    public void click_on_first_link() {
        find(By.cssSelector("a[data-serp-pos='0']")).click();
    }

}
