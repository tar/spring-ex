package com.mycelium.exsp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CommonUtils {

    private static final Logger _logger = Logger.getLogger(CommonUtils.class);

    public static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try {
            InputStream is = CommonUtils.class.getResourceAsStream("/" + fileName);
            props.load(is);
            is.close();
        } catch (IOException e) {
            _logger.error(e);
            return null;
        }
        return props;
    }
    
    public static String getTextFromFile(File file) {
        try {
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
            FileChannel fch = new FileInputStream(file).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(2 * 1024);
            CharBuffer chars = CharBuffer.allocate(2 * 1024);
            char[] array = chars.array();
            StringBuilder sb = new StringBuilder();
            while (fch.read(bb) > 0) {
                bb.flip();
                decoder.decode(bb, chars, false);
                chars.flip();
                sb.append(String.valueOf(array, chars.position(), chars.remaining()));
                bb.compact();
                chars.clear();
            }
            bb.flip();
            decoder.decode(bb, chars, true);
            decoder.flush(chars);
            chars.flip();
            sb.append(String.valueOf(array, chars.position(), chars.remaining()));
            fch.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            _logger.error(e);
        } catch (IOException e) {
            _logger.error(e);
        }
        return null;
    }
    public static String getTextFromFile(String path) {
        return getTextFromFile(new File(path));
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }
}
