import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class PostControllerTest {
 @Autowired MockMvc mockMvc;
 @MockBean UserService userService;

 @Test
 void testCreateUser() throws Exception{
   User u=new User(); u.setId(1L); u.setName("Krishna");
   when(userService.saveUser(any())).thenReturn(u);

   mockMvc.perform(post("/users")
     .contentType(MediaType.APPLICATION_JSON)
     .content("{\"id\":1,\"name\":\"Krishna\"}"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.name").value("Krishna"));
 }
}