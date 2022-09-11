package ssvv.features.ap.search;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import ssvv.steps.serenity.APUserSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/APSearch.csv")
public class APSearchParametrizedStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    public String searchInput;
    public String productName;

    @Steps
    public APUserSteps user;

    @Issue("#AP-SEARCH")
    @Test
    public void searching_by_input_should_display_the_corresponding_products() {
        user.is_the_home_page();
        user.searches_for(searchInput);
        user.should_see_product(productName);
    }
}