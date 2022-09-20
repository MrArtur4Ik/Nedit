package me.nullicorn.nedit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import me.nullicorn.nedit.type.NBTCompound;

/**
 * A utility class for writing NBT data to various destinations/formats
 *
 * @author Nullicorn
 */
public final class NBTWriter {

    /**
     * Serialize, gzip, and base64-encode the provided NBT data (in that order)
     *
     * @throws IOException If the NBT data could not be serialized
     * @see #writeToBase64(NBTCompound, boolean)
     * @see #writeToBase64(NBTCompound, String, boolean)
     */
    public static byte[] writeToBase64(NBTCompound data) throws IOException {
        return writeToBase64(data, "", true);
    }

    /**
     * Serialize and base64-encode the provided NBT data
     * <p>
     * If control over the root tag's name is needed, use
     * {@link #writeToBase64(NBTCompound, String, boolean)} instead.
     *
     * @param data           NBT compound to convert
     * @param useCompression If true, the NBT data is gzipped before being encoded as base64
     * @return Base64-encoded NBT data
     * @throws IOException If the NBT data could not be serialized
     * @see #writeToBase64(NBTCompound)
     * @see #writeToBase64(NBTCompound, String, boolean)
     */
    public static byte[] writeToBase64(NBTCompound data, boolean useCompression) throws IOException {
        return writeToBase64(data, /* rootCompoundName = */ "", useCompression);
    }

    /**
     * Serialize and base64-encode the provided NBT data
     * <p>
     * If the root compound's name doesn't matter, use
     * {@link #writeToBase64(NBTCompound, boolean)} instead.
     *
     * @param data           NBT compound to convert
     * @param rootCompoundName The name of the NBT compound that holds all other NBT data
     * @param useCompression If true, the NBT data is gzipped before being encoded as base64
     * @return Base64-encoded NBT data
     * @throws IOException If the NBT data could not be serialized
     * @see #writeToBase64(NBTCompound)
     * @see #writeToBase64(NBTCompound, boolean)
     */
    public static byte[] writeToBase64(NBTCompound data, String rootCompoundName, boolean useCompression) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(data, out, rootCompoundName, useCompression);
        return Base64.getEncoder().encode(out.toByteArray());
    }

    /**
     * Serialize, gzip, and write to file the provided NBT data
     *
     * @throws IOException If the NBT data could not be serialized or the file could not be written
     *                     to
     * @see #writeToFile(NBTCompound, File, boolean)
     * @see #writeToFile(NBTCompound, File, String, boolean)
     */
    public static void writeToFile(NBTCompound data, File file) throws IOException {
        writeToFile(data, file, "", true);
    }

    /**
     * Serialize the provided NBT data and write it to a file
     * <p>
     * If control over the root tag's name is needed, use
     * {@link #writeToFile(NBTCompound, File, String, boolean)} instead.
     *
     * @param data           NBT compound to serialize
     * @param file           File to write the data to
     * @param useCompression If true, the serialized data will be gzipped before being written to
     *                       the file
     * @throws IOException If the NBT data could not be serialized or the file could not be written
     *                     to
     * @see #writeToFile(NBTCompound, File)
     * @see #writeToFile(NBTCompound, File, String, boolean)
     */
    public static void writeToFile(NBTCompound data, File file, boolean useCompression) throws IOException {
        writeToFile(data, file, /* rootCompoundName = */ "", useCompression);
    }

    /**
     * Serialize the provided NBT data and write it to a file
     * <p>
     * If the root compound's name doesn't matter, use
     * {@link #writeToFile(NBTCompound, File, boolean)} instead.
     *
     * @param data           NBT compound to serialize
     * @param file           File to write the data to
     * @param rootCompoundName The name of the NBT compound that holds all other NBT data
     * @param useCompression If true, the serialized data will be gzipped before being written to
     *                       the file
     * @throws IOException If the NBT data could not be serialized or the file could not be written
     *                     to
     * @see #writeToFile(NBTCompound, File)
     * @see #writeToFile(NBTCompound, File, boolean)
     */
    public static void writeToFile(NBTCompound data, File file, String rootCompoundName, boolean useCompression) throws IOException {
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        if (file.getParentFile().exists()) {
            try (OutputStream fileOut = Files.newOutputStream(file.toPath())) {
                write(data, fileOut, rootCompoundName, useCompression);
            }
        } else {
            throw new FileNotFoundException("Failed to create required directories for " + file);
        }
    }

    /**
     * Serialize, gzip, and write the provided NBT data and write it to an output stream
     *
     * @throws IOException If the NBT data could not be serialized or the output stream could not be
     *                     written to
     * @see #write(NBTCompound, OutputStream, boolean)
     * @see #write(NBTCompound, OutputStream, String, boolean)
     */
    public static void write(NBTCompound data, OutputStream outputStream) throws IOException {
        write(data, outputStream, "", true);
    }

    /**
     * Serialize the provided NBT data and write it to an output stream
     * <p>
     * If control over the root tag's name is needed, use
     * {@link #write(NBTCompound, OutputStream, String, boolean)} instead.
     *
     * @param data             NBT compound to serialize
     * @param outputStream     Output stream to write the serialized NBT to
     * @param useCompression   If true, the serialized data will be gzipped
     * @throws IOException If the NBT data could not be serialized or the output stream could not be
     *                     written to
     * @see #write(NBTCompound, OutputStream)
     * @see #write(NBTCompound, OutputStream, String, boolean)
     */
    public static void write(NBTCompound data, OutputStream outputStream, boolean useCompression) throws IOException {
        write(data, outputStream, /* rootCompoundName = */ "", useCompression);
    }

    /**
     * Serialize the provided NBT data and write it to an output stream
     * <p>
     * If the root compound's name doesn't matter, use
     * {@link #write(NBTCompound, OutputStream, boolean)} instead.
     *
     * @param data             NBT compound to serialize
     * @param outputStream     Output stream to write the serialized NBT to
     * @param rootCompoundName The name of the NBT compound that holds all other NBT data
     * @param useCompression   If true, the serialized data will be gzipped
     * @throws IOException If the NBT data could not be serialized or the output stream could not be
     *                     written to
     * @see #write(NBTCompound, OutputStream)
     * @see #write(NBTCompound, OutputStream, boolean)
     */
    public static void write(NBTCompound data, OutputStream outputStream, String rootCompoundName, boolean useCompression) throws IOException {
        new NBTOutputStream(outputStream, useCompression).writeFully(data, rootCompoundName);
    }

    private NBTWriter() {
        throw new UnsupportedOperationException("NBTWriter should not be instantiated");
    }
}
