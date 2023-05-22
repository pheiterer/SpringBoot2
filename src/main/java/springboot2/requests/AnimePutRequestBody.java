package springboot2.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
