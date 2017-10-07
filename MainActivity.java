package com.ibm.moodFinder;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

	NaturalLanguageUnderstanding nlu;
	EditText editText;
	String text;

	private NaturalLanguageUnderstanding initNLUService()	{

		NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
			NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
			"213094ed-f2ca-4ac0-961c-c3c163e46606",
			"EfSMlqCEuyN4"
		);
		return service;
	}

	private class WatsonTask extends AsyncTask<String, Void, String>	{

		@Override
		protected String doInBackground(String... s) {
			String finalTone="";
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
				}
			});

			nlu = initNLUService();

			HashMap<Double,String> emo = new HashMap<>();

			EmotionOptions emotion= new EmotionOptions.Builder()
//				.targets(targets)
				.build();

			Features features = new Features.Builder()
				.emotion(emotion)
				.build();

			AnalyzeOptions parameters = new AnalyzeOptions.Builder()
				.text(text)
				.features(features)
				.build();

			AnalysisResults response = nlu
				.analyze(parameters)
				.execute();


			emo.put(response.getEmotion().getDocument().getEmotion().getAnger(),"anger");
			emo.put(response.getEmotion().getDocument().getEmotion().getDisgust(),"disgust");
			emo.put(response.getEmotion().getDocument().getEmotion().getFear(),"fear");
			emo.put(response.getEmotion().getDocument().getEmotion().getJoy(),"joy");
			emo.put(response.getEmotion().getDocument().getEmotion().getSadness(),"sadness");

			ArrayList<Double> vals = new ArrayList<>(emo.keySet());
			Collections.sort(vals);

			finalTone = emo.get(vals.get(vals.size()-1));
			return finalTone;
		}

		@Override
		protected void onPostExecute(String result) {
			editText.setText(result);
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		 editText = (EditText)findViewById(R.id.edittext);
		 Button analyzeBtn = (Button)findViewById(R.id.analyzebtn);

        // Core SDK must be initialized to interact with Bluemix Mobile services.
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_UK);

        analyzeBtn.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  text = editText.getText().toString();
				  WatsonTask task = new WatsonTask();
				  task.execute(new String[]{});
			  }
		  });

        

        

        

        

        

        

        
    }

    @Override
    public void onResume() {
        super.onResume();
        
        
        
    }

    @Override
    public void onPause() {
        super.onPause();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
}
