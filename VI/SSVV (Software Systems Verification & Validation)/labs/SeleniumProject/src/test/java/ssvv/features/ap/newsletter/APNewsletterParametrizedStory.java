package ssvv.features.ap.newsletter;

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
@UseTestDataFrom("src/test/resources/APNewsletter.csv")
public class APNewsletterParametrizedStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    public String email;

    @Steps
    public APUserSteps user;

    @Issue("#AP-NEWSLETTER")
    @Test
    public void subscribing_to_newsletter_should_display_success_alert() {
        user.is_the_home_page();
        user.subscribes_to_newsletter(email);
        user.should_see_success_alert();
    }
}