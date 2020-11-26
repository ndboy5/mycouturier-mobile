package com.raidify.mobi.mycouturier.api_server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface ServerResponseCallback {
    public void  onJSONResponse (JSONObject jsonObj);
    public void  onJSONArray (JSONArray jsonArray);
    public void  onError (JSONException jsonException);
}
