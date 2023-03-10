package zerobase.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;
import zerobase.weather.repository.JpaMemoRepository;

@SpringBootTest
@Transactional
public class JpaMemoRepositoryTest {
  @Autowired
  JpaMemoRepository jpaMemoRepository;

  @Test
  void insertMemoTest() {
      //given
      Memo newMemo=new Memo(10,"this is Jpa memo");
      //when
      jpaMemoRepository.save(newMemo);
      //then
      List<Memo> memoList=jpaMemoRepository.findAll();
      assertTrue(memoList.size()>0);
  }

  @Test
  void findBtIdTest() {
    //given
    Memo newMemo=new Memo(11,"jpa");
    //when
    jpaMemoRepository.save(newMemo);
    //then
    Optional<Memo> result=jpaMemoRepository.findById(11);
    assertEquals(result.get().getText(),"jpa");
  }

}
