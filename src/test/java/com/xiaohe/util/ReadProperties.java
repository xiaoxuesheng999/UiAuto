package com.xiaohe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
        public static Properties getValue(String filePath) throws IOException {
            Properties properties = new Properties();
            InputStream inputStream = ReadProperties.class.getClassLoader().getResourceAsStream(filePath);
            properties.load(inputStream);
            return properties;
        }
}

