package io.neferupito.myawesomeguild.api.controller.recruitment;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.recruitment.Apply;
import io.neferupito.myawesomeguild.data.domain.user.User;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class ApplyController {

    public ResponseEntity<Response<Apply>> createApply(Apply apply) {
        return ResponseEntity.ok(Response.<Apply>builder().content(Apply.builder().build()).build());
    }

    public ResponseEntity<Response<Apply>> readApply(Long id) {
        return ResponseEntity.ok(Response.<Apply>builder().content(Apply.builder().build()).build());
    }

    public ResponseEntity<Response<List<Apply>>> readAllAppliesFromUser(User user) {
        return ResponseEntity.ok(Response.<List<Apply>>builder().content(Arrays.asList(Apply.builder().build())).build());
    }

    public ResponseEntity<Response<Apply>> updateApply(Apply apply) {
        return ResponseEntity.ok(Response.<Apply>builder().content(Apply.builder().build()).build());
    }

    public ResponseEntity<Response<Apply>> deleteApply(Apply apply) {
        return ResponseEntity.ok(Response.<Apply>builder().content(Apply.builder().build()).build());
    }

}
