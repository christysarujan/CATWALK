package lk.catwalk.catwalk.crawler;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lk.catwalk.catwalk.model.Dress;

public class Crawler {

    public Crawler() {

    }

    public Response trend() throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(25, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(25, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url("http://catwalk.cypercode.com/trends")
                .build();
        return client.newCall(request).execute();
    }
    public Response trend(String search) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(25, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(25, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url("http://catwalk.cypercode.com/trends/"+search)
                .build();
        return client.newCall(request).execute();
    }

    public Response article() throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(25, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(25, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url("http://catwalk.cypercode.com/article")
                .build();
        return client.newCall(request).execute();
    }
}
