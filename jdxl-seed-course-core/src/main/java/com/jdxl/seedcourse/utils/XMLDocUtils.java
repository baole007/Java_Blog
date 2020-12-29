package com.jdxl.seedcourse.utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class XMLDocUtils {

    public static Document str2Doc(String xmlStr) throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
    }

}
