package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.support.context.CannotDeleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);
    private static final QuestionDao questionDao = QuestionDao.getInstance();
    private static final AnswerDao answerDao = AnswerDao.getInstance();

    private static QnaService qnaService;

    private QnaService() {
    }

    public static QnaService getInstance() {
        if (qnaService == null) {
            qnaService = new QnaService();
        }
        return qnaService;
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        // 로그인해야만 삭제 가능
        if (user == null) {
            throw new CannotDeleteException("로그인한 사용자만 삭제할 수 있습니다.");
        }

        // 존재하지 않는 글인 경우
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 글 입니다.");
        }

        // 질문 작성자만 삭제 가능
        if (!question.isSameUser(user)) {
            throw new CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }

        // 질문자와 답변자가 같은 경우에 삭제 가능
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (answers.stream()
                .anyMatch(answer -> !answer.getWriter().equals(question.getWriter()))) {
            throw new CannotDeleteException("다른 사용자의 답변이 있는 경우에는 삭제할 수 없습니다.");
        }

        questionDao.delete(questionId);
    }
}
