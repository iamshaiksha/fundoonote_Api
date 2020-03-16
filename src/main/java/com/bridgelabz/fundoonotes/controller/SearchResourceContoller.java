//package com.bridgelabz.fundoonotes.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.repository.UserDao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/rest/search")
//public class SearchResourceContoller {
//
//    @Autowired
//    UserDao usersRepository;
//    UserInformation userInformation=new UserInformation();
//    @GetMapping(value = "/name/{text}")
//    public List<UserInformation> searchName(@PathVariable final String text) {
//        return usersRepository.findByName(text);
//    }
//
//
////    @GetMapping(value = "/salary/{salary}")
////    public List<Users> searchSalary(@PathVariable final Long salary) {
////        return usersRepository.findBySalary(salary);
////    }
//
//
//    @GetMapping(value = "/all")
//    public List<UserInformation> searchAll() {
//        List<UserInformation> usersList = new ArrayList<>();
//        Iterable<UserInformation> userses = usersRepository.findAll();
//        userses.forEach(usersList::add);
//        return usersList;
//    }
//
//
//}