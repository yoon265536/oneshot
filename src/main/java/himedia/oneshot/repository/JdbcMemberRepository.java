package himedia.oneshot.repository;

import himedia.oneshot.dto.MemberDTO;
import himedia.oneshot.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Repository
@Slf4j
public class JdbcMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setLogin_id(rs.getString("login_id"));
                member.setPw(rs.getString("pw"));
                member.setEmail(rs.getString("email"));
                member.setName(rs.getString("name"));
                member.setPhone_number(rs.getString("phone_number"));
                member.setId_card_number(rs.getString("id_card_number"));
                member.setAddress(rs.getString("address"));
                member.setGender(rs.getString("gender"));
                member.setAuthority(rs.getString("authority"));
                member.setDate_created(rs.getDate("date_created"));
                return member;
            }
        };
    }

    /**
     * 회원 관리를 위한 RowMapper 입니다.
     * 비밀번호 및 기타 개인정보 등 중요 개인정보사항을 제외한 가공된 member 데이터를 구합니다.
     * @return RowMapper&lt;Member&gt;
     */
    public RowMapper<Member> memberRowMapperForList(){
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setLogin_id(rs.getString("login_id"));
                member.setEmail(rs.getString("email"));
                member.setName(rs.getString("name"));
                member.setPhone_number(rs.getString("phone_number"));
                member.setAddress(rs.getString("address"));
                member.setGender(rs.getString("gender"));
                member.setAuthority(rs.getString("authority"));
                member.setDate_created(rs.getDate("date_created"));
                return member;
            }
        };
    }

    @Override
    public Member join(Member member) {
        return null;
    }

    @Override
    public void edit(Member member) {
        String sql = "UPDATE member SET email=?, phone_number=?, address=? WHERE id=?";
        int result =jdbcTemplate.update(sql,
                member.getEmail(),
                member.getPhone_number(),
                member.getAddress(),
                member.getId());
    }

    @Override
    public void ban(int memberId) {
    }

    @Override
    public Optional<Member> findById(long id) {
        String sql = "SELECT * FROM member WHERE id = ?;";
        List<Member> member = jdbcTemplate.query(sql, memberRowMapper(), id);
        return member.stream().findAny();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "SELECT * FROM member WHERE login_id = ?;";
        List<Member> memberList = jdbcTemplate.query(sql, memberRowMapper(), loginId);
        return memberList.stream().findAny();
    }

    /**
     * 아래의 필드를 조회한 모든 member를 List로 반환합니다.
     * 단, 관리자는 제외합니다.
     * select id, login_id, email, name, phone_number, address, gender, authority, date_created
     * @return List&lt;Member&gt;
     */
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT id, login_id, email, name, phone_number, address, gender, authority, date_created FROM member WHERE authority NOT LIKE 'M';", memberRowMapperForList());
    }

    @Override
    public void changePassword(long id, String password) {
        String sql = "UPDATE member SET pw =? WHERE id=?";
        jdbcTemplate.update(sql, password, id);
    }
}
