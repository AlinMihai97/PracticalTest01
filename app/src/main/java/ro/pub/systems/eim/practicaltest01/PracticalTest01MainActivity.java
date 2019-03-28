package ro.pub.systems.eim.practicaltest01;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    int left_val = 0;
    int right_val = 0;
    EditText left;
    EditText right;
    Button leftb;
    Button rightb;
    Button navigate;


    private IntentFilter intentFilter = new IntentFilter();
    private MyReceiver messageBroadcastReceiver = new MyReceiver();
    int serviceStatus = Constants.STOPPED;

    View.OnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        this.left = (EditText) findViewById(R.id.left_edit_text);
        this.right = (EditText) findViewById(R.id.right_edit_text);
        this.navigate = (Button) findViewById(R.id.navigate_to_secondary_activity_button);

        left.setText(String.valueOf(left_val));
        right.setText(String.valueOf(right_val));

        this.leftb = (Button) findViewById(R.id.left_button);
        this.rightb = (Button) findViewById(R.id.right_button);

        listener = new ButtonClickListener();
        leftb.setOnClickListener(listener);
        rightb.setOnClickListener(listener);
        navigate.setOnClickListener(listener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
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
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            int leftNumber = Integer.valueOf(left.getText().toString());
            int rightNumber = Integer.valueOf(right.getText().toString());

            switch (view.getId()) {
                case R.id.left_button:
                    leftNumber++;
                    left.setText(String.valueOf(leftNumber));
                    launchService(leftNumber, rightNumber);
                    break;
                case R.id.right_button:
                    rightNumber++;
                    right.setText(String.valueOf(rightNumber));
                    launchService(leftNumber, rightNumber);
                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    int numberOfClicks = Integer.parseInt(left.getText().toString()) + Integer.parseInt(right.getText().toString());
                    intent.putExtra("numberOfClicks",numberOfClicks);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }

        void launchService(int left, int right) {
            if(left + right > Constants.NUMBER_OF_CLICKS && serviceStatus == Constants.STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", left);
                intent.putExtra("secondNumber", right);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.STARTED;
            }

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("leftCount", left.getText().toString());
        savedInstanceState.putString("rightCount", right.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("leftCount")) {
            left.setText(savedInstanceState.getString("leftCount"));
        } else {
            left.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
            right.setText(savedInstanceState.getString("rightCount"));
        } else {
            right.setText(String.valueOf(0));
        }
    }

}
