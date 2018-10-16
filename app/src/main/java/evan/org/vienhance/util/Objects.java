package evan.org.vienhance.util;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Create By yejiaquan in 2018/10/16 14:22
 */

public class Objects {

    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(@Nullable Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static final class Strings{
        public static boolean isNullOrEmpty(String s){
            return s == null||s.isEmpty();
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
    public static final class Lists{
        public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
            checkNotNull(elements); // for GWT
            // Let ArrayList's sizing logic work, if possible
            return (elements instanceof Collection)
                    ? new ArrayList<E>(Collections2.cast(elements))
                    : newArrayList(elements.iterator());
        }

        public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
            ArrayList<E> list = newArrayList();
            Iterators.addAll(list, elements);
            return list;
        }

        public static <E> ArrayList<E> newArrayList() {
            return new ArrayList<E>();
        }
    }

    public static final class Iterators{
        public static <T> boolean addAll(
                Collection<T> addTo, Iterator<? extends T> iterator) {
            checkNotNull(addTo);
            checkNotNull(iterator);
            boolean wasModified = false;
            while (iterator.hasNext()) {
                wasModified |= addTo.add(iterator.next());
            }
            return wasModified;
        }
    }

    public static final class Collections2{
        static <T> Collection<T> cast(Iterable<T> iterable) {
            return (Collection<T>) iterable;
        }
    }
}
