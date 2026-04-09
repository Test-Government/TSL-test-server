package ee.test_gov.tsl.server.util;

import com.google.protobuf.ByteString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

@RequiredArgsConstructor
public class ByteStringResource implements Resource {

    private final @NonNull ByteString byteString;

    @Override
    public long contentLength() {
        return byteString.size();
    }

    @Override
    public @NonNull Resource createRelative(@NonNull String relativePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public byte @NonNull [] getContentAsByteArray() {
        return byteString.toByteArray();
    }

    @Override
    public @NonNull String getContentAsString(@NonNull Charset charset) {
        return byteString.toString(charset);
    }

    @Override
    public @NonNull String getDescription() {
        return "ByteString resource";
    }

    @Override
    public @NonNull File getFile() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public @NonNull InputStream getInputStream() {
        return byteString.newInput();
    }

    @Override
    public @NonNull URI getURI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull URL getURL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReadable() {
        return true;
    }

    @Override
    public long lastModified() {
        return 0;
    }

}
