package com.example.sestahodina;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

// třída, která nám pak umožní vytoviřt vedlejší vlákno
public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    //vytvoříme konstruktor --> do kterého musíme poslat aktivitu
    public Activity activity;
    public int howLongSleep;

    public MyAsyncTask(Activity activity, int howLongSleep) {
        this.activity = activity;
        this.howLongSleep = howLongSleep;
    }
    /* - třída má různémetody, které lze přpsat, ale vždy musí být přepsaná doInBackground - ta určuje co se bude dít na pozadí
       pak je ještě metoda před vstupem, při vstupu, v procesu a výstupu
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        */

    @Override // nemusí bý string ... strings - ale Void nebo integer -> prostě pole nějakých nebo žádných hodnot, nemusí se s tím pracovat
    protected String doInBackground(String... strings) { // tady vlastně musí probíhat vešekerá náročná práce, aby to nebrduilo hlavní vlákno
        try {
            Thread.sleep(howLongSleep); //uspává vlákno vedlejší v milisekundách
        }
        catch (InterruptedException e){
            e.printStackTrace(); // pokud v try vyskočí chyba, tak catch to odchytne - e = error a vypíše to
        }
        return "úspěšně provedeno "; // tohle se přenese do proměnné s (parametr) a ukáže se nám to v Toastu v postExecute
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // aktivitu vyvoláme tu, ve které je to spuštěné --> vloží se jen "neutrálně" activity a ta se tam v instanci "dosadí"
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show(); // napiš To --> a nabídne k vygeneraování celou fci samo
    }
}