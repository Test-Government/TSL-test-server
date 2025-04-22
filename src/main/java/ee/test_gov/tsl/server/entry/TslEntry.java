package ee.test_gov.tsl.server.entry;

import com.google.protobuf.ByteString;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.http.MediaType;

@Builder
public record TslEntry(
        @NonNull String fileName,
        @NonNull ByteString content,
        @NonNull MediaType mediaType
) {
}
