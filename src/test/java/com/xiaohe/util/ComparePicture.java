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
        // 加载 OpenCV 库
        System.load("D:\\developer\\developer_tools\\OpenCV\\opencv\\build\\java\\x64\\opencv_java490.dll");
    }

    public static double comparePicture(String imagePath,String imagePath2,int offset) {
        // 读取图片
        Mat src = Imgcodecs.imread(imagePath);
        Mat src2 = Imgcodecs.imread(imagePath2);

        if (src.empty() || src2.empty()) {
            logger.info("Error: One or both images cannot be read!");
            return 0;
        }

        // 转换为灰度图
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 如果 src2 也需要灰度图，则进行转换
        Mat gray2 = new Mat();
        Imgproc.cvtColor(src2, gray2, Imgproc.COLOR_BGR2GRAY);

        // 进行模板匹配
        Mat result = new Mat();
        Imgproc.matchTemplate(gray, gray2, result, Imgproc.TM_CCOEFF_NORMED);

        // 检查 result 是否为空
        if (result.empty()) {
            logger.info("Error: Result matrix is empty!");
            return 0;
        }

        // 找到匹配结果中的最大值及其位置
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        double maxVal = mmr.maxVal; // 相似度值
        Point maxLoc = mmr.maxLoc; // 最佳匹配位置

        // 打印最佳匹配的位置和相似度
        System.out.println("Best match at (" + maxLoc.x + ", " + maxLoc.y + ") with similarity " + maxVal);

        //返回坐标
        double x = maxLoc.x;
        double finallyoffset=x-offset;
        return finallyoffset;
    }
}
