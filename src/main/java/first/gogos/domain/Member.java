package first.gogos.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @OneToMany(mappedBy="member")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy="member")
    private List<Comment> commentList = new ArrayList<>();

    private String joindate;

    public void setJoindate() {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.joindate = format;

    }
}