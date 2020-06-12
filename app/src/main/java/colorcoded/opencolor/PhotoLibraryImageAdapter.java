package colorcoded.opencolor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

    ArrayList<Uri> chosenImageArr = new ArrayList<>();

    public PhotoLibraryImageAdapter(){};

    public PhotoLibraryImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return chosenImageArr.size();
    }

    @Override
    public Object getItem(int position) {
        return chosenImageArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Picture picture = new Picture();

        ImageView imageView = new ImageView(mContext);
        //imageView.setImageResource(imageArr[position]);
        imageView.setImageURI(chosenImageArr.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(350,350));

        return imageView;
    }
}
