package ro.pub.systems.eim.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView textView = null;
    private Button okButton = null;
    private Button cancelButton = null;
    private View.OnClickListener listener = null;

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        listener = new MyClickListener();

        textView = findViewById(R.id.secondary_text);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey("numberOfClicks")) {
            textView.setText(String.valueOf(intent.getIntExtra("numberOfClicks", -1)));
        }

        okButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);


    }
}
