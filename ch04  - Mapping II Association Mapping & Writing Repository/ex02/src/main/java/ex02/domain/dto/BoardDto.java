package ex02.domain.dto;

import ex02.domain.User;
import lombok.*;
import org.springframework.core.annotation.AliasFor;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private Integer hit;
    private String title;
    private String contents;
    private Date regDate;

    @FieldPath("user.name")
    private String userName;
}
