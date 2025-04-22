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
    public Resource createRelative(String relativePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public byte[] getContentAsByteArray() {
        return byteString.toByteArray();
    }

    @Override
    public String getContentAsString(Charset charset) {
        return byteString.toString(charset);
    }

    @Override
    public String getDescription() {
        return "ByteString resource";
    }

    @Override
    public File getFile() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return byteString.newInput();
    }

    @Override
    public URI getURI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public URL getURL() {
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
