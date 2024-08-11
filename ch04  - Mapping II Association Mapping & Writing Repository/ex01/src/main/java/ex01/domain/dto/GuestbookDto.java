package ex01.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class GuestbookDto {
    private Integer id;
    private String name;
    private String contents;
    private Date regDate;
}