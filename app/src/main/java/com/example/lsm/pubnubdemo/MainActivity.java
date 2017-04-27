package com.example.lsm.pubnubdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    PNConfiguration pnConfiguration;
    JSONObject jsonObject;
    PubNub pubnub;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-b7926738-2a8a-11e7-984f-0619f8945a4f");
        pnConfiguration.setPublishKey("pub-c-922a861c-981a-45f6-83ff-535f86d30e1f");
        pnConfiguration.setSecure(true);

         pubnub = new PubNub(pnConfiguration);
    }
    public void fetchData()
    {
         jsonObject= new JSONObject();
        try {
            jsonObject.put("1","one");
            jsonObject.put("2","Two");
            jsonObject.put("3","Three");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void sendPubnubData(View view)
    {
        fetchData();
        pubnub.publish()
                .message(jsonObject)
                .channel("energy.23")
                .shouldStore(true)
                .usePOST(true)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (status.isError()) {
                            // something bad happened.
                            System.out.println("error happened while publishing: " + status.toString());
                        } else {
                            System.out.println("publish worked! timetoken: " + result.getTimetoken());
                        }
                    }
                });


    }

}
