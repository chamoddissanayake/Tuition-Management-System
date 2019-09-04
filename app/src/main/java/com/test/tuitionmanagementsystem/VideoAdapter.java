package com.test.tuitionmanagementsystem;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends PagerAdapter {

    private List<ModelVideo> modelVideos;
    private LayoutInflater layoutInflater;
    private Context context;

    public VideoAdapter(List<ModelVideo> modelVideos, Context context) {
        this.modelVideos = modelVideos;
        this.context = context;
    }


    @Override
    public int getCount() {
        return modelVideos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_video, container, false);

        ImageView imageView;
        TextView title;
//        desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
//        desc = view.findViewById(R.id.desc);

        //imageView.setImageResource(modelVideos.get(position).getImage());
        Picasso.with(context).load(modelVideos.get(position).getImage()).into(imageView);

        title.setText(modelVideos.get(position).getTitle());
        //desc.setText(modelVideos.get(position).getLink());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
                String youtubeLink = modelVideos.get(position).getLink();
                /// Toast.makeText(context,youtubeLink,Toast.LENGTH_LONG).show();


//                Intent videoClient = new Intent(Intent.ACTION_VIEW);
//                videoClient.setData(Uri.parse("http://m.youtube.com/watch?v="+videoId));
//                startActivityForResult(videoClient, 1234);

                // finish();


                String youtubeLinkID =youtubeLink;
                youtubeLinkID = youtubeLinkID.replace("https://www.youtube.com/watch?v=", "");

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeLinkID));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeLinkID ));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }

            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }




}
