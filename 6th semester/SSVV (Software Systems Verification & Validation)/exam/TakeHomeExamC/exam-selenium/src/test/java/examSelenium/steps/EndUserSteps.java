package examSelenium.steps;

import examSelenium.pages.BestSongsOfAllTimePage;
import examSelenium.pages.BirthdaySongPage;
import examSelenium.pages.WikipediaPage;
import net.thucydides.core.annotations.Step;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class EndUserSteps {

    BirthdaySongPage birthdaySongPage;
    BestSongsOfAllTimePage bestSongsOfAllTimePage;
    WikipediaPage wikipediaPage;


    // BIRTHDAY SONG PAGE

    @Step
    public void open_birthday_songs_page() {
        birthdaySongPage.open();
    }

    @Step
    public void selects_birthday_month(int month) {
        birthdaySongPage.select_month(month);
    }

    @Step
    public void selects_birthday_day(int day) {
        birthdaySongPage.select_day(day);
    }

    @Step
    public void selects_birthday_year(int year) {
        birthdaySongPage.select_year(year);
    }

    @Step
    public void selects_birthday(int month, int day, int year) {
        selects_birthday_month(month);
        selects_birthday_day(day);
        selects_birthday_year(year);
    }

    @Step
    public void presses_find_song() {
        birthdaySongPage.press_find_song();
    }

    @Step
    public String gets_song() {
        return birthdaySongPage.get_song_name();
    }

    @Step
    public String gets_song_for_birthday(int month, int day, int year) {
        selects_birthday(month, day, year);
        presses_find_song();

        return gets_song();
    }


    // BEST SONGS OF ALL TIME PAGE

    @Step
    public void open_best_songs_of_all_time_page() {
        bestSongsOfAllTimePage.open();
    }

    @Step
    public void checks_for_song_in_best_songs_of_all_time(String song, String artist) {
        List<String> bestSongs = bestSongsOfAllTimePage.getSongs();
        Optional<String> foundSong = bestSongs.stream()
                .filter(bestSong -> bestSong.toLowerCase().contains(song.toLowerCase()) && bestSong.toLowerCase().contains(artist.toLowerCase()))
                .findAny();

        if (foundSong.isPresent()) {
            System.out.printf("FOUND SONG in best songs of all time: '%s' (%s)%n", song, artist);
        } else {
            System.out.printf("DID NOT FIND song in best songs of all time: '%s' (%s)%n", song, artist);
        }
    }


    // WIKIPEDIA PAGE

    @Step
    public void open_wikipedia_page() {
        wikipediaPage.open();
    }

    @Step
    public void enters_search_input(String searchInput) {
        wikipediaPage.enter_search_input(searchInput);
    }

    @Step
    public void presses_search_button() {
        wikipediaPage.press_search_button();
    }

    @Step
    public void searches_for(String searchInput) {
        enters_search_input(searchInput);
        presses_search_button();
    }

    @Step
    public void clicks_on_first_link() {
        wikipediaPage.click_on_first_link();
    }

}
