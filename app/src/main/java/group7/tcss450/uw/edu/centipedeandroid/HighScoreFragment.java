package group7.tcss450.uw.edu.centipedeandroid;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HighScoreFragment extends Fragment {

    private Context mContext;
    private ListView listView;


    public HighScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_high_score, container, false);
        listView = (ListView) v.findViewById(R.id.high_scores_list);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<HighScore> listOfScores =
                MenuActivity.getHighScores(mContext, mContext.getString(R.string.scores_list));
        List<String> scores = new ArrayList<>();
        if (listOfScores != null) {
            for (HighScore temp : listOfScores) {
                Calendar cal = toCalendar(temp.getDate());
                scores.add("SCORE: " + temp.getScore() + " : " + "Date: " + cal.get(Calendar.MONTH)
                        + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
            }
        } else {
            scores.add("You must Play A Game To Have A Score!");
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

}
