package edu.poly.shop.utils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static File saveFileUpload(String nameFolder, Part part) {
        File folderUpload = new File("D:\\LEARN\\ServletJSP\\LAB\\Assignment_SOF3011\\src\\main\\webapp\\image\\" + nameFolder);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }

        File file = new File(folderUpload, part.getSubmittedFileName());
        try {
            if (!file.exists()) {
                part.write(file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
