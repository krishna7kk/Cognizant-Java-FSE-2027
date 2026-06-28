import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
 @Autowired UserRepository repo;

 @Test
 void testFindByName(){
   User u=new User(); u.setName("Krishna");
   repo.save(u);
   List<User> list=repo.findByName("Krishna");
   assertEquals(1,list.size());
 }
}