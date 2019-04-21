package com.comp3710.exam1;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private float balance = 0;
    private String history = "";
    private TextView balanceTextView;
    private EditText date;
    private EditText amount;
    private EditText source;
    private Button deposit, withdrawal, clear;
    private TextView historyDialog;
    private DecimalFormat fmt = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balanceTextView = (TextView) findViewById(R.id.balanceTextView);
        date = (EditText) findViewById(R.id.dateEditText);
        amount = (EditText) findViewById(R.id.amountEditText);
        source = (EditText) findViewById(R.id.sourceEditText);
        deposit = (Button) findViewById(R.id.depositButton);
        withdrawal = (Button) findViewById(R.id.withdrawlButton);
        clear = (Button) findViewById(R.id.clearButton);
        historyDialog = (TextView) findViewById(R.id.historyDialog);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checkSharedPreferences();
        updateBalance();
        historyDialog.setText(history);

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (missingFields()) {
                    Toast.makeText(MainActivity.this, "Missing Fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    double numAmount = Float.parseFloat(amount.getText().toString());
                    balance += numAmount;
                    updateBalance();
                    history += "Added " + fmt.format(numAmount) + " on " + date.getText().toString()
                            + " from " + source.getText().toString() + "\n";
                    historyDialog.setText(history);

                    editor.putString("history", history);
                    editor.putFloat("balance", balance);
                    editor.apply();

                }


            }
        });

        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (missingFields()) {
                    Toast.makeText(MainActivity.this, "Missing Fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    double numAmount = Float.parseFloat(amount.getText().toString());
                    balance -= numAmount;
                    updateBalance();
                    history += "Spent " + fmt.format(numAmount) + " on " + date.getText().toString()
                            + " for " + source.getText().toString() + "\n";
                    historyDialog.setText(history);

                    editor.putString("history", history);
                    editor.putFloat("balance", balance);
                    editor.apply();

                }
            }
        });

        // Added feature to clear history and balance
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balance = 0;
                updateBalance();
                history = "";
                historyDialog.setText(history);
                editor.putString("history", history);
                editor.putFloat("balance", balance);
                editor.apply();
                Toast.makeText(MainActivity.this, "History Cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Restores previous balance and transaction history
    private void checkSharedPreferences() {
        String checkHistory = preferences.getString("history", "No History").trim();
        Float checkBalance = preferences.getFloat("balance", 0);

        if (!checkHistory.equals("No History")) {
            if (!checkHistory.isEmpty()) {
                history = checkHistory + "\n";
            }
        }

        balance = checkBalance;
    }

    // Updates the string to match the balance in current_balance TextView
    private void updateBalance() {
        balanceTextView.setText(String.format(getText(R.string.current_balance).toString(), fmt.format(balance)));
    }

    // Looks for missing inputs to ensure all relevant data is collected
    private boolean missingFields() {
        return (date.getText().toString().isEmpty()
                || amount.getText().toString().isEmpty()
                || source.getText().toString().isEmpty());
    }

}
