package colorcoded.opencolor;

import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FullScreenImageView extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_view);

        imageView = findViewById(R.id.fullScreenImageView);

        Intent intent = getIntent();

        int position = intent.getExtras().getInt("IMAGE_TAPPED");

        PhotoLibraryImageAdapter photoLibraryImageAdapter = new PhotoLibraryImageAdapter(this);

        imageView.setImageResource(photoLibraryImageAdapter.imageArr[position]);
    }
}
