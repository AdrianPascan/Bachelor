package examSelenium.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@DefaultUrl("https://playback.fm/birthday-song")
public class BirthdaySongPage extends PageObject {

    @FindBy(id = "month")
    private WebElementFacade monthSelect;

    @FindBy(id = "day")
    private WebElementFacade daySelect;

    @FindBy(id = "year")
    private WebElementFacade yearSelect;

    @FindBy(className = "findSong")
    private WebElementFacade findSongButton;

    @FindBy(tagName = "strong")
    private WebElementFacade songNameTag;

    public void select_month(int monthIndex) {
        Select monthDropdown = new Select(monthSelect);
        monthDropdown.selectByIndex(monthIndex);
    }

    public void select_day(int dayIndex) {
        Select dayDropdown = new Select(daySelect);
        dayDropdown.selectByIndex(dayIndex);
    }

    public void select_year(int year) {
        Select yearDropdown = new Select(yearSelect);
        yearDropdown.selectByValue(String.valueOf(year));
    }

    public void press_find_song() {
        findSongButton.click();
    }

    public String get_song_name() {
        return songNameTag.getText();
    }

}
