package next.model;

import java.time.LocalDateTime;

public class Question {
    private Long questionId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private int countOfAnswer;

    public Question(Long questionId, String writer, String title, String contents,
                    LocalDateTime createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public void update(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;

    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }
}
