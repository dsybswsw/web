// Copyright 2012 Mobvoi Inc. All Rights Reserved.
package com.platform.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Utils for paring the configuration file.
 *
 * @author Shiwei Wu, <dsybswsw@gmail.com>
 * @Date Jan 28, 2013
 */
public class ConfigParser {
  private final static Logger logger = Logger.getLogger(ConfigParser.class.getName());
  /**
   * Extract the elements of A=B to a pair <A,B>.
   */
  public static Pair<String, String> extractAttriValue(String line) {
    line = line.trim();
    if (line.startsWith("#")) {
      return null;
    }
    String[] splits = line.split("=");
    if (splits == null || splits.length != 2) {
      return null;
    } else {
      return new Pair<String, String>(splits[0].trim(), splits[1].trim());
    }
  }

  /**
   * Load the the lines in a file to a list of string.
   */
  public static void readLinesToSet(String filePath, List<String> contents) throws IOException {
    LineReader lineReader = new LineReader(filePath);
    while (lineReader.hasNext()) {
      String line = lineReader.next();
      contents.add(line);
    }
  }

  // load config file.
  public static Map<String, String> loadConfigFile(String filePath) {
    Map<String, String> options = new HashMap<String, String>();
    try {
      logger.info(filePath);
      LineReader lineReader = new LineReader(filePath);      
      while (lineReader.hasNext()) {
        String line = lineReader.next();
        Pair<String, String> keyValue = extractAttriValue(line);
        if (keyValue == null)
          continue;
        options.put(keyValue.getFirst(), keyValue.getSecond());
      }
      return options;
    } catch (IOException e) {
      e.printStackTrace();
      logger.info("Failed to initialize !");
      return null;
    }
  }
}
