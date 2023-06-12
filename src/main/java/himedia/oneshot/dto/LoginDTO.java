package himedia.oneshot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 로그인과 관련된 데이터만을 담고 있는 DTO입니다.
 */
@Data
public class LoginDTO {
    private long id;
    private String loginId;
    private String pw;
    private String auth;
    private Boolean loginSuccess;

    /**
     *
     * @param id 회원 고유 번호
     * @param loginId 회원 로그인 아이디
     * @param auth 회원 상태
     * @param loginSuccess 로그인 성공 여부
     */
    public LoginDTO(long id, String loginId, String auth, Boolean loginSuccess) {
        this.id = id;
        this.loginId = loginId;
        this.auth = auth;
        this.loginSuccess = loginSuccess;
    }

    /**
     *
     * @param loginId 회원 로그인 아이디
     * @param pw 회원 비밀 번호
     */
    public LoginDTO(String loginId, String pw){
        this.loginId = loginId;
        this.pw = pw;
    }

    /**
     *
     * @param loginSuccess 로그인 성공 여부
     */
    public LoginDTO(Boolean loginSuccess){
        this.loginSuccess = loginSuccess;
    }
}


