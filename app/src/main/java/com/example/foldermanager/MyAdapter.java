package com.example.foldermanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    File[] filesAndFolders;

    public MyAdapter(Context context, File[] filesAndFolders)
    {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());
        if(selectedFile.isDirectory())
        {
            holder.imageView.setImageResource(R.drawable.baseline_folder_24);
        }
        else {
            holder.imageView.setImageResource(R.drawable.baseline_insert_drive_file_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = selectedFile.getAbsolutePath();
                int ext = path.lastIndexOf(".");
                String fileExt = path.substring(ext>0?ext+1:0);
                if(selectedFile.isDirectory())
                {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("path",path);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else if (fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png") || fileExt.equals("gif"))
                {
                    String filePath = path;
                    Uri fileUri = Uri.parse(filePath);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(fileUri);

                    PackageManager packageManager = context.getPackageManager();
                    List<ResolveInfo> appsList = packageManager.queryIntentActivities(intent, 0);

                    if (!appsList.isEmpty()) {
                        Intent chooserIntent = Intent.createChooser(intent, "Open with");
                        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(chooserIntent);
                    } else {
                        Intent i = new Intent(context, Image.class);
                        Uri image = Uri.parse(selectedFile.getAbsolutePath());
                        i.putExtra("Image", image.toString());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }

                }
                else if(fileExt.equals("mp4"))
                {
                    Intent i = new Intent(context, Video.class);
                    Uri video = Uri.parse(selectedFile.getAbsolutePath());
                    i.putExtra("Video", video.toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return filesAndFolders.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        public ViewHolder(View v)
        {
            super(v);
            textView = v.findViewById(R.id.textFolder);
            imageView = v.findViewById(R.id.icon);
        }
    }
}
