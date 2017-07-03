package com.jh352160.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtil {

    private final static int QR_WIDTH = 275;
    private final static int QR_HEIGHT = 275;

    private final static int MAX_HEIGHT = 640;

    public static Bitmap decodeFile(File f) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);

            if (inputStream != null) {

                int degree = readPictureDegree(f.getPath());
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = false;
                opts.inSampleSize = 4;
                Bitmap bitmap = rotateBitmap(
                        BitmapFactory.decodeStream(inputStream, null, opts),
                        degree);

                return bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap decodeBigFile(File f) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);

            if (inputStream != null) {

                int degree = readPictureDegree(f.getPath());
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = false;
                Bitmap bitmap = rotateBitmap(
                        BitmapFactory.decodeStream(inputStream, null, opts),
                        degree);

                return bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap decodeBigFile(File f, int size) {
        FileInputStream stream1 = null;
        FileInputStream stream2 = null;
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);

            int degree = readPictureDegree(f.getPath());
            // int degree = 90;
            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 200;
            // int width_tmp = o.outWidth, height_tmp = o.outHeight;
            double scale = 1;

            int length = 0;

            if (o.outWidth > o.outHeight) {
                length = o.outWidth;
            } else {
                length = o.outHeight;
            }

            while (true) {
                // if (width_tmp / 4 * 3 < size || height_tmp / 4 * 3 < size)
                // break;
                if (length / 7 * 6 < size)
                    break;
                length = length / 7 * 6;
                scale = scale * 7 / 6;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = (int) scale;
            o2.inJustDecodeBounds = false;
            stream2 = new FileInputStream(f);

            // Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);

            Bitmap bitmap = rotateBitmap(
                    BitmapFactory.decodeStream(stream2, null, o2), degree);

            bitmap = decodeBigFile(bitmap, size, 1);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream1 != null) {
                    stream1.close();
                }
                if (stream2 != null) {
                    stream2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f, int size) {
        FileInputStream stream1 = null;
        FileInputStream stream2 = null;
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);

            int degree = readPictureDegree(f.getPath());
            // int degree = 90;
            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 200;
            // int width_tmp = o.outWidth, height_tmp = o.outHeight;
            double scale = 1;

            int length = 0;

            if (o.outWidth > o.outHeight) {
                length = o.outWidth;
            } else {
                length = o.outHeight;
            }

            if (size > MAX_HEIGHT) {
                size = MAX_HEIGHT;
            }

            while (true) {
                // if (width_tmp / 4 * 3 < size || height_tmp / 4 * 3 < size)
                // break;
                if (length / 5 * 4 < size)
                    break;
                length = length / 5 * 4;
                scale = scale * 5 / 4;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = (int) scale;
            o2.inJustDecodeBounds = false;
            stream2 = new FileInputStream(f);

            // Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);

            Bitmap bitmap = rotateBitmap(
                    BitmapFactory.decodeStream(stream2, null, o2), degree);

            bitmap = decodeFile(bitmap, size, 1);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream1 != null) {
                    stream1.close();
                }
                if (stream2 != null) {
                    stream2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // decodes image and scales it to reduce memory consumption
    public static Bitmap decodeLoadFile(File f, int size) {
        FileInputStream stream1 = null;
        FileInputStream stream2 = null;
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);

            int degree = readPictureDegree(f.getPath());
            // int degree = 90;
            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 200;
            // int width_tmp = o.outWidth, height_tmp = o.outHeight;
            double scale = 1;

            int length = 0;

            if (o.outWidth > o.outHeight) {
                length = o.outWidth;
            } else {
                length = o.outHeight;
            }

            if (size > MAX_HEIGHT) {
                size = MAX_HEIGHT;
            }

            while (true) {
                // if (width_tmp / 4 * 3 < size || height_tmp / 4 * 3 < size)
                // break;
                if (length / 5 * 4 < size)
                    break;
                length = length / 5 * 4;
                scale = scale * 5 / 4;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = (int) scale;
            o2.inJustDecodeBounds = false;
            stream2 = new FileInputStream(f);
            Bitmap bitmap = rotateBitmap(
                    BitmapFactory.decodeStream(stream2, null, o2), degree);

            bitmap = decodeFile(bitmap, size, 1);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream1 != null) {
                    stream1.close();
                }
                if (stream2 != null) {
                    stream2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(Bitmap f, int size, int width) {
        try {
            // int degree = 90;
            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 200;
            // int width_tmp = o.outWidth, height_tmp = o.outHeight;
            float scale = 1;

            int length = 0;

            if (f.getWidth() > f.getHeight()) {
                length = f.getWidth();
            } else {
                length = f.getHeight();
            }

            if (size > MAX_HEIGHT) {
                size = MAX_HEIGHT;
            }

            while (true) {
                // if (width_tmp / 4 * 3 < size || height_tmp / 4 * 3 < size)
                // break;
                if (length / 5 * 4 < size)
                    break;
                length = length / 5 * 4;
                scale = scale * 5 / 4;
            }

            float s = (float) length / (float) (f.getWidth());

            Matrix matrix = new Matrix();
            matrix.postScale(s, s);
            // decode with inSampleSize
            f = Bitmap.createBitmap(f, 0, 0, f.getWidth(), f.getHeight(),
                    matrix, true);

            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // decodes image and scales it to reduce memory consumption
    public static Bitmap decodeBigFile(Bitmap f, int size, int width) {
        try {
            // int degree = 90;
            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 200;
            // int width_tmp = o.outWidth, height_tmp = o.outHeight;
            float scale = 1;

            int length = 0;

            if (f.getWidth() > f.getHeight()) {
                length = f.getWidth();
            } else {
                length = f.getHeight();
            }

            while (true) {
                // if (width_tmp / 4 * 3 < size || height_tmp / 4 * 3 < size)
                // break;
                if (length / 7 * 6 < size)
                    break;
                length = length / 7 * 6;
                scale = scale * 7 / 6;
            }

            float s = (float) length / (float) (f.getWidth());

            Matrix matrix = new Matrix();
            matrix.postScale(s, s);
            // decode with inSampleSize
            f = Bitmap.createBitmap(f, 0, 0, f.getWidth(), f.getHeight(),
                    matrix, true);

            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        return digree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }

    public static Bitmap combineBitmaps(Bitmap[] bitmaps, int w, int h) {
        int imageW = bitmaps[0].getWidth();
        int imageH = bitmaps[0].getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(imageW, imageH, Config.RGB_565);
        Canvas cv = new Canvas(newBitmap);
        for (int i = 0; i < bitmaps.length; i++) {
            if (i == 0) {
                cv.drawBitmap(bitmaps[0], 0, 0, null);
            } else {
                cv.drawBitmap(bitmaps[i], w, h, null);
            }
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();
        }
        return newBitmap;
    }

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
        return dateFormat.format(date) + ".jpg";
    }

    public static void saveLocalBitmap(Bitmap bitmap, File file, int quality) {
        if (bitmap == null) {
            return;
        }

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
            if (fOut != null) {
                fOut.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File bitmapToFile(Context context, File file, File imageDir) {
        Bitmap bitmap = BitmapUtil.decodeFile(file, WindowUtil.getDeviceWidth(context));
        File newFile = new File(imageDir, System.currentTimeMillis() + ".jpg");
        BitmapUtil.saveLocalBitmap(bitmap, newFile, 60);
        if (bitmap != null) {
            bitmap.recycle();
        }
        return newFile;
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, File imageDir) {
        File newFile = new File(imageDir, System.currentTimeMillis() + ".jpg");
        BitmapUtil.saveLocalBitmap(bitmap, newFile, 100);
        return newFile;
    }

}
