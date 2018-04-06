package com.xsp.library.util.unused;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.util.Stack;

/**
 * 通过Url获取绝对路径
 * (http://stackoverflow.com/questions/13209494/how-to-get-the-full-file-path-from-uri)
 * on 2016/5/20.
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class AbsolutePathUtil {
    /**
     * @param context 上下文
     * @param uri     url对象
     * @return 绝对路径
     */
    public static String getAbsolutePath(final Context context, final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * public class BaseActivity extends AppCompatActivity {
     * private ActivityManagerUtil activityManagerUtil;
     * protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * setContentView(R.layout.activity_base);
     * activityManagerUtil = ActivityManagerUtil.getInstance();
     * activityManagerUtil.pushOneActivity(this);
     * }
     * protected void onDestroy() {
     * super.onDestroy();
     * activityManagerUtil.popOneActivity(this);
     * }
     * }
     * on 2016/5/19.
     * 微信公众号：吴小龙同学
     * 个人博客：http://wuxiaolong.me/
     */
    public static class ActivityManagerUtil {
        private static ActivityManagerUtil instance;
        //activity栈
        private Stack<Activity> activityStack = new Stack<>();

        /**
         * 单例模式
         *
         * @return 单例
         */

        public static ActivityManagerUtil getInstance() {
            if (instance == null) {
                instance = new ActivityManagerUtil();
            }
            return instance;
        }

        /**
         * 把一个activity压入栈中
         *
         * @param activity activity
         */
        public void pushOneActivity(Activity activity) {
            activityStack.add(activity);
        }


        /**
         * 移除一个activity
         *
         * @param activity activity
         */
        public void popOneActivity(Activity activity) {
            if (activityStack != null && activityStack.size() > 0) {
                if (activity != null) {
                    activityStack.remove(activity);
                    activity.finish();
                }
            }
        }

        /**
         * 获取栈顶的activity，先进后出原则
         *
         * @return 栈顶的activity
         */
        public Activity getLastActivity() {
            return activityStack.lastElement();
        }

        /**
         * 结束指定的Activity
         *
         * @param activity activity
         */
        public void finishActivity(Activity activity) {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
            }
        }

        /**
         * 结束指定类名的Activity
         *
         * @param cls 指定的Activity
         */
        public void finishActivity(Class<?> cls) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }

        /**
         * 结束所有activity
         */
        public void finishAllActivity() {
            try {
                for (int i = 0; i < activityStack.size(); i++) {
                    if (null != activityStack.get(i)) {
                        activityStack.get(i).finish();
                    }
                }
                activityStack.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 退出应用程序
         */
        public void appExit() {
            try {
                finishAllActivity();
                //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
                System.exit(0);
                //从操作系统中结束掉当前程序的进程
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
