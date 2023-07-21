package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyQnaService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

//    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
//        this.userRepository = userRepository;
//        this.questionRepository = questionRepository;
//    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}
