package ee.test_gov.tsl.server.controller;

import ee.test_gov.tsl.server.entry.TslEntry;
import ee.test_gov.tsl.server.util.ByteStringResource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TslController {

    private final @NonNull Map<String, TslEntry> tslRegistry;

    @GetMapping(path = "/{tslEntity}")
    ResponseEntity<?> getTsl(@PathVariable("tslEntity") String tslEntity) {
        final TslEntry tslEntry = tslRegistry.get(tslEntity);

        if (tslEntry == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(tslEntry.mediaType())
                .body(new ByteStringResource(tslEntry.content()));
    }

}
