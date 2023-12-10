package com.its.service.enums;

public enum ActionPerformed {

  REVIEW("REVIEW"),
  APPROVE("APPROVE");

  private final String label;

  ActionPerformed(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
