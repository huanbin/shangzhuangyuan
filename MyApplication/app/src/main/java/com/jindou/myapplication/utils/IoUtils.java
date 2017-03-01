package com.jindou.myapplication.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhishi on 2017/2/23.
 */

public class IoUtils {
    public static String readStringByInputstream(InputStream ins) throws IOException {
        BufferedReader fileReader=new BufferedReader(new InputStreamReader(ins));
        StringBuilder stringBuilder=new StringBuilder();
        String line;
        while ((line=fileReader.readLine())!=null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
