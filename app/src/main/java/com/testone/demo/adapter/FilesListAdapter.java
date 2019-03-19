package com.testone.demo.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FilesListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<File> mFilesList;
    private String startPath;
    private boolean mOptMultiFileItem;

    final private String[] mVideoExtensions = { "avi", "mp4", "3gp", "mov" };
    final private String[] mImagesExtensions = { "jpeg", "jpg", "png", "gif", "bmp", "wbmp" };
    private final LruCache<String, Bitmap> mBitmapsCache;

    public FilesListAdapter(Context context, List<File> mFilesList, String startPath, boolean mOptMultiFileItem) {
        this.context = context;
        this.mFilesList = mFilesList;
        this.startPath = startPath;
        this.mOptMultiFileItem = mOptMultiFileItem;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mBitmapsCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return getBitmapSize(bitmap) / 1024;
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_file_picker_list_item, null);
        return new FilePickerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        File file = mFilesList.get(position);
        if (viewHolder instanceof FilePickerHolder) {
            FilePickerHolder holder = (FilePickerHolder) viewHolder;
            if (file.isDirectory()) {
                holder.thumbnailIV.setImageResource(R.drawable.icon_filepicker_folder);
            } else {
                if ((Arrays.asList(mVideoExtensions).contains(getFileExtension(file.getName())) ||
                        Arrays.asList(mImagesExtensions).contains(getFileExtension(file.getName())))) {
                    Bitmap bitmap = getBitmapFromCache(file.getAbsolutePath());
                    if (bitmap == null){
//                        new ThumbnailLoader(holder.thumbnailIV).execute(file);
                    }else{
                        holder.thumbnailIV.setImageBitmap(bitmap);
                    }
                } else{
                    String fileName = file.getName();
                    String mimeType = "";
                    try{
                        mimeType = FileUtils.guessContentTypeFromName(fileName);
                    } catch(Exception e){
//                        MXLog.e(MXLog.APP_WARN, e);
                    }

                    int drawableInt = FileUtils.getFileIconByContentType(mimeType);

                    holder.thumbnailIV.setImageResource(drawableInt);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFilesList == null ? 0 : mFilesList.size();
    }

    private class FilePickerHolder extends RecyclerView.ViewHolder {

        private final LinearLayout itemLayoutLL;
        private final ImageView thumbnailIV;
        private final TextView fileNameTV;
        private final TextView fileSizeTV;
        private final ImageView checkBoxIV;

        public FilePickerHolder(View view) {
            super(view);
            itemLayoutLL = view.findViewById(R.id.RelativeLayout1);
            thumbnailIV = view.findViewById(R.id.thumbnail);
            fileNameTV = view.findViewById(R.id.filename);
            fileSizeTV = view.findViewById(R.id.filesize);
            checkBoxIV = view.findViewById(R.id.checkbox);
        }
    }

    private int getBitmapSize(Bitmap bitmap) {
//        if (Build.VERSION.SDK_INT >= 12) {
//            return new OldApiHelper().getBtimapSize(bitmap);
//        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    private class OldApiHelper {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        private int getBtimapSize(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    @SuppressLint("DefaultLocale")
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1)
            return "";
        return fileName.substring(index + 1).toLowerCase(Locale.getDefault());
    }

    /**
     * 把图片从缓存中取出
     * @param key
     * @return
     */
    private Bitmap getBitmapFromCache(String key) {
        return mBitmapsCache.get(key);
    }
}
