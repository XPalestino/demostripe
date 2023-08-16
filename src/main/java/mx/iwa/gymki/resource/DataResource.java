package mx.iwa.gymki.resource;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class DataResource<T> {
  @NonNull private final List<T> content;

  private final long size;

  public static <T> DataResource<T> build(final List<T> content) {
    return DataResource.<T>builder().content(content).size(content.size()).build();
  }
}
