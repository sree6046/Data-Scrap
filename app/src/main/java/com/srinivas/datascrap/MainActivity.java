package com.srinivas.datascrap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity {

    private static String TextFrom = "This data taken from the following link: \n";
    Button button;
    org.jsoup.nodes.Document mBlogDocument;
    LinearLayout linearLayout;
    TextView tv1, tv2, tv3;
    private ProgressDialog mProgressDialog;
    private String url = "https://github.blog/2018-06-04-github-microsoft/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.linear);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Here actually one request is enough because you were given same url for 3 requests.
                //I am using same url for 3 requests as you asked.
                new TenthCharacter().execute();
                new TenthArrayCharacter().execute();
                new WordCount().execute();
            }
        });
    }

    // To Get the 10 th character from the web content
    private class TenthCharacter extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                mBlogDocument = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Elements data1 = mBlogDocument.select("p").eq(4);
            String text = data1.text();
            String a_letter = Character.toString(text.charAt(9));
            if (a_letter.equals(" ")) {
                a_letter = "  (white space)";
            }
            System.out.println(a_letter); // Prints f
            tv1.setText(TextFrom + url + "\n\n" + text + "\n\n 10th Character : " + a_letter);
        }
    }


    // To Get the Every 10th character from the String
    private class TenthArrayCharacter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                mBlogDocument = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Elements data1 = mBlogDocument.select("p").eq(4);
            String text = data1.text();
            String a_letter = TextFrom + url + "\n\n" + text + "\n\n";
            int j;
            for (int i = 9; i < text.length(); i = i + 10) {
                j = i + 1;
                String str = j + "th character: " + Character.toString(text.charAt(i)) + "\n";
                a_letter = a_letter + str;
            }
            tv2.setText(a_letter);
            mProgressDialog.dismiss();
        }
    }


    //To Get the Word and display count of words
    private class WordCount extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                mBlogDocument = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            linearLayout.setVisibility(View.VISIBLE);
            Elements data1 = mBlogDocument.select("p").eq(4);
            String text = data1.text();

            try {
                String[] splitArray = text.split("\\s+");
                String word = TextFrom + url + "\n\n" + text + "\n\n";
                for (int i = 0; i < splitArray.length; i++) {
                    String str = splitArray[i];
                    int x = 0;

                    for (int j = 0; j < splitArray.length; j++) {
                        String find = splitArray[j];

                        if (str.equalsIgnoreCase(find)) {
                            x++;
                        }
                    }

                    String string = splitArray[i].replaceAll(",", "") + "    (" + x + ")\n";
                    word = word + string;
                }
                tv3.setText(word);

            } catch (PatternSyntaxException ex) {

            }
        }
    }
}