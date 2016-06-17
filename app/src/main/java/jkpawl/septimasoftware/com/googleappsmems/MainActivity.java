package jkpawl.septimasoftware.com.googleappsmems;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jkpawl.septimasoftware.com.googleappsmems.datamodels.RowItem;
import jkpawl.septimasoftware.com.googleappsmems.datamodels.RowList;
import jkpawl.septimasoftware.com.googleappsmems.utils.CustomLinearLayoutManager;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MainActivity";

    private final String WEB_SERVICE_URL = "https://script.google.com/macros/s/AKfycbxc2R6IMJXi79pzb9m-dEz41bnJ6102e_QxkrEhlBfiXyCoDj0/exec";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final int CODE_SUCCESS = 200;

    private List<RowItem> mRows;
    private RowItemAdapter mAdapter;

    @Bind(R.id.row_list)
    RecyclerView mRowsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Ion.getDefault(this).configure().setLogging(LOG_TAG, Log.VERBOSE);
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        //set cache parameters
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
//        builder.memoryCache(new LruCache(24000));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        //built.setLoggingEnabled(DEBUG);
        Picasso.setSingletonInstance(built);


        mRows = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllRows();
//                getFakeRows();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshGui();
    }

    private void refreshGui() {
        mAdapter = new RowItemAdapter(mRows, this);

        mRowsRecyclerView.setAdapter(mAdapter);
        mRowsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRowsRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        mAdapter.notifyDataSetChanged();
    }


    private void getFakeRows() {
        mRows.clear();
        mRows.add(new RowItem(1, "jeden", "https://www.google.pl/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiBtLqH-K7NAhVGjSwKHQvFBwUQjRwIBA&url=http%3A%2F%2Fulubionykolor.pl%2Fobrazek%2F2825&psig=AFQjCNGIc2wai0KQoN_LsuCnpmfSO_-2hA&ust=1466248664105962"));
        mRows.add(new RowItem(2, "dwa", "https://www.google.pl/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwi1n4uT-K7NAhWB8ywKHc9NDmYQjRwIBw&url=http%3A%2F%2Fwww.czeskimarket.pl%2Fp40-Obrazek-podkladka-Krecik-i-ptaszek-z-kwiatami-25-cm&psig=AFQjCNGJ06GWwFLnpBMc0PU1Oi9OB6MW_w&ust=1466248664269858"));

        mAdapter.notifyDataSetChanged();
    }

    private void getAllRows() {
        Log.d(LOG_TAG, "getAllRows");
        Ion.with(this)
                .load(METHOD_GET, WEB_SERVICE_URL)
                .as(new TypeToken<RowList>() {
                })
                .withResponse()
                .setCallback(new FutureCallback<Response<RowList>>() {
                    @Override
                    public void onCompleted(Exception e, Response<RowList> result) {
                        if (null != e) {
                            Log.e(LOG_TAG, e.toString());
                        }
                        Log.d(LOG_TAG, "onCompleted, result" + result);

                        if (null == result) return;

                        RowList rl = result.getResult();
                        if (null != rl && null != rl.list) {
                            mRows.clear();

                            StringBuilder sb = new StringBuilder();
                            sb.append("getAllRows:");
                            for (RowItem ri : rl.list) {
                                sb.append(ri.id).append(", ")
                                        .append(ri.name).append(", ")
                                        .append(ri.img);

                                mRows.add(ri);

                            }

                            Log.d(LOG_TAG, "getAllRows: " + sb.toString());
                            mAdapter.notifyDataSetChanged();


                        }
                    }
                });
    }

}
