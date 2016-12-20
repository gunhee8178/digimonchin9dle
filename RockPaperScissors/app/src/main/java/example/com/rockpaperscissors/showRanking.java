package example.com.rockpaperscissors;

import android.app.TabActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * This class is to show your rank.
 * @author BaeSeongHun. HanGunHee
 */
public class ShowRanking extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tab = getTabHost();
        TabHost.TabSpec spec;
        LayoutInflater.from(this).inflate(R.layout.show_ranking, tab.getTabContentView(), true);

        SharedPreferences mPrefs = getSharedPreferences("RankM", MODE_PRIVATE);
        TextView mjb_rank_text = (TextView) findViewById(R.id.mjb_rank);
        mjb_rank_text.setTextSize(20);
        mjb_rank_text.setText("Ranking");
        for(int i=0; i<MainActivity.ranklistM.size(); i++) {
            String rank_name = mPrefs.getString(i + "rank_name", null);
            int rank_score = mPrefs.getInt(i + "rank_score", 0);
            mjb_rank_text.append("\n"+(i+1)+". \""+rank_name);
            mjb_rank_text.append("\" - "+rank_score+" wins");
        }

        SharedPreferences rPrefs = getSharedPreferences("RankR", MODE_PRIVATE);
        TextView rps_rank_text = (TextView) findViewById(R.id.rps_rank);
        rps_rank_text.setTextSize(20);
        rps_rank_text.setText("Ranking");
        for(int i=0; i<MainActivity.ranklistR.size(); i++) {
            String rank_name = rPrefs.getString(i + "rank_name", null);
            int rank_score = rPrefs.getInt(i + "rank_score", 0);
            int rank_draw = rPrefs.getInt(i + "rank_draw", 0);
            rps_rank_text.append("\n"+(i+1)+". \""+rank_name);
            rps_rank_text.append("\" - "+rank_score+" wins");
            rps_rank_text.append(", "+rank_draw+" draws");
        }

        Button button_ok = (Button) findViewById(R.id.rank_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spec = tab.newTabSpec("rps_tab").setIndicator("RPS").setContent(R.id.rps_tab);
        tab.addTab(spec);
        spec = tab.newTabSpec("mjb_tab").setIndicator("MJB").setContent(R.id.mjb_tab);
        tab.addTab(spec);
    }
}
