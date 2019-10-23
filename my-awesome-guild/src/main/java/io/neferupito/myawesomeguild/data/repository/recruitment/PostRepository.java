package io.neferupito.myawesomeguild.data.repository.recruitment;

import io.neferupito.myawesomeguild.data.domain.recruitment.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
