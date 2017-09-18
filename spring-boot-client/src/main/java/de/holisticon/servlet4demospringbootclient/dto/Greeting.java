package de.holisticon.servlet4demospringbootclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
  private final long id;
  private final String content;

}
