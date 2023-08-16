package mx.iwa.gymki.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionBuilderUtil {
  public static <T, R> Set<R> getValues(final Collection<T> items, final Function<T, R> propFunc) {
    return items.stream().map(propFunc).filter(Objects::nonNull).collect(Collectors.toSet());
  }
}
