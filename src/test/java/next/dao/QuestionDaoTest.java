package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionDaoTest {

    private final QuestionDao questionDao = new QuestionDao();

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        //given
        Question expected = new Question(null, "writer", "title", "content", LocalDateTime.now(), 0);

        // when
        Long expectedId = questionDao.insert(expected);
        Question actual = questionDao.findById(expectedId);

        // TODO : isEqual, hashCode override
        // then
        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getContents(), actual.getContents());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getCountOfAnswer(), actual.getCountOfAnswer());

        // given
        actual.update("writer2", "title2", "contents2");

        // when
        questionDao.update(actual);
        Question updated = questionDao.findById(expectedId);

        // then
        assertEquals(updated.getQuestionId(), actual.getQuestionId());
        assertEquals(updated.getWriter(), actual.getWriter());
        assertEquals(updated.getTitle(), actual.getTitle());
        assertEquals(updated.getContents(), actual.getContents());
        assertEquals(updated.getCreatedDate(), actual.getCreatedDate());
        assertEquals(updated.getCountOfAnswer(), actual.getCountOfAnswer());
    }

    @Test
    public void findAll() {
        // when
        List<Question> questions = questionDao.findAll();

        // then
        assertEquals(8, questions.size());
    }
}