package de.predic8.demo.api;

import de.predic8.demo.domain.User;
import de.predic8.demo.repository.UserRepo;
import de.predic8.demo.service.Greeter;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

  public static final String BREAK = "\n";
  @Autowired
  UserRepo userRepo;

  final Greeter greeter;

  public HelloWorldController(Greeter greeter) {
    this.greeter = greeter;
  }

  @RequestMapping("/specialHello")
  public String hello() {
    return greeter.getGreeting();
  }

  @GetMapping("/createUser")
  @ApiOperation("Achtung, diese Schnittstelle erstellt User!")
  public Integer createUser () {
    User user = new User();
    user.setNachname("Nachname" + Math.random());
    user.setVorname("Vorname" + Math.random());
    User saved = userRepo.save(user);
    return saved.getId();
  }

  @GetMapping("/getUser/{id}")
  public String getUser(@PathVariable Integer id) {
    User one = userRepo.getOne(id);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(one.getId() + BREAK);
    stringBuffer.append(one.getVorname() + BREAK);
    stringBuffer.append(one.getNachname() + BREAK);
    return stringBuffer.toString();
  }

  @GetMapping("/getUsers/{contains}")
  public List<StringBuffer> getUsers(@PathVariable String contains) {
    List<User> allByVornameContains = userRepo.findAllByVornameContains(contains);
    List<StringBuffer> collect = allByVornameContains.stream().map(val -> {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append(val.getId() + BREAK);
      stringBuffer.append(val.getVorname() + BREAK);
      stringBuffer.append(val.getNachname() + BREAK);
      return stringBuffer;
    }).collect(Collectors.toList());

    return collect;
  }

}
