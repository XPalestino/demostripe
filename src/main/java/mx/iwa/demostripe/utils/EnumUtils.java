package mx.iwa.demostripe.utils;

import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumUtils {

  public static <T, E extends Enum<E>> Function<T, E> lookupMap(
      final Class<E> clazz, final Function<E, T> mapper) {
    @SuppressWarnings("unchecked")
    final E[] emptyArray = (E[]) Array.newInstance(clazz, 0);
    return lookupMap(EnumSet.allOf(clazz).toArray(emptyArray), mapper);
  }

  public static <T, E extends Enum<E>> Function<T, E> lookupMap(
      final E[] values, final Function<E, T> mapper) {
    final Map<T, E> enumMap = Maps.newHashMapWithExpectedSize(values.length);
    for (final E value : values) {
      enumMap.put(mapper.apply(value), value);
    }
    return enumMap::get;
  }
}
