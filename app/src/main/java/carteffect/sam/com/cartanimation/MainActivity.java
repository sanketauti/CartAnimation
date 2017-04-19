package carteffect.sam.com.cartanimation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanketauti on 4/19/2017.
 */
public class MainActivity extends AppCompatActivity implements ListView.OnTouchListener, TextWatcher {

    private ArrayList<DataModel> dataModels;
    private ListView listView;
    private CustomAdapter adapter;

    private RelativeLayout rl_parent;
    private LinearLayout ll_bottomview;
    private TextView tv_cart;
    private Context mContext;
    private DisplayMetrics metrics;
    private int positionX;
    private int positionY;

    private Animation animBounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        dataModels= new ArrayList<>();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        animBounce = AnimationUtils.loadAnimation(mContext, R.anim.cartbounce);

        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        ll_bottomview = (LinearLayout) findViewById(R.id.ll_bottomview);
        listView=(ListView)findViewById(R.id.list);
        tv_cart = (TextView) findViewById(R.id.tv_cart);

        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1",R.drawable.apple_pie));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2",R.drawable.bananabread));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3",R.drawable.cupcake));
        dataModels.add(new DataModel("Donut","Android 1.6","4",R.drawable.donut));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5",R.drawable.eclair));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8",R.drawable.froyo));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9",R.drawable.gingerbread));
        dataModels.add(new DataModel("Honeycomb","Android 3.0","11",R.drawable.honeycomb));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14",R.drawable.ics));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16",R.drawable.jellybean));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19",R.drawable.kitkat));
        dataModels.add(new DataModel("Lollipop","Android 5.0","21",R.drawable.lollipop));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23",R.drawable.marsh));


        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View cardView = mLayoutInflater.inflate(R.layout.row_item, (ViewGroup) view.getParent(), false);

                TextView txtName = (TextView) cardView.findViewById(R.id.name);
                TextView txtType = (TextView) cardView.findViewById(R.id.type);
                TextView txtVersion = (TextView) cardView.findViewById(R.id.version_number);
                ImageView info = (ImageView) cardView.findViewById(R.id.item_info);

                txtName.setText(dataModel.getName());
                txtType.setText(dataModel.getType());
                txtVersion.setText(dataModel.getVersion_number());
                info.setImageResource(dataModel.getImage());

                rl_parent.addView(cardView);
                doMaterialAnimation(cardView);

            }
        });
        listView.setOnTouchListener(this);
        tv_cart.addTextChangedListener(this);
    }

    private void doMaterialAnimation(View cardView) {
        AnimationSet animSet = new AnimationSet(true);
        animSet.setFillAfter(true);
        animSet.setDuration(500);
        //animSet.setInterpolator(new BounceInterpolator());
        TranslateAnimation translate = new TranslateAnimation(0, (metrics.widthPixels) / 3, positionY, ll_bottomview.getY() + 4);
        animSet.addAnimation(translate);
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.grey_300));
        ScaleAnimation scale = new ScaleAnimation(1f, 0f, 1f, 0f, ScaleAnimation.ABSOLUTE,
                0f, ScaleAnimation.RELATIVE_TO_PARENT, 0.9f);
//        ScaleAnimation scale = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_PARENT,
//                .6f, ScaleAnimation.RELATIVE_TO_PARENT, .4f);
//        ScaleAnimation scale = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_PARENT, .5f, ScaleAnimation.RELATIVE_TO_PARENT, .5f);
        scale.setInterpolator(new DecelerateInterpolator());
        animSet.addAnimation(scale);
        cardView.startAnimation(animSet);

        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    tv_cart.setText(String.valueOf(Integer.parseInt(tv_cart.getText().toString().trim())+1));
                } catch (Exception e) {
                    tv_cart.setText("0");
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        positionX = (int) event.getX();
        positionY = (int) event.getY();
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tv_cart.startAnimation(animBounce);
    }
}
