package com.xiaohe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
        public static Properties getValue(String filePath) throws IOException {
            Properties properties = new Properties();
            InputStream inputStream = ReadProperties.class.getClassLoader().getResourceAsStream(filePath);
          /*   从输入流中加载属性列表（键和元素对）*/
            properties.load(inputStream);
            return properties;
        }
}

