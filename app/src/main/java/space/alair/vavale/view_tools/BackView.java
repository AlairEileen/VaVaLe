package space.alair.vavale.view_tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import space.alair.vavale.R;

/**
 * Created by Alair on 3/21/2018.
 */


public class BackView extends LinearLayout {
    Activity activity;
    private ImageView iv;

    public BackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        activity = (Activity) context;
        iv = new ImageView(activity);
        iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BackView);
        if (typedArray != null) {
            int src = typedArray.getResourceId(R.styleable.BackView_src, -1);
            iv.setImageDrawable(context.getResources().getDrawable(src));
        }
        this.addView(iv);
        setOnClickListener(view -> {
            activity.finish();
        });
    }


}
