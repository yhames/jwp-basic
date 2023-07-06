package next.model;

import java.time.LocalDateTime;

public class Answer {

    private Long answerId;
    private String writer;
    private String contents;
    private LocalDateTime createdDate;
    private Long questionId;


    public Answer(Long answerId, String writer, String contents,
                  LocalDateTime createdDate, Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Answer(String writer, String contents, Long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.questionId = questionId;
        this.createdDate = LocalDateTime.now();

    }

    public void update(String writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
