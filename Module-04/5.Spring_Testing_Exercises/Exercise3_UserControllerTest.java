import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
 @Autowired MockMvc mockMvc;
 @MockBean UserService userService;

 @Test
 void testGetUser() throws Exception{
   User u=new User(); u.setId(1L); u.setName("Krishna");
   when(userService.getUserById(1L)).thenReturn(u);

   mockMvc.perform(get("/users/1"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.name").value("Krishna"));
 }
}