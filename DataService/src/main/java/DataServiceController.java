
@RestController
public class DataServiceController {

        @RequestMapping("/")
        public String index() {
            return "Greetings from Spring Boot!";
        }

}
