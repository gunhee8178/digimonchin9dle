package example.com.rockpaperscissors;

import android.app.TabActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class showRanking extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tab = getTabHost();
        TabHost.TabSpec spec;
        LayoutInflater.from(this).inflate(R.layout.show_ranking, tab.getTabContentView(), true);

        SharedPreferences mPrefs = getSharedPreferences("RankM", MODE_PRIVATE);
        TextView mjbtext = (TextView) findViewById(R.id.mjb_rank);
        mjbtext.setTextSize(20);
        mjbtext.setText("Ranking");
        for(int i=0; i<MainActivity.Mranklist.size(); i++) {
            String rank_name = mPrefs.getString(i + "rank_name", null);
            int rank_score = mPrefs.getInt(i + "rank_score", 0);
            mjbtext.append("\n"+(i+1)+". \""+rank_name);
            mjbtext.append("\" - "+rank_score+" wins");
        }

        SharedPreferences rPrefs = getSharedPreferences("RankR", MODE_PRIVATE);
        TextView rpstext = (TextView) findViewById(R.id.rps_rank);
        rpstext.setTextSize(20);
        rpstext.setText("Ranking");
        for(int i=0; i<MainActivity.Mranklist.size(); i++) {
            String rank_name = rPrefs.getString(i + "rank_name", null);
            int rank_score = rPrefs.getInt(i + "rank_score", 0);
            int rank_draw = rPrefs.getInt(i + "rank_draw", 0);
            rpstext.append("\n"+(i+1)+". \""+rank_name);
            rpstext.append("\" - "+rank_score+" wins");
            rpstext.append(", "+rank_draw+" draws");
        }

        Button button_ok = (Button) findViewById(R.id.rank_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spec = tab.newTabSpec("tab1").setIndicator("RPS").setContent(R.id.tab1);
        tab.addTab(spec);
        spec = tab.newTabSpec("tab2").setIndicator("MJB").setContent(R.id.tab2);
        tab.addTab(spec);
    }
}
