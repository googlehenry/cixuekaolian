package com.viastub.kao100.wigets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetImageView extends androidx.appcompat.widget.AppCompatImageView {
    private String imagePath;
    public boolean isUseCache = false;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(getContext(), "ConnectionError", Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(getContext(), "ServerError", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NetImageView(Context context) {
        super(context);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageURL(String path) {
        imagePath = path;
        if (isUseCache) {
            useCacheImage();
        } else {
            useNetWorkImage();
        }
    }

    public void useNetWorkImage() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imagePath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        Bitmap bitmap;
                        InputStream inputStream = connection.getInputStream();

                        if (isUseCache) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            try {
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = inputStream.read(buffer)) > -1) {
                                    baos.write(buffer, 0, len);
                                }
                                baos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            InputStream is = new ByteArrayInputStream(baos.toByteArray());
                            InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

                            bitmap = getCompressBitmap(is);
                            cacheImage(is2);
                        } else {
                            bitmap = getCompressBitmap(inputStream);
                        }

                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }
            }
        }.start();
    }

    public void useCacheImage() {
        File file = new File(getContext().getCacheDir(), getURLPath());
        if (file != null && file.length() > 0) {
            try {
                InputStream inputStream = new FileInputStream(file);
                Bitmap bitmap = getCompressBitmap(inputStream);
                Message msg = Message.obtain();
                msg.obj = bitmap;
                msg.what = GET_DATA_SUCCESS;
                handler.sendMessage(msg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            useNetWorkImage();
        }
    }
    public void cacheImage(InputStream inputStream) {
        try {
            File file = new File(getContext().getCacheDir(), getURLPath());
            FileOutputStream fos = new FileOutputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getURLPath() {
        StringBuilder urlStr2 = new StringBuilder();
        String[] strings = imagePath.split("\\/");
        for (String string : strings) {
            urlStr2.append(string);
        }
        return urlStr2.toString();
    }

    public Bitmap getCompressBitmap(InputStream input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = getInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is2, null, options);
    }
    public int getInSampleSize(BitmapFactory.Options options) {
        int inSampleSize = 1;
        int realWith = realImageViewWith();
        int realHeight = realImageViewHeight();

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        if (outWidth > realWith || outHeight > realHeight) {
            int withRadio = Math.round(outWidth / realWith);
            int heightRadio = Math.round(outHeight / realHeight);
            inSampleSize = withRadio > heightRadio ? withRadio : heightRadio;
        }
        return inSampleSize;
    }

    public int realImageViewWith() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int width = getWidth();
        if (width <= 0) {
            width = layoutParams.width;
        }
        if (width <= 0) {
            width = getMaxWidth();
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        return width;
    }
    public int realImageViewHeight() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();

        int height = getHeight();
        if (height <= 0) {
            height = layoutParams.height;
        }
        if (height <= 0) {
            height = getMaxHeight();
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        return height;
    }
}