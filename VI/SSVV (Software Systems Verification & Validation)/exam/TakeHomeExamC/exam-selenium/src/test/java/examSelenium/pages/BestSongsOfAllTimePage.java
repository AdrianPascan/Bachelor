package examSelenium.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("https://www.thedelite.com/best-songs-of-all-time-according-to-critics-and-fans")
public class BestSongsOfAllTimePage extends PageObject {

    public List<String> getSongs() {
        int songsNo = 50;
        List<String> songs = new ArrayList<>();

        for (int songNo = songsNo; songNo >= 1; songNo--) {
            String song = find(By.id("header-slide-" + String.valueOf(songNo))).getText();
            songs.add(song);
        }

        return songs;
    }

}
