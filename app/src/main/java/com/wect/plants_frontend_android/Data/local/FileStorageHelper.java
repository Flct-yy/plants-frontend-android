package com.wect.plants_frontend_android.Data.local;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 文件系统操作（文件存储）
public class FileStorageHelper {

    // 处理内部存储文件读写
    // 作用：将文本内容保存到应用内部存储
    // 参数：
    // fileName：文件名（如 "notes.txt"）
    // content：要保存的文本内容
    // 存储路径：/data/data/<package_name>/files/
    // 返回值：boolean 表示是否成功
    public static boolean saveFile(Context context, String fileName, String content) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 读取文件
    // readFile(Context context, String fileName)
    // 作用：从内部存储读取文本文件
    // 返回值：文件内容字符串（读取失败返回 null）
    public static String readFile(Context context, String fileName) {
        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 保存图片到缓存
    // 作用：将 Bitmap 图片保存到缓存目录
    // 参数：
    // bitmap：要保存的图片对象
    // fileName：目标文件名（如 "avatar.png"）
    // 存储路径：/data/data/<package_name>/cache/
    // 返回值：保存后的 File 对象（失败返回 null）
    // 注意：缓存文件可能被系统自动清理
    public static File saveImageToCache(Context context, Bitmap bitmap, String fileName) {
        File cacheDir = context.getCacheDir();
        File imageFile = new File(cacheDir, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
