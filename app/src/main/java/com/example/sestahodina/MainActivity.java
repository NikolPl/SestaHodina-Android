package com.example.sestahodina;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int howLongSleep; // mělo by se ošetřit, když není první hodnota, mělo by to sapdnout, ošetřeno v try catch, ale taky by se mělo ošetřit nějakou původní hodnotou

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // při vytváření instance v parametru pošeleme "údaj" ve které aktivitě to probíhá, aby se pak v asyntasku dala vyvolat

                new MyAsyncTask(MainActivity.this, howLongSleep).execute(""); //vytvořená instance, jen ji nepotřebujeme dát do proměnné
                //execute --> zmaená proveď to co je v asnyctask
                // preexecute --> proveď než půjdeš do vedlejšího vlákna --> vše se prováí na hlavním vlákně
                // jakékoli změny na UI nejsou povoleny jinde než na hlavní vlákně
            }
        });

        // a tady to tvoříme jen na hlavním vlákně --> uvidíme, že se to sekne, když se klidkne na Task

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(howLongSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Ha a zamrzlo, co?", Toast.LENGTH_SHORT).show();
            }
        });


        // tady je fce, která nám určí jak dlouho to bude spát
        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() { //--> textWatcher vygeneruje Override metody sám
            // edit text má v layoutu nastaven input type na číslo
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try { // radši to odchytneme, kdyby se převádělo něco jiného než číslo di integru
                    howLongSleep = Integer.parseInt(s.toString())*1000; // převedeme na číslo to, co se nám přenese v parametru s, který ale nejdříve musíem převeést na string, aby se s tím dalo pracovat --> výstup je ale int
                    // vynásobili jsme to 1000 --> aby tam byl pak ten vysledek v milisekundách
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // Thread --> musí se vytvořit i nová fce - ale z ní se špatně dostává zpět do hlavního vlákna --> na to se používají Handlery





}
