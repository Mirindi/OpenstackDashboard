package openstack_connection.PostRequest;

import com.example.tanay.openstackdashboard.ComputeData;
import com.example.tanay.openstackdashboard.OnLoadingCompleted;
import com.example.tanay.openstackdashboard.UserData;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;

/**
 * Created by tanay on 25/2/15.
 */
public class ComputeRequest extends HttpConnection {
    OnLoadingCompleted listener;
    String username;
    String url = ":5000/v2.0/"+ UserData.tokenId+"/limits";
    public void setComputeRequest() {
        this.host = UserData.host;
        setGetRequest(url,null);
    }
    @Override
    protected void onPostExecute(Boolean status) {
        if (success) {
            try {
                ComputeData.maxImageMeta = jsonResponse.getJSONObject("limits").getJSONObject("absolute").getInt("maxImageMeta");
                ComputeData.maxPersonality = jsonResponse.getJSONObject("limits").getJSONObject("absolute").getInt("maxPersonality");
                ComputeData.maxPersonalitySize = jsonResponse.getJSONObject("limits").getJSONObject("absolute").getInt("maxPersonalitySize");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listener.onLoadingCompleted(this.success);
    }
}
