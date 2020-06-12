package colorcoded.opencolor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoLibraryImageAdapter extends BaseAdapter {


    private Context mContext;
    public int[] imageArr = {R.drawable.colorblindtest01, R.drawable.colorblindtest02, R.drawable.colorblindtest02
            , R.drawable.colorblindtest02, R.drawable.colorblindtest02, R.drawable.colorblindtest02
            , R.drawable.colorblindtest02, R.drawable.colorblindtest02, R.drawable.colorblindtest02
            , R.drawable.colorblindtest02, R.drawable.colorblindtest02, R.drawable.colorblindtest02
            , R.drawable.colorblindtest02, R.drawable.colorblindtest02};

    ArrayList<Integer> chosenImageArr = new ArrayList<>();

    public PhotoLibraryImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imageArr.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(imageArr[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(350,350));

        return imageView;
    }
}
