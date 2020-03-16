//package com.bridgelabz.fundoonotes.serviceImplementation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.repository.UserDao;
//import com.bridgelabz.fundoonotes.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Service
//public class Loaders {
//
//    @Autowired
//    ElasticsearchOperations operations;
//    @Autowired
//    UserService userService;
//    @Autowired
//    UserDao usersRepository;
//    UserInformation userInformation=new UserInformation();
//    @PostConstruct
//    @Transactional
//    public void loadAll(){
//
//        operations.putMapping(UserInformation.class);
//        System.out.println("Loading Data");
//        usersRepository.saveAll(getData());
//        System.out.printf("Loading Completed");
//
//    }
//
//    private List<UserInformation> getData() {
//        List<UserInformation> userses = userService.getUsers();
////        userses.add(new Users("Ajay",123L, "Accounting", 12000L));
////        userses.add(new Users("Jaga",1234L, "Finance", 22000L));
////        userses.add(new Users("Thiru",1235L, "Accounting", 12000L));
//        return userses;
//    }
//}