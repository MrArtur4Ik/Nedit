package me.nullicorn.nedit.provider.tag;

import java.io.DataOutputStream;
import java.util.function.Supplier;
import me.nullicorn.nedit.provider.NBTValueProvider;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class FloatProvider extends NBTValueProvider {

    @Override
    public float[] provide() {
        return new float[]{
            0,
            Float.MIN_VALUE,
            Float.MAX_VALUE
        };
    }

    public static final class IOProvider extends NBTEncodedValueProvider {

        @Override
        public Supplier<ArgumentsProvider> provider() {
            return FloatProvider::new;
        }

        @Override
        public NBTEncoder<Float> encoder() {
            return DataOutputStream::writeFloat;
        }
    }
}
