package vu.SmartLight.iot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Details extends AppCompatActivity {
    TextView textDesc;
    TextView textTitle;
    Switch aSwitch;
    String power;
    final String url_PostRequest = "https://use1-wap.tplinkcloud.com";
    String token = "f19dcc66-C4sM0T7phxSSZRaxLRphMec";

    //for brightness
    TextView textView1;
    SeekBar seekBar1;

    //for temperature
    TextView textView2;
    SeekBar seekBar2;

    //for hue
    TextView textView3;
    SeekBar seekBar3;

    //for saturation
    TextView textView4;
    SeekBar seekBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //for brightness
        textView1 = (TextView) findViewById(R.id.brightness);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser) {
                textView1.setText("" + progress1);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                String Brightness = textView1.getText().toString();
                new ChangeBrightness().execute(Brightness);
            }
        });

        //for temperature
        textView2 = (TextView) findViewById(R.id.temperature);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress2, boolean fromUser) {
                textView2.setText(Integer.toString(progress2 + 2500));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {
                String Temperature = textView2.getText().toString();
                new ChangeTemperature().execute(Temperature);
            }
        });

        //for hue
        textView3 = (TextView) findViewById(R.id.hue);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar3, int progress3, boolean fromUser) {
                textView3.setText("" + progress3);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar3) {
                String Hue = textView3.getText().toString();
                new ChangeHue().execute(Hue);
            }
        });

        //for saturation
        textView4 = (TextView) findViewById(R.id.saturation);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar4, int progress4, boolean fromUser) {
                textView4.setText("" + progress4);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar3) {
                String Saturation = textView4.getText().toString();
                new ChangeSaturation().execute(Saturation);
            }
        });

        textDesc = findViewById(R.id.detailDesc);
        textTitle = findViewById(R.id.detailTitle);

        Intent i = getIntent();
        String title = i.getStringExtra("Title of device");
        String desc = i.getStringExtra("Description of device");

        // set content of the story to textview
        textDesc.setText(desc);
        textDesc.setMovementMethod(new ScrollingMovementMethod());
        textTitle.setText(title);

        String DeviceId = textDesc.getText().toString();



        aSwitch=findViewById(R.id.powerswitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(aSwitch.isChecked()) {
                    //do if switch is on
                    power ="1";
                    new ChangePower().execute();

                }else{
                    //do if switch is off
                    power="0";
                    new ChangePower().execute();
                }
            }
        });




    }

    public class ChangePower extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String finalURL = url_PostRequest + "?token=" + token;
            String DeviceId = textDesc.getText().toString();
            String postbody = "{\"method\":\"passthrough\", \n" +
                    "\"params\": {\n" +
                    "\"deviceId\": \""+DeviceId+"\", \n" +
                    "\"requestData\": \"{\\\"smartlife.iot.smartbulb.lightingservice\\\":{\\\"transition_light_state\\\":{\\\"on_off\\\":" + power + "}}}\"\n" +
                    "}\n" +
                    "}\n";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8")/*MediaType.parse("text/x-markdown; charset=utf-8")*/, postbody))
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    String responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //change setting
    public class ChangeBrightness extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Brightness = textView1.getText().toString();
            String DeviceId = textDesc.getText().toString();
            String finalURL = url_PostRequest + "?token=" + token;
            String postbody = "{\"method\":\"passthrough\", \n" +
                    "\"params\": {\n" +
                    "\"deviceId\": \""+DeviceId+"\", \n" +
                    "\"requestData\": \"{\\\"smartlife.iot.smartbulb.lightingservice\\\":{\\\"transition_light_state\\\":{\\\"brightness\\\":"+Brightness+",\\\"ignore_default\\\":0,\\\"mode\\\":\\\"normal\\\",\\\"on_off\\\":" + power + ",\\\"transition_period\\\":100}}}\"\n" +
                    "}\n" +
                    "}\n";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8")/*MediaType.parse("text/x-markdown; charset=utf-8")*/, postbody))
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    String responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class ChangeTemperature extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Temperature = textView2.getText().toString();
            String DeviceId = textDesc.getText().toString();
            String finalURL = url_PostRequest + "?token=" + token;
            String postbody = "{\"method\":\"passthrough\", \n" +
                    "\"params\": {\n" +
                    "\"deviceId\": \""+DeviceId+"\", \n" +
                    "\"requestData\": \"{\\\"smartlife.iot.smartbulb.lightingservice\\\":{\\\"transition_light_state\\\":{\\\"color_temp\\\":"+Temperature+",\\\"ignore_default\\\":0,\\\"mode\\\":\\\"normal\\\",\\\"on_off\\\":" + power + ",\\\"transition_period\\\":100}}}\"\n" +
                    "}\n" +
                    "}\n";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8")/*MediaType.parse("text/x-markdown; charset=utf-8")*/, postbody))
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    String responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class ChangeHue extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Hue = textView3.getText().toString();
            String DeviceId = textDesc.getText().toString();
            String finalURL = url_PostRequest + "?token=" + token;
            String postbody = "{\"method\":\"passthrough\", \n" +
                    "\"params\": {\n" +
                    "\"deviceId\": \""+DeviceId+"\", \n" +
                    "\"requestData\": \"{\\\"smartlife.iot.smartbulb.lightingservice\\\":{\\\"transition_light_state\\\":{\\\"color_temp\\\":0,\\\"ignore_default\\\":0,\\\"mode\\\":\\\"normal\\\",\\\"on_off\\\":" + power + ",\\\"transition_period\\\":100,\\\"hue\\\":"+Hue+"}}}\"\n" +
                    "}\n" +
                    "}\n";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8")/*MediaType.parse("text/x-markdown; charset=utf-8")*/, postbody))
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    String responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class ChangeSaturation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Saturation = textView4.getText().toString();
            String DeviceId = textDesc.getText().toString();
            String finalURL = url_PostRequest + "?token=" + token;
            String postbody = "{\"method\":\"passthrough\", \n" +
                    "\"params\": {\n" +
                    "\"deviceId\": \""+DeviceId+"\", \n" +
                    "\"requestData\": \"{\\\"smartlife.iot.smartbulb.lightingservice\\\":{\\\"transition_light_state\\\":{\\\"color_temp\\\":0,\\\"ignore_default\\\":0,\\\"mode\\\":\\\"normal\\\",\\\"on_off\\\":" + power + ",\\\"transition_period\\\":100,\\\"saturation\\\":"+Saturation+"}}}\"\n" +
                    "}\n" +
                    "}\n";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8")/*MediaType.parse("text/x-markdown; charset=utf-8")*/, postbody))
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                try {
                    response = call.execute();
                    String responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
