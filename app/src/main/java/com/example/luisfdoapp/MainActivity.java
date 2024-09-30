package com.example.luisfdoapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView tvRes;
    private int result = 0;
    private StringBuilder currentInput = new StringBuilder();
    private String operator = "";
    private int firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvRes = findViewById(R.id.tvRes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupButtons();
    }

    private void setupButtons() {
        setupNumberButton(R.id.btn0, 0);
        setupNumberButton(R.id.btn1, 1);
        setupNumberButton(R.id.btn2, 2);
        setupNumberButton(R.id.btn3, 3);
        setupNumberButton(R.id.btn4, 4);
        setupNumberButton(R.id.btn5, 5);
        setupNumberButton(R.id.btn6, 6);
        setupNumberButton(R.id.btn7, 7);
        setupNumberButton(R.id.btn8, 8);
        setupNumberButton(R.id.btn9, 9);

        findViewById(R.id.btnsumar).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnresta).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnmult).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btndiv).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.btnigual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnreset).setOnClickListener(v -> resetCalculator());
    }

    private void setupNumberButton(int id, int value) {
        findViewById(id).setOnClickListener(v -> {
            if (currentInput.length() < 15) {
                currentInput.append(value);
                if (operator.isEmpty()) {
                    updateDisplay(currentInput.toString());
                } else {
                    updateDisplay(firstOperand + " " + operator + " " + currentInput);
                }
            }
        });
    }

    private void updateDisplay(String text) {
        if (text.length() > 15) {
            text = text.substring(0, 15);
        }
        tvRes.setText(text);
    }

    private void setOperator(String op) {
        if (!operator.isEmpty()) {
            calculateResult();
        }
        firstOperand = Integer.parseInt(currentInput.toString());
        operator = op;
        updateDisplay(firstOperand + " " + operator + " ");
        currentInput.setLength(0);
    }

    private void calculateResult() {
        int currentNumber = Integer.parseInt(currentInput.toString());
        switch (operator) {
            case "+":
                result = firstOperand + currentNumber;
                break;
            case "-":
                result = firstOperand - currentNumber;
                break;
            case "*":
                result = firstOperand * currentNumber;
                break;
            case "/":
                if (currentNumber != 0) {
                    result = firstOperand / currentNumber;
                } else {
                    updateDisplay(getString(R.string.error));
                    return;
                }
                break;
        }
        operator = "";
        currentInput.setLength(0);
        updateDisplay(String.valueOf(result));
    }

    private void resetCalculator() {
        result = 0;
        currentInput.setLength(0);
        operator = "";
        firstOperand = 0;
        tvRes.setText("0");
    }
}
