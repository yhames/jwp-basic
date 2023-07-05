package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnswerDaoTest {

    private final AnswerDao answerDao = new AnswerDao();

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        // given
        Answer expected = new Answer(null, "writer", "contents", LocalDateTime.now(), 1L);

        // when
        Long expectedId = answerDao.insert(expected);
        Answer actual = answerDao.findById(expectedId);

        // TODO : isEqual, hashCode override
        // then
        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getContents(), actual.getContents());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getQuestionId(), actual.getQuestionId());

        // given
        actual.update("writer2", "contents2");

        // when
        answerDao.update(actual);
        Answer updated = answerDao.findById(expectedId);

        // then
        assertEquals(updated.getAnswerId(), actual.getAnswerId());
        assertEquals(updated.getWriter(), actual.getWriter());
        assertEquals(updated.getContents(), actual.getContents());
        assertEquals(updated.getCreatedDate(), actual.getCreatedDate());
        assertEquals(updated.getQuestionId(), actual.getQuestionId());
    }

    @Test
    public void findAll() {
        // when
        List<Answer> answers = answerDao.findAll();

        // then
        assertEquals(5, answers.size());
    }

}