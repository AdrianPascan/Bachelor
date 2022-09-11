package examSelenium.stories;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import examSelenium.steps.EndUserSteps;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RunWith(SerenityRunner.class)
public class MyBirthdaySongStory {

    private static final int SECONDS_TO_KEEP_PAGE_OPEN = 1000;

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps student;

    @Test
    public void searching_for_my_birthday_song() {
        webdriver.manage().window().maximize();

        // the student goes to the birthday songs page
        student.open_birthday_songs_page();

        // he retrieves his corresponding birthday song (full name)
        // ...
        String songAndArtist = student.gets_song_for_birthday(8, 20, 1999);

        String[] temp = songAndArtist.split(" by ");
        String song = temp[0];
        String artist = temp[1];


        // he open the article with the top 100 songs of all time
        student.open_best_songs_of_all_time_page();

        // he checks if his songs is in the top (simply tries to match the full name)
        // and then prints the appropriate message in the console
        // ...
        student.checks_for_song_in_best_songs_of_all_time(song, artist);


        // he goes on Wikipedia
        student.open_wikipedia_page();

        // he searches for the song there as well and opens the first result
        // ...
        student.searches_for("song " + song);
        student.clicks_on_first_link();


        // keep the page open a little longer after the process is over
        waitForIt(SECONDS_TO_KEEP_PAGE_OPEN);

    }

    private void waitForIt(int secondsToWait)
    {
//        webdriver.manage().timeouts().implicitlyWait(secondsToWait, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(secondsToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
