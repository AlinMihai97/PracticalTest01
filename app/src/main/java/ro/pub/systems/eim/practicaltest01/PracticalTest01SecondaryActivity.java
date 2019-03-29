package ro.pub.systems.eim.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    int score = 0;

    private TextView textView = null;
    private Button okButton = null;
    private Button cancelButton = null;
    private View.OnClickListener listener = null;

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    Intent intent = new Intent();
                    intent.putExtra("score", score);
                    setResult(RESULT_OK, intent);
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
        if(intent != null && intent.getExtras().containsKey("number1")) {
            int number1 = intent.getIntExtra("number1", -1);
            int number2 = intent.getIntExtra("number2", -1);
            int number3 = intent.getIntExtra("number3", -1);
            int checkedCount = intent.getIntExtra("checkedCount", -1);

            int i;
            for(i = 1; i <=3; i++) {
                if((number1 == i || number1 == 0) && (number2 == i || number2 == 0) && (number3 ==  i || number3 == 0)) {
                    if(checkedCount == 3)
                        score =0;
                    if(checkedCount == 2)
                        score = 10;
                    if(checkedCount == 1)
                        score = 50;
                    if(checkedCount == 0)
                        score = 100;
                    break;
                }
            }
            textView.setText("Gained " + String.valueOf(score));
        }



        okButton.setOnClickListener(listener);



    }
}
