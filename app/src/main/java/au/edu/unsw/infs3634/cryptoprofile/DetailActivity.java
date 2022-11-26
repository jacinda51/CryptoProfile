package au.edu.unsw.infs3634.cryptoprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import au.edu.unsw.infs3634.cryptoprofile.api.Coin;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "DetailActivity";
    private TextView mName;
    private TextView mSymbol;
    private TextView mValue;
    private TextView mChange1h;
    private TextView mChange24h;
    private TextView mChange7d;
    private TextView mMarketcap;
    private TextView mVolume;
    private ImageView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get handle for view elements
        mName = findViewById(R.id.tvName);
        mSymbol = findViewById(R.id.tvSymbol);
        mValue = findViewById(R.id.tvValueField);
        mChange1h = findViewById(R.id.tvChange1hField);
        mChange24h = findViewById(R.id.tvChange24hField);
        mChange7d = findViewById(R.id.tvChange7dField);
        mMarketcap = findViewById(R.id.tvMarketcapField);
        mVolume = findViewById(R.id.tvVolumeField);
        mSearch = findViewById(R.id.ivSearch);

        // Get the intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Intent Message = " + message);
            Coin coin = Coin.findCoin(message);
            if(coin != null) {
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                setTitle(coin.getName());
                mName.setText(coin.getName());
                mSymbol.setText(coin.getSymbol());
                mValue.setText(formatter.format(Double.valueOf(coin.getPriceUsd())));
                mChange1h.setText(coin.getPercentChange1h() + " %");
                mChange24h.setText(coin.getPercentChange24h() + " %");
                mChange7d.setText(coin.getPercentChange7d() + " %");
                mMarketcap.setText(formatter.format(Double.valueOf(coin.getMarketCapUsd())));
                mVolume.setText(formatter.format(coin.getVolume24()));
                mSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchCoin(coin.getName());
                    }
                });
            }
        }
    }

        // Called when the user taps the search icon
        public void searchCoin(String name) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
            startActivity(intent);
        }
}