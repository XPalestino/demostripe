package mx.iwa.gymki.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PageResource {
  private final int number;

  private final int size;

  public static PageResource build(final int number, final int size) {
    return PageResource.builder().number(number).size(size).build();
  }
}
