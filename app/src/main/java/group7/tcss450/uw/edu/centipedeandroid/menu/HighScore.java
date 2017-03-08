package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Travis Holloway on 3/5/2017.
 * A custom class to track scores.
 */

public class HighScore implements Comparable<HighScore> {

        private int score;
        private Date date;

        public HighScore(final int theScore, final Date theDate) {
            this.score = theScore;
            this.date = theDate;
        }

        int getScore() {
            return score;
        }

        Date getDate() {
            return date;
        }



    @Override
    public int compareTo(@NonNull HighScore highScore) {
        if  (this.score == highScore.getScore()) {
            return 0;
        }  else if(this.score > highScore.getScore()) {
            return -1;
        } else {
            return 1;
        }
    }

    public class HighScoreComparator implements Comparator<HighScore> {

        @Override
        public int compare(HighScore highScore1, HighScore highScore2) {
            if (highScore1.getScore() < highScore2.getScore()) {
                return -1;
            } else if (highScore1.getScore() > highScore2.getScore()) {
                return 1;
            }
            return 0;
        }
    }
}
