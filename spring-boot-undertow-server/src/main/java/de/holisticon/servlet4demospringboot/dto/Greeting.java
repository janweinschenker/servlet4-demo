package de.holisticon.servlet4demospringboot.dto;

public class Greeting {
  private long id;

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  private String content;

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
