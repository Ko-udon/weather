package zerobase.weather.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

@Repository
public class JdbcMemoRepository {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JdbcMemoRepository(DataSource dataSource){
    jdbcTemplate=new JdbcTemplate(dataSource);
  }

  //Jpa를 사용하지 않는다면 아래와 같은 메소드를 모두 직접 구현해야 한다.
  /*public Memo save(Memo memo){
    String sql="insert into memo values(?,?)";
    jdbcTemplate.update(sql,memo.getId(),memo.getText());
    return memo;
  }

  public List<Memo> findAll(){
    String sql="select * from memo";
    return jdbcTemplate.query(sql,memoRowMapper());
  }

  public Optional<Memo> findById(int id){
    String sql="select * from memo where id=?";
    return jdbcTemplate.query(sql,memoRowMapper(),id).stream().findFirst();
  }

  private RowMapper<Memo> memoRowMapper(){
    //ResultSet

    return (rs,rowNum) -> new Memo(
        rs.getInt("id"),
        rs.getString("text")
    );

  }*/
}
