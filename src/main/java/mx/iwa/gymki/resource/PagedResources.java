package mx.iwa.gymki.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PagedResources<T> {
  private final DataResource<T> data;

  private final PageResource page;

  private final long totalElements;

  public static <T> PagedResources<T> build(
      final DataResource<T> data, final long totalElements, final PageResource page) {
    return PagedResources.<T>builder().data(data).totalElements(totalElements).page(page).build();
  }
}
