package example.com.rockpaperscissors;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class showRanking extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_ranking);

        SharedPreferences mPrefs = getSharedPreferences("Rank", MODE_PRIVATE);
        TextView t = (TextView) findViewById(R.id.rank_text);
        t.setText("Ranking!!");
        for(int i=0; i<MainActivity.ranklist.size(); i++) {
            String rank_name = mPrefs.getString(i + "rank_name", null);
            int rank_score = mPrefs.getInt(i + "rank_score", 0);
            t.append("\n"+(i+1)+". \""+rank_name);
            t.append("\" - "+rank_score+" points");
        }

        Button button_ok = (Button) findViewById(R.id.rank_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
