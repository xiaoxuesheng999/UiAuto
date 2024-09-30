package com.xiaohe.util;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ComparePicture {
    static Logger logger=Logger.getLogger(ComparePicture.class);
    static {
        System.load("D:\\developer\\developer_tools\\OpenCV\\opencv\\build\\java\\x64\\opencv_java490.dll");
    }

    public static double comparePicture(String imagePath,String imagePath2,int offset) {

        Mat src = Imgcodecs.imread(imagePath);
        Mat src2 = Imgcodecs.imread(imagePath2);

        if (src.empty() || src2.empty()) {
            logger.info("Error: One or both images cannot be read!");
            return 0;
        }


        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);


        Mat gray2 = new Mat();
        Imgproc.cvtColor(src2, gray2, Imgproc.COLOR_BGR2GRAY);


        Mat result = new Mat();
        Imgproc.matchTemplate(gray, gray2, result, Imgproc.TM_CCOEFF_NORMED);


        if (result.empty()) {
            logger.info("Error: Result matrix is empty!");
            return 0;
        }

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        double maxVal = mmr.maxVal;
        Point maxLoc = mmr.maxLoc;

        System.out.println("Best match at (" + maxLoc.x + ", " + maxLoc.y + ") with similarity " + maxVal);

        double x = maxLoc.x;
        double finallyoffset=x-offset;
        return finallyoffset;
    }
}
