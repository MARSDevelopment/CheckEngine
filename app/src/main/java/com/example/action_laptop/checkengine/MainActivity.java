package com.example.action_laptop.checkengine;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variables
        Button btnWriteXML = (Button)findViewById(R.id.btnWriteXML);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        btnWriteXML.setOnClickListener(
            new Button.OnClickListener(){
                public void onClick(View v){
                    TextView txtCreate = (TextView)findViewById(R.id.txtCreated);
                    CarXMLHandler carXMLHandler = new CarXMLHandler();

                    try{
                        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.custom);
                        CarValues carValues = carXMLHandler.ParseCarXMLHandler(xmlResourceParser);

                        if(carValues != null){
                            txtCreate.setText("Your file was read.");
                        } else{
                            txtCreate.setText("fail.");
                        }
                    } catch (Exception ex){
                        Exception exception = ex;
                    }
                }
            }
        );
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
        Intent intent;

        switch (item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_notifications:
                // TODO Handle Notification Menu Item
//                intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
                break;
            default:
                //defaults to home page
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
