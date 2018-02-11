package ru.hedhyw.cexpr.model;

import java.util.HashMap;

public class Constants extends HashMap<String, Double> {

  static final long serialVersionUID = 19785;

  public Constants() {
    put("PI", Math.PI);
    put("E", Math.E);
  }

}
