package orange.w.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import orange.w.R;
import orange.w.common.APP;
import orange.w.customview.PlusImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageItemFragment extends Fragment {
    private PlusImageView imageView;
    private String url;

    public ImageItemFragment() {
        // Required empty public constructor
    }

    public ImageItemFragment(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_item, container, false);
        imageView = (PlusImageView) view.findViewById(R.id.item_image);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 10f);
        animator.setDuration(10);
        animator.start();
        Picasso.with(APP.appContext).load(url).into(imageView);
        return view;
    }

}
