package avduhova.lubov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {
    private String uuid;

    @Builder.Default
    //короткая ссылка, полная инфа по ней
    private Map<String, Url> url = new HashMap<>();
}
