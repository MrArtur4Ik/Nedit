package me.nullicorn.nedit.provider.tag;

import java.util.function.Supplier;
import me.nullicorn.nedit.provider.NBTValueProvider;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class LongArrayProvider extends NBTValueProvider {

    @Override
    public long[][] provide() {
        return new long[][]{
            new long[0],
            generateLongs(129),
            generateLongs(3210)
        };
    }

    /**
     * Generates a diverse array of {@code long}s, whose {@code length} is determined by the
     * argument with the same name.
     */
    public static long[] generateLongs(int length) {
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = (i * 7 + (long) Math.pow(i, 6));
        }
        return array;
    }

    public static final class IOProvider extends NBTEncodedValueProvider {

        @Override
        public Supplier<ArgumentsProvider> provider() {
            return LongArrayProvider::new;
        }

        @Override
        public NBTEncoder<long[]> encoder() {
            return (out, array) -> {
                out.writeInt(array.length);
                for (long value : array) {
                    out.writeLong(value);
                }
            };
        }
    }
}
