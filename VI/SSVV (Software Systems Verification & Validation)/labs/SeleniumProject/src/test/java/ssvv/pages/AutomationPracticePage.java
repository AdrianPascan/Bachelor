package ssvv.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@DefaultUrl("http://automationpractice.com/index.php")
public class AutomationPracticePage extends PageObject {

    @FindBy(id = "search_query_top")
    private WebElementFacade searchInput;

    @FindBy(name = "submit_search")
    private WebElementFacade searchButton;

    @FindBy(id = "newsletter-input")
    private WebElementFacade newsletterInput;

    @FindBy(name = "submitNewsletter")
    private WebElementFacade newsletterButton;

    public void enter_search_input(String input) {
        searchInput.type(input);
    }

    public void search() {
        searchButton.click();
    }

    public List<String> getProductNames() {
        return findAll(By.className("product-name")).stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());
    }

    public void enter_newsletter_email(String email) {
        newsletterInput.type(email);
    }

    public void subscribe_to_newsletter() {
        newsletterButton.click();
    }

    public String getAlertText() {
        try{
            WebElementFacade successAlert = find(By.cssSelector("p[class='alert alert-success']"));
            return successAlert.getText();
        } catch (NoSuchElementException ignored) {
        }

        try{
            WebElementFacade dangerAlert = find(By.cssSelector("p[class='alert alert-danger']"));
            return dangerAlert.getText();
        } catch (NoSuchElementException ignored) {
        }

        return "";
    }
}
