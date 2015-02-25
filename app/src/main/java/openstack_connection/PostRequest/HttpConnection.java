package openstack_connection.PostRequest;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tanay.openstackdashboard.OnLoadingCompleted;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HttpConnection extends AsyncTask<Object, Integer, Boolean> {
    String host = "";
    HttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost();
    HttpGet get = new HttpGet();
    HttpResponse response;
    JSONObject jsonResponse;
    Boolean isGet;
    Boolean success;

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean setPostRequest(String urlPostfix, JSONObject obj) {
        try {
            StringEntity stringEntity = new StringEntity(obj.toString());
            post = new HttpPost(host+urlPostfix);
            post.setEntity(stringEntity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            isGet = false;

        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean setGetRequest(String urlPostfix, JSONObject obj) {
        try {
//            StringEntity stringEntity = new StringEntity(obj.toString());
            get = new HttpGet(host+urlPostfix);
//            get.setEntity(stringEntity);
            get.setHeader("Accept", "application/json");
//            get.setHeader("Content-type", "application/json");
            isGet = true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            if (isGet) {
                Log.i("Running: ","Running get request");
                response = client.execute(get);
            } else {
                Log.i("Running: ","Running post request");
                response = client.execute(post);
            }
            Log.i("HTTPResponse","The response is: "+response.getStatusLine().getStatusCode());
            String jsonString = EntityUtils.toString(response.getEntity());
            Log.i("Recieved JSON Object: ", jsonString);
            jsonResponse = new JSONObject(jsonString);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {  //If status 200
                success = true;
            } else {
                success = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
