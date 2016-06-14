package shakestudios.traintimer.Util;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import shakestudios.traintimer.R;
import shakestudios.traintimer.navigationAcivity;

public class About extends navigationAcivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView text = (TextView) findViewById(R.id.About);
        text.setText("This is the about Page");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
