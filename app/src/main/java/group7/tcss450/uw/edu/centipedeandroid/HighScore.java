package group7.tcss450.uw.edu.centipedeandroid;

import java.util.Date;

/**
 * Created by Travis Holloway on 3/5/2017.
 * A custom class to track scores.
 */

public class HighScore {

        private int score;
        private Date date;

        public HighScore(final int theScore, final Date theDate) {
            this.score = theScore;
            this.date = theDate;
        }

        public int getScore() {
            return score;
        }

        public Date getDate() {
            return date;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setDate(Date date) {
            this.date = date;
        }
}
