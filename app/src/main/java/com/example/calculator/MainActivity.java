package com.example.calculator;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView input = findViewById(R.id.inputView);
        TextView output = findViewById(R.id.resultView);
        HorizontalScrollView svInput = findViewById(R.id.svInput);
        HorizontalScrollView svOutput = findViewById(R.id.svOutput);

        View.OnClickListener symbolClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                input.append(b.getText().toString());
                focus(input, svInput);
            }
        };

        View.OnClickListener functionClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                input.append(b.getText().toString() + "(");
                focus(input, svInput);
            }
        };



        {
            ArrayList<Button> buttons = new ArrayList<Button>();
            buttons.add((Button) findViewById(R.id.number0Button));
            buttons.add((Button) findViewById(R.id.number1Button));
            buttons.add((Button) findViewById(R.id.number2Button));
            buttons.add((Button) findViewById(R.id.number3Button));
            buttons.add((Button) findViewById(R.id.number4Button));
            buttons.add((Button) findViewById(R.id.number5Button));
            buttons.add((Button) findViewById(R.id.number6Button));
            buttons.add((Button) findViewById(R.id.number7Button));
            buttons.add((Button) findViewById(R.id.number8Button));
            buttons.add((Button) findViewById(R.id.number9Button));
            buttons.add((Button) findViewById(R.id.plusButton));
            buttons.add((Button) findViewById(R.id.minusButton));
            buttons.add((Button) findViewById(R.id.multiplyButton));
            buttons.add((Button) findViewById(R.id.divideButton));
            buttons.add((Button) findViewById(R.id.dotButton));
            buttons.add((Button) findViewById(R.id.powButton));
            buttons.add((Button) findViewById(R.id.openParButton));
            buttons.add((Button) findViewById(R.id.closedParButton));
            for(Button button : buttons){
                button.setOnClickListener(symbolClick);
            }



            ArrayList<Button> functions = new ArrayList<Button>();
            functions.add((Button) findViewById(R.id.sinButton));
            functions.add((Button) findViewById(R.id.cosButton));
            functions.add((Button) findViewById(R.id.sqrtButton));
            functions.add((Button) findViewById(R.id.logButton));

            for(Button button : functions){
                button.setOnClickListener(functionClick);
            }

            Button enter = (Button) findViewById(R.id.equalButton);
            Button del = (Button) findViewById(R.id.delButton);


            enter.setOnClickListener((View v) -> {
                try {
                    Expr lol = new Parser(Lexer.tokenize(input.getText().toString())).parse();
                    output.setText(String.format("%f", lol.evaluate()));

                } catch (Exception e ){
                    output.setText("Errore");
                }
            });

            del.setOnClickListener((View v) -> {
                if(input.length() > 0) {
                    input.setText(input.getText().subSequence(0, input.length() - 1));
                    focus(input, svInput);
                }

            });

            del.setOnLongClickListener((View v) -> {
                input.setText(null);
                output.setText(null);
                focus(input, svInput);
                focus(output, svOutput);
                return true;
            });

        }
    }

    public void focus(TextView tv, HorizontalScrollView sv){
        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.scrollTo(tv.getRight(), tv.getTop());
            }
        });
    }

    }