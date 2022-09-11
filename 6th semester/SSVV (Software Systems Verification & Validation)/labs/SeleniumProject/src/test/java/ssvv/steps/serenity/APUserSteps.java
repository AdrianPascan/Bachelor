package ssvv.steps.serenity;

import net.thucydides.core.annotations.Step;
import ssvv.pages.AutomationPracticePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class APUserSteps {

    AutomationPracticePage automationPracticePage;

    @Step
    public void is_the_home_page() {
        automationPracticePage.open();
    }

    @Step
    public void enters_search_input(String searchInput) {
        automationPracticePage.enter_search_input(searchInput);
    }

    @Step
    public void starts_search() {
        automationPracticePage.search();
    }

    @Step
    public void should_see_product(String productName) {
        assertThat(automationPracticePage.getProductNames(), hasItem(containsString(productName)));
    }

    @Step
    public void searches_for(String input) {
        enters_search_input(input);
        starts_search();
    }

    @Step
    public void enters_email(String email) {
        automationPracticePage.enter_newsletter_email(email);
    }

    @Step
    public void initiates_subscribe() {
        automationPracticePage.subscribe_to_newsletter();
    }

    @Step
    public void should_see_success_alert() {
        assertThat(automationPracticePage.getAlertText(), containsString("Newsletter : You have successfully subscribed to this newsletter."));
    }

    @Step
    public void subscribes_to_newsletter(String email) {
        enters_email(email);
        initiates_subscribe();
    }
}