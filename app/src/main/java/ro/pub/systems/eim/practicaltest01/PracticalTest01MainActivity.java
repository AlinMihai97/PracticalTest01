package ro.pub.systems.eim.practicaltest01;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    int scor;
    Random random = new Random();
    EditText editText1;
    EditText editText2;
    EditText editText3;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;

    Button playButton;
    int mainScore;
    int[] posibilities_array = {0, 1, 2,3};

    private IntentFilter intentFilter = new IntentFilter();
    private MyReceiver messageBroadcastReceiver = new MyReceiver();
    int serviceStatus = Constants.STOPPED;

    View.OnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

       editText1 = (EditText) findViewById(R.id.editText1);
       editText2 = (EditText) findViewById(R.id.editText2);
       editText3 = (EditText) findViewById(R.id.editText3);

       checkBox1 = (CheckBox) findViewById(R.id.checkBox);
       checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
       checkBox3 = (CheckBox) findViewById(R.id.checkBox3);

       playButton = (Button) findViewById(R.id.play_button);
        mainScore = 0;
        listener = new ButtonClickListener();
        playButton.setOnClickListener(listener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("totalScore", mainScore);
}
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey("totalScore")) {
            mainScore = savedInstanceState.getInt("totalScore");
            Toast.makeText(this, "The activity reinited with the score " + mainScore , Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            mainScore += intent.getIntExtra("score", -1);
            Toast.makeText(this, "The activity returned with totalScore " + mainScore , Toast.LENGTH_LONG).show();

            if(mainScore > 150) {
                Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", mainScore);
                getApplicationContext().startService(intent2);
                serviceStatus = Constants.STARTED;
        }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            int number1 = posibilities_array[random.nextInt(posibilities_array.length)];
            int number2 = posibilities_array[random.nextInt(posibilities_array.length)];
            int number3 = posibilities_array[random.nextInt(posibilities_array.length)];

            Log.d("[MESSAGE]", "The 3 numbers are:" + number1 + " "+  number2 + " "+  number3 );

            switch (view.getId()) {
                case R.id.play_button:
                    if (!checkBox1.isChecked()) {
                        editText1.setText(String.valueOf(number1));
                    }
                    if (!checkBox2.isChecked()) {
                        editText2.setText(String.valueOf(number2));
                    }
                    if (!checkBox3.isChecked()) {
                        editText3.setText(String.valueOf(number3));
                    }
            }

            launchSecondarty(number1, number2, number3);
        }

        void launchSecondarty(int number1, int number2, int number3) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra("number1", Integer.parseInt(editText1.getText().toString()));
            intent.putExtra("number2", Integer.parseInt(editText2.getText().toString()));
            intent.putExtra("number3", Integer.parseInt(editText3.getText().toString()));

            int countChecked = 0;
            if(checkBox1.isChecked()) {
                countChecked++;
            }

            if(checkBox2.isChecked()) {
                countChecked++;
            }
            if(checkBox3.isChecked()) {
                countChecked++;
            }


            intent.putExtra("checkedCount", countChecked);

            startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }

//        void launchService(int left, int right) {
//            if(left + right > Constants.NUMBER_OF_CLICKS && serviceStatus == Constants.STOPPED) {
//                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
//                intent.putExtra("firstNumber", left);
//                intent.putExtra("secondNumber", right);
//                getApplicationContext().startService(intent);
//                serviceStatus = Constants.STARTED;
//            }
//
//        }
    }
//

